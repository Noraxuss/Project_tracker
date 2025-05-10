package project_tracker_backend.dto.mapper;

import org.springframework.stereotype.Component;
import project_tracker_backend.domain.Project;
import project_tracker_backend.domain.User;
import project_tracker_backend.dto.incoming.UserCreationDto;
import project_tracker_backend.dto.outgoing.UserDetails;
import project_tracker_backend.dto.outgoing.UserProjectListDto;

@Component
public class UserMapper {

    private final ProjectMapper projectMapper;

    public UserMapper(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    public User mapUserCreationDtoToUser(UserCreationDto userCreationDto) {
        User user = new User();
        user.setUsername(userCreationDto.getUsername());
        user.setPassword(userCreationDto.getPassword());
        user.setEmail(userCreationDto.getEmail());
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
