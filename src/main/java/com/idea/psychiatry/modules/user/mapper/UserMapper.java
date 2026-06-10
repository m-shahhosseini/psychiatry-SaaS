package com.idea.psychiatry.modules.user.mapper;

import com.idea.psychiatry.modules.user.dto.UserResponse;
import com.idea.psychiatry.modules.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "id", target = "userId")
    UserResponse toResponse(User user);

    List<UserResponse> toResponseList(List<User> users);
}
