package dev.jam.accountservice.service.mappers;

import dev.jam.accountservice.dao.entities.UserAccount;
import dev.jam.accountservice.service.dtos.UserDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-28T21:48:50+0100",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto userToUserDto(UserAccount userAccount) {
        if ( userAccount == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        return userDto;
    }

    @Override
    public UserAccount userDtoToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserAccount userAccount = new UserAccount();

        return userAccount;
    }

    @Override
    public List<UserDto> usersToUserDtos(List<UserAccount> userAccounts) {
        if ( userAccounts == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( userAccounts.size() );
        for ( UserAccount userAccount : userAccounts ) {
            list.add( userToUserDto( userAccount ) );
        }

        return list;
    }
}
