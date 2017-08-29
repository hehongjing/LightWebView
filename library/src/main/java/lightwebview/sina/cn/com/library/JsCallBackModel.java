package lightwebview.sina.cn.com.library;

/**
 * @ClassName: JsCallBackModel
 * @Description ＪＳ回调model
 * @Author jopj
 * @Date 2017/5/23
 */

public class JsCallBackModel {
    public String callBackName; //回调函数名
    public String params; //回调参数
    public String url; //回调URL

    public JsCallBackModel() {
    }

    public JsCallBackModel(String callBackName, String params, String url) {
        this.callBackName = callBackName;
        this.params = params;
        this.url = url;
    }
}
