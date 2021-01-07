package self.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    @GetMapping("/auth")
    @ResponseBody
    public LoginResult auth() {
        //
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return LoginResult.success("用户没有登录", false);
        } else {
            return LoginResult.success(user);
        }
    }

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

}
