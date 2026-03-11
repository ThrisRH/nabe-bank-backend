package com.myproject.nabe_bank.modules.user;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import tools.jackson.databind.ObjectMapper;
import com.myproject.nabe_bank.core.exception.GlobalExceptionHandler;
import com.myproject.nabe_bank.modules.user.dto.request.CreateUserRequest;
import com.myproject.nabe_bank.modules.user.dto.request.PatchUserRequest;
import com.myproject.nabe_bank.modules.user.dto.request.UpdateUserRequest;
import com.myproject.nabe_bank.modules.user.dto.response.UserResponse;
import com.myproject.nabe_bank.modules.user.service.UserService;

@DisplayName("UserController")
class UserControllerTest {

    private MockMvc mockMvc;
    private UserService userService;
    private ObjectMapper objectMapper;

    private UserResponse sampleResponse;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        UserController controller = new UserController(userService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        objectMapper = new ObjectMapper();

        sampleResponse = new UserResponse(
                1L, "John Doe", "john@example.com",
                null, true, false, UserRole.EMPLOYEE,
                LocalDateTime.now(), LocalDateTime.now());
    }

    @Nested
    @DisplayName("POST /api/v1/user")
    class CreateUser {

        @Test
        @DisplayName("returns 201 and created user on valid input")
        void createUser_validInput_returns201() throws Exception {
            CreateUserRequest request = new CreateUserRequest();
            request.setName("John Doe");
            request.setEmail("john@example.com");

            when(userService.create(any(CreateUserRequest.class))).thenReturn(sampleResponse);

            mockMvc.perform(post("/api/v1/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("John Doe")))
                    .andExpect(jsonPath("$.email", is("john@example.com")));
        }

        @Test
        @DisplayName("returns 400 when name is blank")
        void createUser_blankName_returns400() throws Exception {
            CreateUserRequest request = new CreateUserRequest();
            request.setName("");
            request.setEmail("john@example.com");

            mockMvc.perform(post("/api/v1/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("returns 400 when email is invalid")
        void createUser_invalidEmail_returns400() throws Exception {
            CreateUserRequest request = new CreateUserRequest();
            request.setName("John Doe");
            request.setEmail("not-an-email");

            mockMvc.perform(post("/api/v1/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("GET /api/v1/user")
    class GetAllUsers {

        @Test
        @DisplayName("returns 200 and list of users")
        void getAll_returns200AndList() throws Exception {
            when(userService.getAll()).thenReturn(List.of(sampleResponse));

            mockMvc.perform(get("/api/v1/user"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].id", is(1)))
                    .andExpect(jsonPath("$[0].name", is("John Doe")));
        }

        @Test
        @DisplayName("returns 400 when no users exist")
        void getAll_noUsers_returns400() throws Exception {
            when(userService.getAll()).thenThrow(new IllegalArgumentException("No users found!"));

            mockMvc.perform(get("/api/v1/user"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", is("No users found!")));
        }
    }

    @Nested
    @DisplayName("GET /api/v1/user/{id}")
    class GetUserById {

        @Test
        @DisplayName("returns 200 and user wrapped in ApiResponse")
        void getById_exists_returns200() throws Exception {
            when(userService.getById(1L)).thenReturn(sampleResponse);

            mockMvc.perform(get("/api/v1/user/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code", is(200)))
                    .andExpect(jsonPath("$.status", is("SUCCESS")))
                    .andExpect(jsonPath("$.data.id", is(1)))
                    .andExpect(jsonPath("$.data.name", is("John Doe")));
        }

        @Test
        @DisplayName("returns 400 when user not found")
        void getById_notFound_returns400() throws Exception {
            when(userService.getById(99L)).thenThrow(new IllegalArgumentException("User not found"));

            mockMvc.perform(get("/api/v1/user/99"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", is("User not found")));
        }
    }

    @Nested
    @DisplayName("DELETE /api/v1/user/{id}")
    class DeleteUser {

        @Test
        @DisplayName("returns 200 with success message on delete")
        void delete_exists_returns200() throws Exception {
            doNothing().when(userService).delete(1L);

            mockMvc.perform(delete("/api/v1/user/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code", is(200)))
                    .andExpect(jsonPath("$.message", is("User deleted successfully")));
        }

        @Test
        @DisplayName("returns 400 when user not found")
        void delete_notFound_returns400() throws Exception {
            doThrow(new IllegalArgumentException("User not found")).when(userService).delete(99L);

            mockMvc.perform(delete("/api/v1/user/99"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", is("User not found")));
        }
    }

    @Nested
    @DisplayName("PUT /api/v1/user/{id}")
    class UpdateUser {

        @Test
        @DisplayName("returns 200 with updated user on valid input")
        void update_validInput_returns200() throws Exception {
            UpdateUserRequest request = new UpdateUserRequest();
            request.setName("Jane Doe");
            request.setEmail("jane@example.com");

            UserResponse updatedResponse = new UserResponse(
                    1L, "Jane Doe", "jane@example.com",
                    null, true, false, UserRole.EMPLOYEE,
                    LocalDateTime.now(), LocalDateTime.now());

            when(userService.update(eq(1L), any(UpdateUserRequest.class))).thenReturn(updatedResponse);

            mockMvc.perform(put("/api/v1/user/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code", is(200)))
                    .andExpect(jsonPath("$.data.name", is("Jane Doe")))
                    .andExpect(jsonPath("$.data.email", is("jane@example.com")));
        }

        @Test
        @DisplayName("returns 400 when name is blank")
        void update_blankName_returns400() throws Exception {
            UpdateUserRequest request = new UpdateUserRequest();
            request.setName("");
            request.setEmail("jane@example.com");

            mockMvc.perform(put("/api/v1/user/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("returns 400 when user not found")
        void update_notFound_returns400() throws Exception {
            UpdateUserRequest request = new UpdateUserRequest();
            request.setName("Jane");
            request.setEmail("jane@example.com");

            when(userService.update(eq(99L), any(UpdateUserRequest.class)))
                    .thenThrow(new IllegalArgumentException("User not found"));

            mockMvc.perform(put("/api/v1/user/99")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", is("User not found")));
        }
    }

    @Nested
    @DisplayName("PATCH /api/v1/user/{id}")
    class PatchUser {

        @Test
        @DisplayName("returns 200 with patched user on partial update")
        void patch_validInput_returns200() throws Exception {
            PatchUserRequest request = new PatchUserRequest();
            request.setName("Patched Name");

            when(userService.updateUserProfile(eq(1L), any(PatchUserRequest.class))).thenReturn(sampleResponse);

            mockMvc.perform(patch("/api/v1/user/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code", is(200)))
                    .andExpect(jsonPath("$.message", is("Update field successfully")));
        }

        @Test
        @DisplayName("returns 400 when user not found")
        void patch_notFound_returns400() throws Exception {
            PatchUserRequest request = new PatchUserRequest();
            request.setName("Some Name");

            when(userService.updateUserProfile(eq(99L), any(PatchUserRequest.class)))
                    .thenThrow(new IllegalArgumentException("User not found"));

            mockMvc.perform(patch("/api/v1/user/99")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", is("User not found")));
        }
    }
}
