package self.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import self.service.UserService;

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
//        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login").contentType())
    }

}