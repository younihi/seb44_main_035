package com.server.server.domain.user.mapper;

import com.server.server.domain.user.dto.UserDto;
import com.server.server.domain.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User postToUser(UserDto.Post post);
    UserDto.Response userToResponse(User user);
}
