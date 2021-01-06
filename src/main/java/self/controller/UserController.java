package self.controller;

import org.springframework.web.bind.annotation.*;
import self.entity.User;
import self.service.UserService;
import self.util.HttpResult;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Inject
    UserService userService;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public HttpResult list() {
        List<User> list = userService.list();
        return HttpResult.getSuccessResult(list);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public HttpResult add(@RequestBody User user) {
        //
        userService.save(user.getName(), user.getPassword());
        return HttpResult.getSuccessResult(user);
    }
}
