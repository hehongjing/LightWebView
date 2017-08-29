package lightwebview.sina.cn.com.library.annotations;

import android.content.Context;



import java.lang.reflect.Method;
import java.util.Map;

import lightwebview.sina.cn.com.library.JsCallBackModel;
import lightwebview.sina.cn.com.library.UrlInterceptorInter;

/**
 * @ClassName: UrlInterceptorAnnotationParser
 * @Description:URL拦截器注解解析器
 * @Author jopj
 * @Date 2017/5/18
 */

public class UrlInterceptorAnnotationParser implements AnnotationParserInter{
    private Context context;
    private Object delegate;
    private String url;
    public UrlInterceptorAnnotationParser(Context cxt,Object delegate,String url){
        this.context=cxt;
        this.url=url;
        this.delegate=delegate;
    }
    @Override
    public JsCallBackModel[] annotationParser() {
        JsCallBackModel[] jsCallBackModels=new JsCallBackModel[2];
        try {
            if(delegate!=null) {
                Class clazz = delegate.getClass();
                for (Method method : clazz.getMethods()) {
                    if (method.isAnnotationPresent(UrlInterceptorAnnotation.class)) {
                        UrlInterceptorAnnotation
                                urlInterceptor = method.getAnnotation(UrlInterceptorAnnotation.class);
                        UrlInterceptorInter urlInterceptorInter
                                = (UrlInterceptorInter) urlInterceptor.urlInterceptor().newInstance();
                        Map params = urlInterceptorInter.interceptor(url);
                        if (params != null && params.size() > 0) {
                            JsCallBackModel callBackModel =
                                    (JsCallBackModel) method.invoke(clazz.newInstance(), params, context);
                            jsCallBackModels[0]=callBackModel;
                        }
                    }
                }
            }
        //Activity中拦截
            if(context!=null) {
                Class clazz = context.getClass();
                for (Method method : clazz.getMethods()) {
                    if (method.isAnnotationPresent(UrlInterceptorAnnotation.class)) {
                        UrlInterceptorAnnotation
                                urlInterceptor = method.getAnnotation(UrlInterceptorAnnotation.class);
                        UrlInterceptorInter urlInterceptorInter
                                = (UrlInterceptorInter) urlInterceptor.urlInterceptor().newInstance();
                        Map params = urlInterceptorInter.interceptor(url);
                        if (params != null && params.size() > 0) {
                            JsCallBackModel callBackModel =
                                    (JsCallBackModel) method.invoke(clazz.newInstance(), params, context);
                            jsCallBackModels[1]=callBackModel;
                        }
                    }
                }
            }
           return jsCallBackModels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
