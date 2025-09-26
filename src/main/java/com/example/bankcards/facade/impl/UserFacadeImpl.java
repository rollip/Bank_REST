package com.example.bankcards.facade.impl;

import com.example.bankcards.facade.UserFacade;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    @Override
    public void delete(Long id) {
        userService.delete(id);
    }
}
