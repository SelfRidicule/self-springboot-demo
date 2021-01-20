package self.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import self.entity.User;
import self.dao.UserMapper;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserMapper userMapper;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @InjectMocks
    UserService userService = new UserService();

    @Test
    public void testSave() {
        //调用userService
        //验证userService将请求转发给userMapper
        Mockito.when(bCryptPasswordEncoder.encode("myPassword")).thenReturn("myEncodedPassword");
        userService.save("myUser", "myPassword");
        Mockito.verify(userMapper).save("myUser", "myEncodedPassword");
    }

    @Test
    public void testGetUserByUsername() {
        userService.getUserByUsername("myUser");
        Mockito.verify(userMapper).findUserByUsername("myUser");
    }

    @Test
    public void throwExceptionWhenUserNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("myUser"));
    }

    @Test
    public void returnUserDetailsWhenUserFound() {
        Mockito.when(userMapper.findUserByUsername("myUser")).thenReturn(new User(1, "myUser", "myEncodedPassword"));
        UserDetails userDetails = userService.loadUserByUsername("myUser");
        Assertions.assertEquals("myUser", userDetails.getUsername());
        Assertions.assertEquals("myEncodedPassword" , userDetails.getPassword());
    }

}