package com.example.bankcards.service;

import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.enums.Role;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.exception.UserException;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;


    @Test
    public void findByUsername_ShouldReturnUserEntity_WhenUserExists() {
        UserEntity userEntity = UserEntity.builder().username("rollip").build();
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(userEntity));

        UserEntity result = userService.findByUsername("rollip");

        assertEquals(userEntity, result);
        verify(userRepository).findByUsername(anyString());
    }

    @Test
    public void findByUsername_ShouldThrowNotFoundException_WhenUserDoesNotExist() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.findByUsername("rollip"));
        verify(userRepository).findByUsername(anyString());
    }


    @Test
    public void findById_ShouldReturnUserEntity_WhenUserExists() {
        UserEntity userEntity = UserEntity.builder().username("rollip").build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));

        UserEntity result = userService.findById(1L);

        assertEquals(userEntity, result);
        verify(userRepository).findById(anyLong());
    }

    @Test
    public void findById_ShouldThrowNotFoundException_WhenUserDoesNotExist() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.findById(1L));
        verify(userRepository).findById(anyLong());
    }


    @Test
    public void delete_ShouldDelete_WhenUserExists() {
        when(userRepository.existsById(anyLong())).thenReturn(true);

        userService.delete(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    public void delete_ShouldThrowNotFoundException_WhenUserDoesNotExist() {
        when(userRepository.existsById(anyLong())).thenReturn(false);
        assertThrows(NotFoundException.class, () -> userService.delete(1L));
        verify(userRepository, never()).deleteById(1L);
    }

    @Test
    public void create_ShouldCreateUserEntity_WhenUserValid() {
        String username = "rollip";
        String password = "12345";
        String encoded = "ha$heDpa$$";

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenAnswer(inv-> inv.getArgument(0));
        when(passwordEncoder.encode(password)).thenReturn(encoded);

        UserEntity result = userService.create(username, password);

        assertEquals(Role.ROLE_USER, result.getRole());
        assertEquals(username, result.getUsername());
        assertEquals(encoded, result.getPassword());
    }

    @Test
    public void create_ShouldThrowDuplicateUserException_WhenUserExists() {
        String username = "rollip";
        String password = "12345";
        when(userRepository.existsByUsername(anyString())).thenReturn(true);
        assertThrows(UserException.UserAlreadyExistsException.class,()-> userService.create(username, password));
    }



}
