package self.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import self.entity.Blog;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogDao {

    @Inject
    SqlSession sqlSession;

    public List<Blog> getBlogs(Integer page, Integer pageSize, Integer userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("offset", (page - 1) * pageSize);
        map.put("size", pageSize);
        return sqlSession.selectList("getBlogs", map);
    }

    public Integer count(Integer userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        return sqlSession.selectOne("countBlogs" , map);
    }

}
