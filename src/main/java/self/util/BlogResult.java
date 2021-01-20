package self.util;

import self.entity.Blog;
import self.util.Result.ResultStatus;

import java.util.List;

public class BlogResult extends Result<List<Blog>> {

    //总数
    private Integer total;
    //当前页数
    private Integer page;
    //总页数
    private Integer totalPage;


    public static BlogResult success(List<Blog> data, Integer total, Integer page, Integer totalPage) {
        return new BlogResult(ResultStatus.OK, "获取成功",  data,  total,  page,  totalPage);
    }

    public static BlogResult error(String msg) {
        return new BlogResult(ResultStatus.FAIL, msg, null,  null,  null,  null);
    }

    public static BlogResult fail() {
        return error("系统异常");
    }

    public BlogResult(ResultStatus status, String msg, List<Blog> data, Integer total, Integer page, Integer totalPage) {
        super(status, msg, data);
        this.total = total;
        this.page = page;
        this.totalPage = totalPage;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
