package com.server.server.domain.user.mapper;

import com.server.server.domain.user.dto.UserDto;
import com.server.server.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User postToUser(UserDto.Post post);
    UserDto.Response userToResponse(User user);
}
