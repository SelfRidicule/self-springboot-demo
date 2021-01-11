package self.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import self.entity.User;
import self.service.UserService;
import self.util.HttpResult;
import self.util.LoginResult;

import javax.inject.Inject;
import java.util.Map;

@RestController
public class AuthController {

    @Inject
    UserService userService;

    @Inject
    AuthenticationManager authenticationManager;

    /**
     * 是否登录
     */
    @GetMapping("/auth")
    @ResponseBody
    public LoginResult auth() {
        //
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication == null ? null : authentication.getName());
        if (user == null) {
            return LoginResult.success("用户没有登录", false);
        } else {
            return LoginResult.success(user);
        }
    }

    @GetMapping("/auth/logout")
    public LoginResult logout(){
        //
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return LoginResult.failure("用户尚未登录");
        } else {
            SecurityContextHolder.clearContext();
            return LoginResult.success("注销成功",user);
        }
    }

    /**
     * 登录
     */
    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestBody Map<String, Object> usernameAndPassword) {
        //
        String username = usernameAndPassword.get("username").toString();
        String password = usernameAndPassword.get("password").toString();
        UserDetails userDetails;
        try {
            userDetails = userService.loadUserByUsername(username);
        } catch (Exception e) {
            return LoginResult.failure("用户不存在");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        try {
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
            //
            return LoginResult.success("登录成功", userService.getUserByUsername(username));
        } catch (Exception e) {
            return HttpResult.getErrorResult("密码不正确", usernameAndPassword);
        }
    }

    /**
     * 注册
     */
    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    @ResponseBody
    public Object register(@RequestBody Map<String, String> usernameAndPassword) {
        //
        String username = usernameAndPassword.get("username");
        String password = usernameAndPassword.get("password");
        //用户名或密码不存在直接返回
        if(username == null || password == null){
            return HttpResult.getErrorResult("用户名或密码不能为空!", usernameAndPassword);
        }
        //查询用户是否存在
        User user = userService.getUserByUsername(username);
        //存在
        if(user != null){
            return HttpResult.getErrorResult("用户名存在!", usernameAndPassword);
        }else{
            //不存在->添加到数据库
            userService.save(username, password);
            return HttpResult.getSuccessResult(usernameAndPassword);
        }
    }

}
