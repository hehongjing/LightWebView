package lightwebview.sina.cn.com.library;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;

import org.json.JSONObject;

import java.net.URI;
import java.util.Iterator;

/**
 * Created by jopj on 2017/4/20.
 * WebView代理抽象类，以便于以后的不同增强代理的扩展
 */

public abstract class WebViewProxy {
    /**
     * WebView核心对象
     * */
    protected SinaWebView mSinaWebView;

    private WebViewServiceManager.WebViewServiceType mWebViewService[];

    public Context context;

    public Object mDelegate;

    public boolean take_over_title=false;//是否接管title设置 默认是不接管

    public ProgressChangedCallBack progressChangedCallBack; //加载动画

    public WebViewProxy(SinaWebView mSinaWebView,Object delegate,
                        WebViewServiceManager.WebViewServiceType...mWebViewService){
        this.mSinaWebView=mSinaWebView;
        this.context=mSinaWebView.getContext();
        this.mWebViewService = mWebViewService;
        this.mDelegate=delegate;
        init();


    }

    public WebSettings getSettings() {
        return mSinaWebView.getSettings();
    }

    private void init(){
        if(mWebViewService==null||mWebViewService.length<=0)
            return;
      for(WebViewServiceManager.WebViewServiceType webViewServiceType:mWebViewService){
          switch (webViewServiceType){
              case JSPROMPTEVENT_SERVICE:
                  loadJsPromptService();
                  break;
              case URL_INTERCEPT_SERVICE:
                  loadUrlInterceptorService();
                  break;
              case RES_CACHE_SERVICE:
                  resCacheService();
              default:
                  break;
          }
      }
    }

    //URL拦截器
    protected abstract void loadUrlInterceptorService();

    protected abstract void loadJsPromptService();

    protected abstract void resCacheService();

    public void renderWebViewContentByNetUrl(String url) {
        mSinaWebView.loadUrl(url);
    }



    public void renderWebViewContentByLocalTemplate(String base_url,String temp,String encoding) {
        mSinaWebView.loadDataWithBaseURL(base_url, temp, "text/html",
                encoding, "");
    }

    /**
     * @Description:是否让WebView接管页面的Title
     * @param isTake true表示接管，title有WebView设置
     * @return
     */
    public void setTakeOoverTitle(boolean isTake){
        take_over_title=isTake;
    }

    public void setOnProgressChangedCallBack(ProgressChangedCallBack callBack){
        this.progressChangedCallBack=callBack;
    }

    /**
     * 设置cookie
     *
     * @param model
     */
    public void synCookies(CookieModel model, String url) {

        try {
            CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            String data = model.getData();
            JSONObject json = new JSONObject(data);
            Iterator<String> iterator = json.keys();
            URI uri=new URI(url);
            String host=uri.getHost();
            while (iterator.hasNext()){
                String key = iterator.next();
                String value = json.optString(key);
                cookieManager.setCookie(url,key+"="+value+";path=/;domain="+host);
            }
            CookieSyncManager.getInstance().sync();

        } catch (Exception e) {

        }
    }

}
