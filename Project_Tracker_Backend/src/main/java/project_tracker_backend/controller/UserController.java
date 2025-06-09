package project_tracker_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project_tracker_backend.dto.incoming.UserCreationCommand;
import project_tracker_backend.dto.incoming.UserLoginCommand;
import project_tracker_backend.dto.outgoing.UserDetails;
import project_tracker_backend.dto.outgoing.UserProjectListDto;
import project_tracker_backend.service.UserService;

@RestController
@RequestMapping("/api/users")

public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDetails> registerUser(@RequestBody UserCreationCommand userCreationCommand) {
        UserDetails userDetails = userService.registerUser(userCreationCommand);
        return new ResponseEntity<>(userDetails, HttpStatus.CREATED);
    }

    @GetMapping("/login/{username}/{password}")
    public ResponseEntity<UserDetails> login(@PathVariable("username") String username,
                                        @PathVariable("password") String password) {
        UserLoginCommand loginCommand = new UserLoginCommand(username, password);
        UserDetails response = userService.login(loginCommand);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-user-projects")
    public UserProjectListDto getUserProjects(@RequestParam Long userId) {
        userService.getAllProjectsOfUser(userId);
        return null;
    }


}
