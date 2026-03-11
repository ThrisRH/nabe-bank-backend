package com.myproject.nabe_bank.modules.user.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.myproject.nabe_bank.modules.user.User;
import com.myproject.nabe_bank.modules.user.UserRole;
import com.myproject.nabe_bank.modules.user.dto.response.UserResponse;

@DisplayName("UserMapper")
class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    @DisplayName("maps all User fields to UserResponse correctly")
    void toResponse_mapsAllFields() {
        User user = new User("Alice", "alice@example.com");
        user.setId(5L);
        user.setAvatar("https://example.com/avatar.png");
        user.setRole(UserRole.ADMIN);
        user.setEnabled(true);
        user.setLocked(false);
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedDate(now);
        user.setUpdatedDate(now);

        UserResponse response = userMapper.toRespponse(user);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(5L);
        assertThat(response.getName()).isEqualTo("Alice");
        assertThat(response.getEmail()).isEqualTo("alice@example.com");
        assertThat(response.getAvatar()).isEqualTo("https://example.com/avatar.png");
        assertThat(response.getRole()).isEqualTo(UserRole.ADMIN);
        assertThat(response.getEnabled()).isTrue();
        assertThat(response.getLocked()).isFalse();
        assertThat(response.getCreatedDate()).isEqualTo(now);
        assertThat(response.getUpdatedDate()).isEqualTo(now);
    }

    @Test
    @DisplayName("returns null when user is null")
    void toResponse_nullUser_returnsNull() {
        UserResponse response = userMapper.toRespponse(null);

        assertThat(response).isNull();
    }

    @Test
    @DisplayName("maps optional fields as null when not set")
    void toResponse_optionalFieldsNull() {
        User user = new User("Bob", "bob@example.com");
        user.setId(2L);
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());

        UserResponse response = userMapper.toRespponse(user);

        assertThat(response).isNotNull();
        assertThat(response.getAvatar()).isNull();
    }
}
