package self.service;

import org.springframework.stereotype.Service;
import self.dao.BlogDao;
import self.entity.Blog;
import self.entity.User;
import self.util.BlogResult;

import javax.inject.Inject;
import java.util.List;

@Service
public class BlogService {

    @Inject
    private BlogDao blogDao;

    @Inject
    UserService userService;

    /**
     * 获取博客列表
     *
     * @return
     */
    public BlogResult getBlogs(Integer page, Integer pageSize, Integer userId) {
        try {
            List<Blog> blogList = blogDao.getBlogs(page, pageSize, userId);
            if (blogList != null && blogList.size() > 0) {
                blogList.forEach(blog -> {
                    User user = userService.findUserById(blog.getUserId());
                    blog.setUser(user);
                });
            }
            Integer count = blogDao.count(userId);
            Integer totalPage = count / pageSize;
            //不能整除
            if (count % pageSize != 0) {
                totalPage = totalPage + 1;
            }
            return BlogResult.success(blogList, count, page, totalPage);
        } catch (Exception e) {
            return BlogResult.fail();
        }
    }

}
