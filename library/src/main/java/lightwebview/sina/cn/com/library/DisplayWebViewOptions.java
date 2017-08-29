package lightwebview.sina.cn.com.library;

import android.webkit.WebSettings;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jopj on 2017/4/20.
 * WebView设置
 */

public final class DisplayWebViewOptions {

    private final boolean jsEnabled;  //是否支持js

    private final boolean pluginsEnabled;  //支持插件

    private final boolean useWideViewPort;  //将图片调整到适合webview的大小

    private final boolean supportZoom;  //支持缩放

    private final WebSettings.LayoutAlgorithm layoutAlgorithm; //支持内容重新布局

    private final boolean supportMultipleWindows;  //多窗口


    private final boolean allowFileAccess;  //设置可以访问文件


    private final boolean builtInZoomControls; //设置支持缩放

    private final WebSettings.PluginState pluginState;

    private final String textEncoding;

    private final WebSettings.TextSize textSize;

    private final boolean isAllowFileAccessFromFileURLs;

    private final boolean isWebContentsDebuggingEnabled;

    private final String userAgent;  //设置UA

    private final Map<String,String> cookies;

    private DisplayWebViewOptions(Builder builder){
        jsEnabled=builder.jsEnabled;
        cookies=builder.cookies;
        pluginsEnabled=builder.pluginsEnabled;
        userAgent=builder.userAgent;
        isWebContentsDebuggingEnabled=builder.isWebContentsDebuggingEnabled;
        isAllowFileAccessFromFileURLs=builder.isAllowFileAccessFromFileURLs;
        textSize=builder.textSize;
        textEncoding=builder.textEncoding;
        pluginState=builder.pluginState;
        builtInZoomControls=builder.builtInZoomControls;
        allowFileAccess=builder.allowFileAccess;
        supportMultipleWindows=builder.supportMultipleWindows;
        layoutAlgorithm=builder.layoutAlgorithm;
        supportZoom=builder.supportZoom;
        useWideViewPort=builder.useWideViewPort;
    }

    public boolean isJsEnabled(){
        return jsEnabled;
    }

    public Map<String,String> getCookies(){
        return cookies;
    }

    public boolean isPluginsEnabled(){
        return isPluginsEnabled();
    }

    public boolean isUseWideViewPort() {
        return useWideViewPort;
    }

    public boolean isSupportZoom() {
        return supportZoom;
    }

    public WebSettings.LayoutAlgorithm getLayoutAlgorithm() {
        return layoutAlgorithm;
    }

    public boolean isSupportMultipleWindows() {
        return supportMultipleWindows;
    }

    public boolean isAllowFileAccess() {
        return allowFileAccess;
    }

    public boolean isBuiltInZoomControls() {
        return builtInZoomControls;
    }

    public WebSettings.PluginState getPluginState() {
        return pluginState;
    }

    public String getTextEncoding() {
        return textEncoding;
    }

    public WebSettings.TextSize getTextSize() {
        return textSize;
    }

    public boolean isAllowFileAccessFromFileURLs() {
        return isAllowFileAccessFromFileURLs;
    }

    public boolean isWebContentsDebuggingEnabled() {
        return isWebContentsDebuggingEnabled;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public static class Builder {
        private  boolean jsEnabled=false;  //是否支持js

        private  boolean pluginsEnabled=false;  //支持插件

        private  boolean useWideViewPort=false;  //将图片调整到适合webview的大小

        private  boolean supportZoom=false;  //支持缩放

        private  WebSettings.LayoutAlgorithm layoutAlgorithm=null; //支持内容重新布局

        private  boolean supportMultipleWindows=false;  //多窗口

        private  boolean allowFileAccess=false;  //设置可以访问文件

        private  boolean builtInZoomControls=false; //设置支持缩放

        private  WebSettings.PluginState pluginState=null;

        private  String textEncoding="utf-8";

        private  WebSettings.TextSize textSize=null;

        private  boolean isAllowFileAccessFromFileURLs=false;

        private  boolean isWebContentsDebuggingEnabled=false;

        private  String userAgent=null;  //设置UA

        private  Map<String,String> cookies=new HashMap<>();

        public Builder(){

        }

        /**
         * @Description: 设置是否支持JS
         * @param jsEnabled true or false
         * @return
         */
        public Builder setJsEnabled(boolean jsEnabled){
            this.jsEnabled=jsEnabled;
            return this;
        }

        public Builder setPluginsEnabled(boolean pluginsEnabled){
            this.pluginsEnabled=pluginsEnabled;
            return this;
        }

        public Builder setUseWideViewPort(boolean useWideViewPort){
            this.useWideViewPort=useWideViewPort;
            return this;
        }

        public Builder setSupportZoom(boolean supportZoom){
            this.supportZoom=supportZoom;
            return this;
        }

        public Builder setLayoutAlgorithm(WebSettings.LayoutAlgorithm layoutAlgorithm){
            this.layoutAlgorithm=layoutAlgorithm;
            return this;
        }

        public Builder setSupportMultipleWindows(boolean supportMultipleWindows){
            this.supportMultipleWindows=supportMultipleWindows;
            return this;
        }

        public Builder setAllowFileAccess(boolean allowFileAccess){
            this.allowFileAccess=allowFileAccess;
            return this;
        }

        public Builder setBuiltInZoomControls(boolean builtInZoomControls){
            this.builtInZoomControls=builtInZoomControls;
            return this;
        }

        public Builder setPluginState(WebSettings.PluginState pluginState){
            this.pluginState=pluginState;
            return this;
        }

        public Builder setTextEncoding(String textEncoding){
            this.textEncoding=textEncoding;
            return this;
        }

        public Builder setTextSize(WebSettings.TextSize textSize){
            this.textSize=textSize;
            return this;
        }

        public Builder setAllowFileAccessFromFileURLs(boolean isAllowFileAccessFromFileURLs){
            this.isAllowFileAccessFromFileURLs=isAllowFileAccessFromFileURLs;
            return this;
        }

        public Builder setWebContentsDebuggingEnabled(boolean isWebContentsDebuggingEnabled){
            this.isWebContentsDebuggingEnabled=isWebContentsDebuggingEnabled;
            return this;
        }

        public Builder setUserAgent(String userAgent){
            this.userAgent=userAgent;
            return this;
        }

        public Builder addCookie(String key,String value){
            if(!cookies.containsKey(key)) {
                cookies.put(key, value);
            }
            return this;
        }

        public DisplayWebViewOptions build(){
            return new DisplayWebViewOptions(this);
        }

        public Builder cloneOptions(DisplayWebViewOptions options){
            jsEnabled=options.jsEnabled;
            cookies=options.cookies;
            pluginsEnabled=options.pluginsEnabled;
            userAgent=options.userAgent;
            isWebContentsDebuggingEnabled=options.isWebContentsDebuggingEnabled;
            isAllowFileAccessFromFileURLs=options.isAllowFileAccessFromFileURLs;
            textSize=options.textSize;
            textEncoding=options.textEncoding;
            pluginState=options.pluginState;
            builtInZoomControls=options.builtInZoomControls;
            allowFileAccess=options.allowFileAccess;
            supportMultipleWindows=options.supportMultipleWindows;
            layoutAlgorithm=options.layoutAlgorithm;
            supportZoom=options.supportZoom;
            useWideViewPort=options.useWideViewPort;
            return this;
        }
    }

    /**
     * @Description: 创建默认的配置选项
     * @return DisplayWebViewOptions
     */
   public static DisplayWebViewOptions createDefaultOptions(){
       return new Builder().setJsEnabled(true)
                           .setPluginState(WebSettings.PluginState.ON)
                           .setTextEncoding("utf-8")
                           .setAllowFileAccess(true)
                           .setAllowFileAccessFromFileURLs(true)
                           .setTextSize(WebSettings.TextSize.NORMAL).build();

   }
}
