package self.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import self.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AuthControllerTest {

    MockMvc mockMvc;

    @Mock
    UserService userService;
    @Mock
    AuthenticationManager authenticationManager;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    public void setUp() {
        //
        AuthController authController = new AuthController();
        authController.userService = userService;
        authController.authenticationManager = authenticationManager;
        //
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void returnNotLoginByDefault() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/auth")).andExpect(status().isOk()).andExpect(mvcResult -> {
            MockHttpServletResponse response = mvcResult.getResponse();
            response.setCharacterEncoding("utf-8");
            Assertions.assertTrue(response.getContentAsString().contains("用户没有登录"));
        });
    }

    @Test
    public void testLogin() throws Exception {
        //未登录
        mockMvc.perform(MockMvcRequestBuilders.get("/auth")).andExpect(status().isOk()).andExpect(mvcResult -> {
            MockHttpServletResponse response = mvcResult.getResponse();
            response.setCharacterEncoding("utf-8");
            Assertions.assertTrue(response.getContentAsString().contains("用户没有登录"));
        });
        //
        Map<String, String> map = new HashMap<>();
        map.put("username", "MyUser");
        map.put("password", "MyPassword");
        String value = new ObjectMapper().writeValueAsString(map);
        System.out.println(value);
        //
        Mockito.when(userService.loadUserByUsername("MyUser"))
                .thenReturn(new User("MyUser", bCryptPasswordEncoder.encode("MyPassword"), Collections.emptyList()));
        Mockito.when(userService.getUserByUsername("MyUser"))
                .thenReturn(new self.entity.User(1, "MyUser", bCryptPasswordEncoder.encode("MyPassword")));
        //
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login").contentType(MediaType.APPLICATION_JSON_UTF8).content(value))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    MockHttpServletResponse servletResponse = mvcResult.getResponse();
                    servletResponse.setCharacterEncoding("utf-8");
                    Assertions.assertTrue(servletResponse.getContentAsString().contains("登录成功"));
                })
                .andReturn();
        System.out.println(Arrays.toString(response.getResponse().getCookies()));
        HttpSession httpSession = response.getRequest().getSession();
        //
        mockMvc.perform(MockMvcRequestBuilders.get("/auth").session((MockHttpSession) httpSession)).andExpect(status().isOk()).andExpect(mvcResult -> {
            MockHttpServletResponse servletResponse = mvcResult.getResponse();
            servletResponse.setCharacterEncoding("utf-8");
            System.out.println(servletResponse.getContentAsString());
            Assertions.assertTrue(servletResponse.getContentAsString().contains("MyUser"));
        });
    }

}