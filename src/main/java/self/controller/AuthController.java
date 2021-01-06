package self.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import self.entity.User;
import self.service.UserService;
import self.util.HttpResult;

import javax.inject.Inject;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Inject
    UserService userService;

    @Inject
    AuthenticationManager authenticationManager;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public HttpResult login(@RequestBody User user) {
        //
        String name = user.getName();
        String password = user.getPassword();
        UserDetails userDetails;
        try {
            userDetails = userService.loadUserByUsername(name);
        } catch (Exception e) {
            return HttpResult.getErrorResult("用户名不存在", user);
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        try {
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
            //
            return HttpResult.getSuccessResult(user);
        } catch (Exception e) {
            return HttpResult.getErrorResult("密码不正确", user);
        }
    }

}
