package project_tracker_backend.dto.mapper;

import org.springframework.stereotype.Component;
import project_tracker_backend.domain.User;
import project_tracker_backend.dto.incoming.UserCreationCommand;
import project_tracker_backend.dto.outgoing.UserDetails;
import project_tracker_backend.dto.outgoing.UserProjectListDto;

@Component
public class UserMapper {

    private final ProjectMapper projectMapper;

    public UserMapper(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    public User mapUserCreationDtoToUser(UserCreationCommand userCreationCommand) {
        User user = new User();
        user.setUsername(userCreationCommand.getUsername());
        user.setPassword(userCreationCommand.getPassword());
        user.setEmail(userCreationCommand.getEmail());
        return user;
    }

    public UserProjectListDto mapUserToUserProjectListDto(User user) {
        UserProjectListDto userProjectListDto = new UserProjectListDto();
        userProjectListDto.setId(user.getId());
        userProjectListDto.setName(user.getUsername());
        return userProjectListDto;
    }

    public UserDetails mapUserToUserDetails(User user) {
        UserDetails userDetails = new UserDetails();
        userDetails.setId(user.getId());
        userDetails.setUserName(user.getUsername());
        return userDetails;
    }
}
