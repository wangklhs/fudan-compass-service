package fudan.pm.fudanCompass.Utils;

public class JsonResult<T> {

    private T data;
    private String code;

    /**
     * 若没有数据返回，默认状态码为 0，提示信息为“操作成功！”
     */
    public JsonResult() {
        this.code = "0";
    }

    /**
     * 若没有数据返回，可以人为指定状态码和提示信息
     * @param code
     */
    public JsonResult(String code) {
        this.code = code;
    }

    /**
     * 有数据返回时，状态码为 0，默认提示信息为“操作成功！”
     * @param data
     */
    public JsonResult(T data) {
        this.data = data;
        this.code = "0";
    }
    // 省略 get 和 set 方法
}
