package com.shopping.store.service.user;

import com.shopping.store.dto.UserDto;
import com.shopping.store.entity.User;
import com.shopping.store.request.CreateUserRequest;
import com.shopping.store.request.UserUpdateRequest;

public interface UserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToUserDto(User user);

    User getAuthenticatedUser();
}
