package self.controller;

import org.springframework.web.bind.annotation.*;
import self.entity.User;
import self.service.UserService;
import self.util.HttpResult;

import javax.inject.Inject;
import java.util.List;

@RestController
public class UserController {

    @Inject
    UserService userService;


    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    @ResponseBody
    public HttpResult list() {
        List<User> list = userService.list();
        return HttpResult.getSuccessResult(list);
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    @ResponseBody
    public HttpResult add(@RequestBody User user) {
        //
        userService.save(user.getUsername(), user.getEncryptedPassword());
        return HttpResult.getSuccessResult(user);
    }
}
