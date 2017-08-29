package lightwebview.sina.cn.com.library.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: UrlInterceptorAnnotation
 * @Description: URL拦截器
 * @Author jopj
 * @Date 2017/5/18
 */
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface UrlInterceptorAnnotation {
    Class urlInterceptor();
}
