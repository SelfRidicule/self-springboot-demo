package self.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import self.service.UserService;
import self.util.HttpResult;

import javax.inject.Inject;

@RestController
public class HelloController {

    @Inject
    UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public HttpResult index(ModelMap modelMap) {
        return HttpResult.getSuccessResult("主页");
    }

}