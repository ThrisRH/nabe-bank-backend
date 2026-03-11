package com.myproject.nabe_bank.modules.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.myproject.nabe_bank.modules.user.User;
import com.myproject.nabe_bank.modules.user.UserRepository;
import com.myproject.nabe_bank.modules.user.UserRole;
import com.myproject.nabe_bank.modules.user.dto.request.CreateUserRequest;
import com.myproject.nabe_bank.modules.user.dto.request.PatchUserRequest;
import com.myproject.nabe_bank.modules.user.dto.request.UpdateUserRequest;
import com.myproject.nabe_bank.modules.user.dto.response.UserResponse;
import com.myproject.nabe_bank.modules.user.mapper.UserMapper;
import com.myproject.nabe_bank.modules.user.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserServiceImpl")
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User sampleUser;
    private UserResponse sampleResponse;

    @BeforeEach
    void setUp() {
        sampleUser = new User("John Doe", "john@example.com");
        sampleUser.setId(1L);
        sampleUser.setRole(UserRole.EMPLOYEE);
        sampleUser.setEnabled(true);
        sampleUser.setLocked(false);
        sampleUser.setCreatedDate(LocalDateTime.now());
        sampleUser.setUpdatedDate(LocalDateTime.now());

        sampleResponse = new UserResponse(
                1L, "John Doe", "john@example.com",
                null, true, false, UserRole.EMPLOYEE,
                sampleUser.getCreatedDate(), sampleUser.getUpdatedDate());
    }

    @Nested
    @DisplayName("create()")
    class Create {

        @Test
        @DisplayName("saves a new user and returns mapped response")
        void create_success() {
            CreateUserRequest request = new CreateUserRequest();
            request.setName("John Doe");
            request.setEmail("john@example.com");

            when(userRepository.save(any(User.class))).thenReturn(sampleUser);
            when(userMapper.toRespponse(sampleUser)).thenReturn(sampleResponse);

            UserResponse result = userService.create(request);

            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(1L);
            assertThat(result.getName()).isEqualTo("John Doe");
            assertThat(result.getEmail()).isEqualTo("john@example.com");

            verify(userRepository).save(any(User.class));
            verify(userMapper).toRespponse(sampleUser);
        }
    }

    @Nested
    @DisplayName("getAll()")
    class GetAll {

        @Test
        @DisplayName("returns list of users when users exist")
        void getAll_success() {
            when(userRepository.findAll()).thenReturn(List.of(sampleUser));
            when(userMapper.toRespponse(sampleUser)).thenReturn(sampleResponse);

            List<UserResponse> results = userService.getAll();

            assertThat(results).hasSize(1);
            assertThat(results.get(0).getId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("throws IllegalArgumentException when no users exist")
        void getAll_empty_throwsException() {
            when(userRepository.findAll()).thenReturn(Collections.emptyList());

            assertThatThrownBy(() -> userService.getAll())
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("No users found!");

            verify(userMapper, never()).toRespponse(any());
        }
    }

    @Nested
    @DisplayName("getById()")
    class GetById {

        @Test
        @DisplayName("returns user response when user exists")
        void getById_success() {
            when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
            when(userMapper.toRespponse(sampleUser)).thenReturn(sampleResponse);

            UserResponse result = userService.getById(1L);

            assertThat(result.getId()).isEqualTo(1L);
            assertThat(result.getName()).isEqualTo("John Doe");
        }

        @Test
        @DisplayName("throws IllegalArgumentException when user not found")
        void getById_notFound_throwsException() {
            when(userRepository.findById(99L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> userService.getById(99L))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("User not found");
        }
    }

    @Nested
    @DisplayName("getByEmail()")
    class GetByEmail {

        @Test
        @DisplayName("returns user response when email matches")
        void getByEmail_success() {
            when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(sampleUser));
            when(userMapper.toRespponse(sampleUser)).thenReturn(sampleResponse);

            UserResponse result = userService.getByEmail("john@example.com");

            assertThat(result.getEmail()).isEqualTo("john@example.com");
        }

        @Test
        @DisplayName("throws IllegalArgumentException when email not found")
        void getByEmail_notFound_throwsException() {
            when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

            assertThatThrownBy(() -> userService.getByEmail("unknown@example.com"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("User not found");
        }
    }

    @Nested
    @DisplayName("update()")
    class Update {

        @Test
        @DisplayName("updates name and email, returns updated response")
        void update_success() {
            UpdateUserRequest request = new UpdateUserRequest();
            request.setName("Jane Doe");
            request.setEmail("jane@example.com");

            User updatedUser = new User("Jane Doe", "jane@example.com");
            updatedUser.setId(1L);

            UserResponse updatedResponse = new UserResponse(
                    1L, "Jane Doe", "jane@example.com",
                    null, true, false, UserRole.EMPLOYEE,
                    LocalDateTime.now(), LocalDateTime.now());

            when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
            when(userRepository.save(sampleUser)).thenReturn(sampleUser);
            when(userMapper.toRespponse(sampleUser)).thenReturn(updatedResponse);

            UserResponse result = userService.update(1L, request);

            assertThat(result.getName()).isEqualTo("Jane Doe");
            assertThat(result.getEmail()).isEqualTo("jane@example.com");
            verify(userRepository).save(sampleUser);
        }

        @Test
        @DisplayName("throws IllegalArgumentException when user not found")
        void update_notFound_throwsException() {
            UpdateUserRequest request = new UpdateUserRequest();
            request.setName("Jane Doe");
            request.setEmail("jane@example.com");

            when(userRepository.findById(99L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> userService.update(99L, request))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("User not found");

            verify(userRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("updateUserProfile()")
    class UpdateUserProfile {

        @Test
        @DisplayName("updates only name when only name is provided")
        void patch_nameOnly_success() {
            PatchUserRequest request = new PatchUserRequest();
            request.setName("Updated Name");

            when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

            UserResponse patchedResponse = new UserResponse(
                    1L, "Updated Name", "john@example.com",
                    null, true, false, UserRole.EMPLOYEE,
                    LocalDateTime.now(), LocalDateTime.now());
            when(userMapper.toRespponse(sampleUser)).thenReturn(patchedResponse);

            UserResponse result = userService.updateUserProfile(1L, request);

            assertThat(result.getName()).isEqualTo("Updated Name");
            assertThat(result.getEmail()).isEqualTo("john@example.com");
        }

        @Test
        @DisplayName("updates only email when only email is provided")
        void patch_emailOnly_success() {
            PatchUserRequest request = new PatchUserRequest();
            request.setEmail("newemail@example.com");

            when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

            UserResponse patchedResponse = new UserResponse(
                    1L, "John Doe", "newemail@example.com",
                    null, true, false, UserRole.EMPLOYEE,
                    LocalDateTime.now(), LocalDateTime.now());
            when(userMapper.toRespponse(sampleUser)).thenReturn(patchedResponse);

            UserResponse result = userService.updateUserProfile(1L, request);

            assertThat(result.getEmail()).isEqualTo("newemail@example.com");
            assertThat(result.getName()).isEqualTo("John Doe");
        }

        @Test
        @DisplayName("throws IllegalArgumentException when user not found")
        void patch_notFound_throwsException() {
            PatchUserRequest request = new PatchUserRequest();
            request.setName("Some Name");

            when(userRepository.findById(99L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> userService.updateUserProfile(99L, request))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("User not found");
        }
    }

    @Nested
    @DisplayName("delete()")
    class Delete {

        @Test
        @DisplayName("deletes user when user exists")
        void delete_success() {
            when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

            userService.delete(1L);

            verify(userRepository).delete(sampleUser);
        }

        @Test
        @DisplayName("throws IllegalArgumentException when user not found")
        void delete_notFound_throwsException() {
            when(userRepository.findById(99L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> userService.delete(99L))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("User not found");

            verify(userRepository, never()).delete(any());
        }
    }
}
