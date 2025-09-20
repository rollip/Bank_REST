package com.example.bankcards.facade;

import com.example.bankcards.facade.impl.UserFacadeImpl;
import com.example.bankcards.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserFacadeImplTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserFacadeImpl userFacade;

    @Test
    public void delete_ShouldCallService() {
        Long id = 1L;
        userFacade.delete(id);
        verify(userService).delete(id);
    }
}
