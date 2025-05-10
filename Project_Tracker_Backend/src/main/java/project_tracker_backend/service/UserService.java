package project_tracker_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import project_tracker_backend.config.security.KeyEncryptionKey;
import project_tracker_backend.domain.User;
import project_tracker_backend.dto.incoming.UserCreationDto;
import project_tracker_backend.dto.incoming.UserLoginCommand;
import project_tracker_backend.dto.mapper.UserMapper;
import project_tracker_backend.dto.outgoing.ProjectDetails;
import project_tracker_backend.dto.outgoing.UserDetails;
import project_tracker_backend.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final ProjectService projectService;

    private final KeyEncryptionKey keyEncryptionKey;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, ProjectService projectService, KeyEncryptionKey keyEncryptionKey) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.projectService = projectService;
        this.keyEncryptionKey = keyEncryptionKey;
    }

    public UserDetails registerUser(UserCreationDto userCreationDto) {
        userCreationDto.setPassword(keyEncryptionKey.generateKEK(userCreationDto.getPassword()));
        User user = userMapper.mapUserCreationDtoToUser(userCreationDto);
        userRepository.save(user);
        return userMapper.mapUserToUserDetails(user);
    }

    public User findUserById(Long id) {
        //TODO Exception throwing instead of null
        return userRepository.findById(id).orElse(null);
    }

    public void getAllProjectsOfUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        List<ProjectDetails> projectDetailsList = projectService.getUserProjects(userId);


    }

    public UserDetails login(UserLoginCommand loginCommand) {

        // Retrieve the user by username
        User user = userRepository.findUserByUsername(loginCommand.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        // Verify the password using Argon2PasswordEncoder
        Argon2PasswordEncoder encoder = keyEncryptionKey.getArgon2PasswordEncoder();
        if (!encoder.matches(loginCommand.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        UserDetails userDetails = userMapper.mapUserToUserDetails(user);

        // Return a success message or token (if applicable)
        return userDetails;

    }
}
