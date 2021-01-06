package self.util;

public class HttpResult {
    String code;
    String msg;
    Object data;

    public HttpResult(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功结果
     *
     * @param data 数据
     * @return HttpResult
     */
    public static HttpResult getSuccessResult(Object data) {
        return new HttpResult("200", "成功", data);
    }

    /**
     * 失败结果
     *
     * @param data 数据
     * @return HttpResult
     */
    public static HttpResult getErrorResult(Object data) {
        return new HttpResult("500", "失败", data);
    }

    /**
     * 失败结果
     *
     * @param msg  消息
     * @param data 数据
     * @return HttpResult
     */
    public static HttpResult getErrorResult(String msg, Object data) {
        return new HttpResult("500", msg, data);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
