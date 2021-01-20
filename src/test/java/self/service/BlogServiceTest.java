package self.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import self.dao.BlogDao;
import self.entity.Blog;
import self.util.Result;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {

    @Mock
    BlogDao blogDao;

    @InjectMocks
    BlogService blogService;

    @Test
    public void getBlogFromDb() {
        blogService.getBlogs(1, 10, null);
        Mockito.verify(blogDao).getBlogs(1, 10, null);
    }

    @Test
    public void returnFailureWhenExceptionThrow() {
        Mockito.when(blogDao.getBlogs(anyInt(), anyInt(), any())).thenThrow(new RuntimeException());
        Result result = blogService.getBlogs(1, 10, null);
        Assertions.assertEquals("fail", result.getStatus());
        Assertions.assertEquals("系统异常", result.getMsg());
    }

}
