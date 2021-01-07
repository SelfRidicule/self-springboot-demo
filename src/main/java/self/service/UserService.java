package self.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import self.entity.User;
import self.mapper.UserMapper;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Inject
    private UserMapper userMapper;

    @Inject
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public User getUserByUsername(String name) {
        return userMapper.findUserByUsername(name);
    }

    public List<User> list() {
        return userMapper.list();
    }

    public void save(String name, String password) {
        userMapper.save(name, bCryptPasswordEncoder.encode(password));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + " 不存在！");
        }

        return new org.springframework.security.core.userdetails.User(
                username, user.getEncryptedPassword(), Collections.emptyList());
    }

}
