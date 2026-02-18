package com.exchange_simulator.Mapper;

import com.exchange_simulator.dto.user.UserResponseDto;
import com.exchange_simulator.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toDto(User entity);
}
