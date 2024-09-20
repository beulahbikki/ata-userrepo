package com.ata.user_service.controller;

import com.ata.user_service.entity.UserEntity;
import com.ata.user_service.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateUser() {
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        when(userService.saveUser(any(UserEntity.class))).thenReturn(user);

        ResponseEntity<UserEntity> response = userController.createUser(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testGetAllUsers() {
        List<UserEntity> users = new ArrayList<>();
        users.add(new UserEntity());
        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<UserEntity>> response = userController.getAllUsers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetUserById() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        when(userService.getUserById(1L)).thenReturn(user);

        ResponseEntity<UserEntity> response = userController.getUserById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testDeleteUserById() {
        doNothing().when(userService).deleteUserById(1L);

        ResponseEntity<String> response = userController.deleteUserById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted successfully", response.getBody());
    }
}
