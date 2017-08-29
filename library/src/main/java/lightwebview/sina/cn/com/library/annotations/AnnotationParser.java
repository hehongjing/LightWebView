package lightwebview.sina.cn.com.library.annotations;

import android.content.Context;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import lightwebview.sina.cn.com.library.JsPromptEventDispatcher;

/**
 * Created by jopj on 2017/4/27.
 * 注解解析器
 */

public class AnnotationParser {

    private Context context;

    private String message;
    private static AnnotationParser parser;
    private JsPromptEventDispatcher jsPromptEventDispatcher;
    private List<Method> jsPromptEventActions; //所有带注解方法的集合
    private AnnotationParser(Context context,String message){
        this.context=context;
        this.message=message;
    }

    public static AnnotationParser getAnnotationParser(Context context,String msg){
        if (parser == null){
            synchronized(AnnotationParser.class){
                if (parser == null)
                    parser = new AnnotationParser(context,msg);
                 }
        }
        return parser;
    }

    public void annotationParser(){
        try {
            for (Method method : context
                    .getClass()
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
                jsPromptEventDispatcher=new JsPromptEventDispatcher<JsPromptEvent>();

            jsPromptEventDispatcher.dispatch(message,jsPromptEventActions,JsPromptEvent.class);

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}
