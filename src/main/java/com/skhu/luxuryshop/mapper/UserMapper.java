package com.skhu.luxuryshop.mapper;

import com.skhu.luxuryshop.dto.UserSignupDto;
import com.skhu.luxuryshop.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    User userSignupDtoToUser(UserSignupDto userSignup);

    @Mapping(target = "passwordCheck", ignore = true)
    UserSignupDto userToUserSignupDto(User user);
}
