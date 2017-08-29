package lightwebview.sina.cn.com.library;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.WebSettings;

/**
 * Created by jopj on 2017/4/20.
 */

public class WebViewServiceManager {

    private DisplayWebViewOptions displayWebViewOptions; //WebView配置
    /**
     * WEBView的服务配置
     */
    private WebViewServiceType mWebViewService[];
    /**
     * WebView代理类
     */
    private WebViewProxy webViewProxy;

    private Context context;

    private Object mDelegate;
    private ViewGroup rootView;
    private SinaWebView sinaWebView;

    private WebViewServiceManager(DisplayWebViewOptions options, Context context,
                                  Object delegate, ViewGroup rootView, WebViewServiceType... mWebViewService) {

        this.mWebViewService = mWebViewService;
        this.context = context;
        this.rootView = rootView;
        this.mDelegate = delegate;
        this.displayWebViewOptions = options;
        init();
    }

    public static WebViewServiceManager getWebViewService(DisplayWebViewOptions options, Context context,
                                                          Object delegate, ViewGroup rootView, WebViewServiceType... mWebViewService) {
        return new WebViewServiceManager(options, context, delegate, rootView, mWebViewService);
    }

    public enum WebViewServiceType {
        URL_INTERCEPT_SERVICE, //URL拦截服务
        JSPROMPTEVENT_SERVICE,  //JSP事件拦截服务
        RES_CACHE_SERVICE   //静态资源缓存服务
    }

    private void init() {
        if (context == null || rootView == null)
            return;
        SinaWebView webView = createSinaWebView();
        settingSinaWebView(webView);
        rootView.addView(webView);

        //把WebView传给代理
        webViewProxy = new SinaWebViewProxy(webView, mDelegate, mWebViewService);
    }

    /**
     * @return webView
     * @Description:创建最基本WEbView
     */
    private SinaWebView createSinaWebView() {
        sinaWebView = new SinaWebView(context);
        sinaWebView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        sinaWebView.setVerticalScrollBarEnabled(false);
        return sinaWebView;
    }

    /**
     * @param webView
     * @return
     * @Description: 设置WebView
     */
    private void settingSinaWebView(SinaWebView webView) {
        if (displayWebViewOptions == null)
            return;
        WebSettings settings = webView.getSettings();
        settings.setAllowFileAccess(displayWebViewOptions.isAllowFileAccess());
        if (displayWebViewOptions.getPluginState() != null) {
            settings.setPluginState(displayWebViewOptions.getPluginState());
        }
        if (!TextUtils.isEmpty(displayWebViewOptions.getTextEncoding())) {
            settings.setDefaultTextEncodingName(displayWebViewOptions.getTextEncoding());
        }
        settings.setAllowFileAccessFromFileURLs(displayWebViewOptions.isAllowFileAccessFromFileURLs());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setWebContentsDebuggingEnabled(true);
        }
        settings.setJavaScriptEnabled(displayWebViewOptions.isJsEnabled());
    }

    public void setUserAgent(String userAgent) {
        if (sinaWebView == null) {
            return;
        }
        sinaWebView.getSettings().setUserAgentString(userAgent);
    }

    public WebViewProxy getWebViewProxy() {
        return webViewProxy;
    }

}
