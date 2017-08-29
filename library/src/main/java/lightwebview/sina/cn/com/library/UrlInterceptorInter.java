package lightwebview.sina.cn.com.library;

import java.util.Map;

/**
 * @ClassName: UrlInterceptorInter
 * @Description:URL拦截器接口
 * @Author jopj
 * @Date 2017/5/18
 */

public interface UrlInterceptorInter {
   Map interceptor(String url);
}
