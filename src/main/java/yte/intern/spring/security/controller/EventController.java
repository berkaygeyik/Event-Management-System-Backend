package yte.intern.spring.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yte.intern.spring.security.dto.EventDTO;
import yte.intern.spring.security.dto.MessageResponse;
import yte.intern.spring.security.dto.UserDTO;
import yte.intern.spring.security.entity.Event;
import yte.intern.spring.security.entity.Users;
import yte.intern.spring.security.mapper.EventMapper;
import yte.intern.spring.security.mapper.UserMapper;
import yte.intern.spring.security.service.EventService;
import yte.intern.spring.security.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;
    private final UserMapper userMapper;

    @GetMapping("/user/events")
    public List<EventDTO> listAllEventsForUsers() {
        List<Event> events = eventService.listAllComments("user");
        return eventMapper.mapToDto(events);
    }

    @GetMapping("/admin/events")
    public List<EventDTO> listAllEventsForAdmins() {
        List<Event> events = eventService.listAllComments("admin");
        return eventMapper.mapToDto(events);
    }

    @GetMapping("/user/{eventName}/admin")
    public UserDTO getAdminFromEventName(@PathVariable String eventName){
        return userMapper.mapToDto(eventService.getAdminFromEventName(eventName));
    }

    @PostMapping("/admin/events")
    public MessageResponse addEvent(@Valid @RequestBody EventDTO eventDTO) {
        Event event = eventMapper.mapToEntity(eventDTO);
        return eventService.addEvent(event);
    }

    @GetMapping("/user/events/{eventName}")
    public EventDTO getEventByEventName(@PathVariable String eventName) {
        Event event = eventService.getEventByEventName(eventName);
        return eventMapper.mapToDto(event);
    }

    @PutMapping("/admin/events/{eventName}")
    public MessageResponse updateEvent(@PathVariable String eventName, @Valid @RequestBody EventDTO eventDTO) {
        return eventService.updateEvent(eventName, eventMapper.mapToEntity(eventDTO));
    }

    @DeleteMapping("/admin/events/{eventName}")
    public MessageResponse deleteEventByEventName(@PathVariable String eventName) {
        return eventService.deleteEventByEventName(eventName);
    }

    @GetMapping("/user/{eventName}/participants")
    public List<UserDTO> getEventParticipants(@PathVariable String eventName){
        List<Users> users = eventService.getEventParticipants(eventName);
        return userMapper.mapToDto(users);
    }

    @GetMapping("/user/getDayCounts/{eventName}")
    public List<Integer> getDayCounts(@PathVariable String eventName){
        return eventService.getDayCounts(eventName);
    }

    @PostMapping("/admin/giftDraw/{eventName}")
    public MessageResponse giftDraw(@PathVariable String eventName){
        return eventService.giftDraw(eventName);
    }
}
