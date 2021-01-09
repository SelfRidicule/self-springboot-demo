package self.controller;

import org.springframework.web.bind.annotation.*;
import self.entity.User;
import self.service.UserService;
import self.util.HttpResult;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

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
    public HttpResult add(@RequestBody Map<String , String> map) {
        //
        String username = map.get("username");
        String password = map.get("password");
        userService.save(username, password);
        return HttpResult.getSuccessResult(map);
    }
}
