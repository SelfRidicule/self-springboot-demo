package self.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import self.service.BlogService;
import self.util.BlogResult;

import javax.inject.Inject;

@Controller
public class BlogController {

    @Inject
    BlogService blogService;


    @RequestMapping(value = "/blog", method = RequestMethod.GET)
    @ResponseBody
    public BlogResult getBlogs(@RequestParam(value = "page" , required = false) Integer page, @RequestParam(value = "userId", required = false) Integer userId) {
        if (page == null || page <= 0) {
            page = 1;
        }
        return blogService.getBlogs(page, 10, userId);
    }

}
