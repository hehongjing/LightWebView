package lightwebview.sina.cn.com.library;

import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sina.libcomponent.webview.annotations.AnnotationParserInter;
import com.sina.libcomponent.webview.annotations.JsPromptAnnotationParser;
import com.sina.libcomponent.webview.annotations.UrlInterceptorAnnotationParser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jopj on 2017/4/20.
 *
 */

public class SinaWebViewProxy extends WebViewProxy {

    //  key :缓存路径 value: 内存中的缓存数据
    public static Map<String,byte[]> cche=new HashMap<>();


    public SinaWebViewProxy(SinaWebView mSinaWebView,Object delegate,
                            WebViewServiceManager.WebViewServiceType...mWebViewService) {
        super(mSinaWebView,delegate,mWebViewService);
    }


    @Override
    protected void loadUrlInterceptorService() {
      mSinaWebView.setWebViewClient(new WebViewClient(){
          @Override
          public boolean shouldOverrideUrlLoading(WebView view, String url) {
              Log.i("WEBV","-----url interceptor");
               JsCallBackModel[] jsCallBackModels= annotationParser(new UrlInterceptorAnnotationParser(context,mDelegate,url));
              for(JsCallBackModel jsCallBackModel:jsCallBackModels) {
                  if (jsCallBackModel != null) {
                      if (!TextUtils.isEmpty(jsCallBackModel.callBackName)) {
                          //有回调函数
                          String callBackName = jsCallBackModel.callBackName;

                          if (jsCallBackModel.params != null) {
                              callBackName = callBackName + "(" + jsCallBackModel.params + ")";
                          } else {
                              callBackName = callBackName + "()";
                          }
                          mSinaWebView.loadUrl(callBackName);
                          return false;
                      }
                      return TextUtils.isEmpty(jsCallBackModel.url) ? false : true;
                  }
              }
             return false;
          }

          @Override
          public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
              // 老版本以后的缓存
              WebResourceResponse response = getWebResourceResponse(url);
              if (response != null) return response;

              return super.shouldInterceptRequest(view, url);
          }

          @Override
          public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
              // 21版本以后的缓存
              if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                       return super.shouldInterceptRequest(view,request);
              }
              WebResourceResponse response = getWebResourceResponse(request.getUrl().toString());
              if (response != null) return response;

              return super.shouldInterceptRequest(view, request);
          }

          @Nullable
          private WebResourceResponse getWebResourceResponse(String urlString) {
              // 缓存文件夹
              String pa=context.getFilesDir().getAbsolutePath()+"/sina_lcs";
              url_res="";
              // 检测此url，本地有对应的缓存
              Uri uri=Uri.parse(urlString);
              checkUrl(pa,uri.getPath());
              // url 对应的缓存文件
              File res_file=new File(pa+url_res);
              if(res_file.exists()&&res_file.isFile()){
                  // 有缓存
                  System.out.println("最后找到 "+res_file.getAbsolutePath());
                  try {
                      URL url = new URL(urlString);
                      URLConnection connection = url.openConnection();

                      // 获得   contentType   ，mimeType   ，encoding
                      String contentType=connection.getContentType();
                      String mimeType = "";
                      String encoding = "";
                      if (contentType != null && !"".equals(contentType)) {
                          if (contentType.indexOf(";") != -1) {
                              String[] args = contentType.split(";");
                              mimeType = args[0];
                              String[] args2 = args[1].trim().split("=");
                              if (args.length == 2 && args2[0].trim().toLowerCase().equals("charset")) {
                                  encoding = args2[1].trim();
                              } else {

                                  encoding = "utf-8";
                              }
                          } else {
                              mimeType = contentType;
                              encoding = "utf-8";
                          }
                      }


                      byte[] res_b;
                      //   从内存缓存中搜索
                      if(cche.containsKey(url_res)){
                          res_b=cche.get(url_res);
                      }else{
                          // 从本地缓存中获得
                          res_b=getBytes(res_file);
                          // 保存到内存缓存中
                          cche.put(url_res,res_b);
                      }
                      WebResourceResponse response = new WebResourceResponse(mimeType,
                              encoding,
                              new ByteArrayInputStream(res_b));
                      return response;
                  }catch (Exception e){
                      e.printStackTrace();
                  }
              }
              return null;
          }
      });
    }



    @Override
    protected void loadJsPromptService() {

        mSinaWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if(take_over_title){
                    //接管设置页面title
                    if(context instanceof AppCompatActivity){
                        ((AppCompatActivity)context).getSupportActionBar()
                                .setTitle(title);
                    }
                }
            }

            @Override
            public boolean onJsPrompt(WebView view, String url,
                                      String message, String defaultValue,
                                      JsPromptResult result) {
                if(!TextUtils.isEmpty(message)){
                    annotationParser(new JsPromptAnnotationParser(context,mDelegate,message));
                }
                return true;
            }


            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(progressChangedCallBack!=null){
                    progressChangedCallBack.onProgressChanged(newProgress);
                }
            }
        });
    }

    @Override
    protected void resCacheService() {
      /*  mSinaWebView.setWebViewClient(new WebViewClient(){

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

                String result = "<html>\n" +
                        "<title>千度</title>\n" +
                        "<body>\n" +
                        "<a href=\"www.taobao.com\">千度</a>,比百度知道的多10倍\n" +
                        "</body>\n" +
                        "<html>";
                WebResourceResponse response = new WebResourceResponse("text/html",
                        "utf-8",
                        new ByteArrayInputStream(result.getBytes()));
                return response;
            }
        });*/
    }

    private JsCallBackModel[] annotationParser(AnnotationParserInter parserInter){
       return parserInter.annotationParser();
    }
    String url_res="";
    private void checkUrl(String filepath,String path){
        if(TextUtils.isEmpty(path))
            return;
        String fioe[]=path.split("/");
        String [] file_sop=new File(filepath).list();
        if(file_sop!=null) {
            for (String f : fioe) {
                for (String ff : file_sop) {
                    if (f.equalsIgnoreCase(ff)) {
                        path = path.substring(path.indexOf(f) + 1, path.length());
                        url_res += "/";
                        url_res += f;
                        checkUrl(filepath + "/" + f, path + "/" + f);
                    }
                }
            }
        }
    }

    /**
     * 获得指定文件的byte数组
     */
    private byte[] getBytes(File file){
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
