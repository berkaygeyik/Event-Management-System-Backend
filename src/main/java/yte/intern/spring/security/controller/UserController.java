package yte.intern.spring.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import yte.intern.spring.security.dto.EventDTO;
import yte.intern.spring.security.dto.MessageResponse;
import yte.intern.spring.security.dto.UserDTO;
import yte.intern.spring.security.dto.UserInfoDTO;
import yte.intern.spring.security.entity.Event;
import yte.intern.spring.security.entity.Users;
import yte.intern.spring.security.mapper.EventMapper;
import yte.intern.spring.security.mapper.UserMapper;
import yte.intern.spring.security.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final EventMapper eventMapper;

    /*
    @PostMapping("/addUser")
    @PreAuthorize("permitAll()")
    public String addUser(@RequestBody AddUserDTO addUserDTO) {
        return userService.addUser(addUserDTO);
    }
    */

    @PostMapping("/register/user")
    @PreAuthorize("permitAll()")
    public MessageResponse registerUser(@Valid @RequestBody UserDTO userDTO) {
        Users user = userMapper.mapToEntity(userDTO);
        return userService.register(user, "user");
    }

    @PostMapping("/register/admin")
    @PreAuthorize("permitAll()")
    public MessageResponse registerAdmin(@Valid @RequestBody UserDTO userDTO) {
        Users user = userMapper.mapToEntity(userDTO);
        return userService.register(user, "admin");
    }

    @GetMapping("/user/profile/{username}")
    public UserDTO getProfile(@PathVariable String username) {
        Users user = userService.getProfile(username);
        return userMapper.mapToDto(user);
    }

    @GetMapping("/user/shortUserInfo/{username}")
    public UserInfoDTO getShortUserInfo(@PathVariable String username) {
        return userService.getShortUserInfo(username);
    }

    @PutMapping("/user/updateProfile/{username}")
    public MessageResponse updateProfile(@PathVariable String username, @Valid @RequestBody UserDTO userDTO) {
        return userService.updateProfile(username, userMapper.mapToEntity(userDTO));

    }

    @DeleteMapping("/user/deleteProfile/{username}")
    public MessageResponse deleteProfile(@PathVariable String username) {
        return userService.deleteProfile(username);
    }

    @GetMapping("/admin/{username}/events")
    public List<EventDTO> getAdminEvents(@PathVariable String username){
        List<Event> events = userService.getUsersEvents(username);
        return eventMapper.mapToDto(events);
    }


    @PostMapping("/admin/{username}/events")
    public MessageResponse addEventToUser(@PathVariable String username, @RequestBody EventDTO eventDTO){
        return userService.addEventToUser(username, eventMapper.mapToEntity(eventDTO));
    }

    @GetMapping("/user/{username}/enrolledEvents")
    public List<EventDTO> getEnrolledEvents(@PathVariable String username){
        List<Event> events = userService.getEnrolledEvents(username);
        return eventMapper.mapToDto(events);
    }



    @PostMapping("/user/{username}/enrollTo/{eventName}")
    public MessageResponse enrollToEvent(@PathVariable String username, @PathVariable String eventName){
        return userService.enrollToEvent(username, eventName);
    }

    @PostMapping("/user/{username}/leaveFromEvent/{eventName}")
    public MessageResponse leaveFromEvent(@PathVariable String username, @PathVariable String eventName){
        return userService.leaveFromEvent(username, eventName);
    }

}
