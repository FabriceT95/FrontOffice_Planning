package com.agendo.frontoffice_planning.controller;

import com.agendo.frontoffice_planning.entity.Users;
import com.agendo.frontoffice_planning.repository.UserRepository;
import com.agendo.frontoffice_planning.service.File.StorageService;
import com.agendo.frontoffice_planning.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private StorageService storageService;

    @Autowired
    private MockMvc mockMvc;

   /* @Test
    @WithMockUser(roles = "USER")
    void when_getUser__then_return_User() throws Exception {
        // given
        Users users = new Users();
        users.setActivated(true);
        users.setEmail("test@test.fr");
        users.setPassword("random_password");
        users.setPhoto("/upload-dir/picture.jpg");
        users.setUsername("Test");

        // when
        when(userRepository.save(users)).thenReturn(users);

        // then
        mockMvc.perform(get("/api/users")).andExpect(status().isOk()).andDo(print());
    }*/

    @Test
    @WithMockUser(roles = "USER")
    void when_getUserbyId__then_return_User() throws Exception {
        // given
        Users users = new Users();
        users.setActivated(true);
        users.setEmail("test@test.fr");
        users.setPassword("random_password");
        users.setPhoto("/upload-dir/picture.jpg");
        users.setUsername("Test");

        // when
        when(userRepository.save(users)).thenReturn(users);

        // then
        mockMvc.perform(get("/api/users/id/1")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    @WithMockUser(roles = "USER")
    void when_getUserbyName__then_return_User() throws Exception {
        // given
        Users users = new Users();
        users.setActivated(true);
        users.setEmail("test@test.fr");
        users.setPassword("random_password");
        users.setPhoto("/upload-dir/picture.jpg");
        users.setUsername("Test");

        // when
        when(userRepository.save(users)).thenReturn(users);

        // then
        mockMvc.perform(get("/api/users/name/Test")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    @WithMockUser(roles = "USER")
    void when_getUserbyEmail__then_return_User() throws Exception {
        // given
        Users users = new Users();
        users.setActivated(true);
        users.setEmail("test@test.fr");
        users.setPassword("random_password");
        users.setPhoto("/upload-dir/picture.jpg");
        users.setUsername("Test");

        // when
        when(userRepository.save(users)).thenReturn(users);

        // then
        mockMvc.perform(get("/api/users/email/test@test.fr")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    @WithMockUser(roles = "USER")
    void when_deleteUserById__then_return_200AndTrue() throws Exception {
        // given
        Users users = new Users();
        users.setActivated(true);
        users.setEmail("test@test.fr");
        users.setPassword("random_password");
        users.setPhoto("/upload-dir/picture.jpg");
        users.setUsername("Test");

        // when
        when(userRepository.save(users)).thenReturn(users);

        // then
        mockMvc.perform(delete("/api/users/1")).andExpect(status().isOk()).andExpect(content().string("true")).andDo(print());
    }
}

