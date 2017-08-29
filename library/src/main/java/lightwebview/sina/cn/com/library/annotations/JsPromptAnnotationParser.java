package lightwebview.sina.cn.com.library.annotations;

import android.content.Context;

import com.sina.libcomponent.webview.IJsPromptParserInter;
import com.sina.libcomponent.webview.JsCallBackModel;
import com.sina.libcomponent.webview.JsPromptEventDispatcher;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: JsPromptAnnotationParser
 * @Description:JSPrompt事件注解解析器
 * @Author jopj
 * @Date 2017/5/18
 */

public class JsPromptAnnotationParser implements AnnotationParserInter {
    private Context context;
    private String message;
    private JsPromptEventDispatcher jsPromptEventDispatcher;
    private List<Method> jsPromptEventActions; //所有带注解方法的集合
    private IJsPromptParserInter jsPromptParser;
    private Object delegate;
    public JsPromptAnnotationParser(Context context,Object delegate,String message){
        this.context=context;
        this.message=message;
        this.delegate=delegate;
    }

    @Override
    public JsCallBackModel[] annotationParser() {
        try {
            Class clazz=delegate.getClass();
            if(clazz.isAnnotationPresent(JsPromptParserAnnotation.class)){
                //是否检测到类解析器注解
                JsPromptParserAnnotation jsPromptParserAnnotation
                        = (JsPromptParserAnnotation) clazz.getAnnotation(JsPromptParserAnnotation.class);
                jsPromptParser=(IJsPromptParserInter)jsPromptParserAnnotation.eventParser().newInstance();
            }
            for (Method method : clazz
                    .getMethods()) {
                if (method
                        .isAnnotationPresent(JsPromptEvent.class)) {
                    if(jsPromptEventActions==null) {
                        jsPromptEventActions = new ArrayList<>();
                    }
                    if(!jsPromptEventActions.contains(method)) {
                        jsPromptEventActions.add(method);
                    }

                }
            }
            if(jsPromptEventDispatcher==null)
                jsPromptEventDispatcher= new JsPromptEventDispatcher(jsPromptParser);

            jsPromptEventDispatcher.dispatch(message,context,delegate,jsPromptEventActions);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}

