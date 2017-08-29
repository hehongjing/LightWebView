package lightwebview.sina.cn.com.library;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by jopj on 2017/4/20.
 * JsPrompt事件调度员
 * 主要用于解析器和事件处理器之间的调度
 */

public class JsPromptEventDispatcher{
    /**
     * 解析器
     */
    private IJsPromptParserInter jsPromptParser;

    public JsPromptEventDispatcher(IJsPromptParserInter jsPromptParser){
       this.jsPromptParser=jsPromptParser;
    }

    /**
     * @Description: 调度方法
     * @param parser_content 需要解析的内容
     */
    public void dispatch(String parser_content, Context cxt,Object delegate,List<Method> jsPromptEventActions){
        Map map= jsPromptParser.parser(parser_content);
        Set<Map.Entry> keyAndValue= map.entrySet();
        String key="";
        for(Map.Entry entry:keyAndValue){
            key= (String) entry.getKey();
        }

        for(Method method:jsPromptEventActions){
            if(method.getName().equalsIgnoreCase(key)){
                try {
                    method.invoke(delegate,map.get(key));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
