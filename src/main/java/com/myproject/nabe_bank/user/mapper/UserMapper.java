package com.myproject.nabe_bank.user.mapper;

import com.myproject.nabe_bank.user.User;
import com.myproject.nabe_bank.user.dto.response.UserResponse;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toRespponse(User user) {
        if (user == null) {
            return null;
        }

        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getAvatar(), user.getEnabled(),
                user.getLocked(), user.getRole(), user.getCreatedDate(), user.getUpdatedDate());
    }
}
