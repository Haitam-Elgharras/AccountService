package dev.jam.accountservice.service.mappers;

import dev.jam.accountservice.dao.entities.UserAccount;
import org.mapstruct.Mapper;
import dev.jam.accountservice.service.dtos.UserDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDto(UserAccount userAccount);
    UserAccount userDtoToUser(UserDto userDto);

    List<UserDto> usersToUserDtos(List<UserAccount> userAccounts);
}