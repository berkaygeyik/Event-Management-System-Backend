package yte.intern.spring.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yte.intern.spring.security.dto.MessageResponse;
import yte.intern.spring.security.dto.UserDTO;
import yte.intern.spring.security.dto.UserInfoDTO;
import yte.intern.spring.security.entity.Authority;
import yte.intern.spring.security.entity.Event;
import yte.intern.spring.security.entity.EventUser;
import yte.intern.spring.security.entity.Users;
import yte.intern.spring.security.repository.AuthorityRepository;
import yte.intern.spring.security.repository.EventRepository;
import yte.intern.spring.security.repository.EventUserRepository;
import yte.intern.spring.security.repository.UsersRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static yte.intern.spring.security.dto.MessageType.SUCCESS;
import static yte.intern.spring.security.dto.MessageType.ERROR;

@Service
@RequiredArgsConstructor
public class UserService {

    //private final CustomUserDetailsManager customUserDetailsManager;
    private final AuthorityRepository authorityRepository;
    private final UsersRepository usersRepository;
    private final EventRepository eventRepository;
    private final EventUserRepository eventUserRepository;
    private final PasswordEncoder passwordEncoder;

    private final Authority authorityAdmin = new Authority(null, new HashSet<>(), "ADMIN");
    private final Authority authorityUser = new Authority(null, new HashSet<>(), "USER");

    public MessageResponse register(Users users, String userType) {

        if(usersRepository.existsByUsername(users.getUsername())){
            return new MessageResponse("The user with this username has been already created.", ERROR);
        }
        //return usersRepository.save(users);
        users.setEvents(Set.of());

        List<Authority> savedAuthorities;
        if(userType.equals("user")){
            savedAuthorities = authorityRepository.saveAll(Set.of(authorityUser));
            users.setUserRole("user");
        }
        else if(userType.equals("admin")){
            savedAuthorities = authorityRepository.saveAll(Set.of(authorityUser, authorityAdmin));
            users.setUserRole("admin");
        }
        else {
            return new MessageResponse("Wrong request!", ERROR);
        }

        users.setAuthorities(Set.copyOf(savedAuthorities));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        usersRepository.save(users);

        return new MessageResponse("The user has been successfully added.", SUCCESS);
    }

    public Users getProfile(String username) {

        if(!usersRepository.existsByUsername(username)){
            System.out.println("There is no user with this name. Chose a different name.");
            return null;
        }
        return usersRepository.findByUsername(username);
    }

    public UserInfoDTO getShortUserInfo(String username) {

        if(!usersRepository.existsByUsername(username)){
            System.out.println("There is no user with this name. Chose a different name.");
            return null;
        }
        Users user = usersRepository.findByUsername(username);

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUsername(username);
        userInfoDTO.setImageURL(user.getImageURL());
        return userInfoDTO;
    }

    public MessageResponse updateProfile(String username, Users newUser) {

        if(!usersRepository.existsByUsername(username)){
            return new MessageResponse("There is no user with this name. Chose a different name.", ERROR);
        }

        Users userFromDB = usersRepository.findByUsername(username);
        newUser.setId(userFromDB.getId());

        update(userFromDB, newUser);
        usersRepository.save(userFromDB);
        
        return new MessageResponse("The user has been successfully updated.", SUCCESS);
    }

    private void update(Users userFromDB, Users newUser) {
        userFromDB.setName(newUser.getName());
        userFromDB.setSurname(newUser.getSurname());
        userFromDB.setEmail(newUser.getEmail());
        userFromDB.setUsername(newUser.getUsername());
        userFromDB.setTcIdentificationNumber(newUser.getTcIdentificationNumber());
        userFromDB.setImageURL(newUser.getImageURL());
    }

    public MessageResponse deleteProfile(String username) {

        if(!usersRepository.existsByUsername(username)){
            return new MessageResponse("There is no user with this name. Chose a different name.", ERROR);
        }

        Users user = usersRepository.findByUsername(username);
        Set<Event> eventSet = user.getEnrolledEvents();
        for (Event event: eventSet) {
            event.setRegisteredUserCount(event.getRegisteredUserCount()-1);
            eventRepository.save(event);
        }

        usersRepository.deleteByUsername(username);

        return new MessageResponse("The user has been successfully updated.", SUCCESS);
    }

    public List<Event> getUsersEvents(String username) {
        if(!usersRepository.existsByUsername(username)){
            System.out.println("There is no user with this name. Chose a different name.");
            return null;
        }

        Users user = usersRepository.findByUsername(username);
        return new ArrayList<>(user.getEvents());
    }

    public MessageResponse addEventToUser(String username, Event event) {

        if(!usersRepository.existsByUsername(username)){
            return new MessageResponse("There is no user with this name. Chose a different name.", ERROR);
        }

        if(eventRepository.existsByName(event.getName())){
            return new MessageResponse("There is an event created with this event name. Choose another name.", ERROR);
        }

        LocalDateTime startDate = event.getStartDate();
        LocalDateTime endDate = event.getEndDate();
        LocalDateTime addDate = LocalDateTime.now();

        if(!startDate.isBefore(endDate)){
            return new MessageResponse("The start date of the event must be before the end date.", ERROR);
        }

        if(startDate.isBefore(addDate)){
            return new MessageResponse("After the start date, the event cannot be added.", ERROR);
        }

        Users user = usersRepository.findByUsername(username);
        event.setUsers(user);
        Event savedEvent = eventRepository.save(event);
        user.getEvents().add(savedEvent);
        usersRepository.save(user);
        return new MessageResponse("The event successfully added.", SUCCESS);
    }

    public List<Event> getEnrolledEvents(String username) {
        if(!usersRepository.existsByUsername(username)){
            System.out.println("There is no user with this name. Chose a different name.");
            return null;
        }

        Users user = usersRepository.findByUsername(username);
        return new ArrayList<>(user.getEnrolledEvents());
    }

    public MessageResponse enrollToEvent(String username, String eventName) {

        if(!usersRepository.existsByUsername(username)){
            return new MessageResponse("There is no user with this name. Chose a different name.", ERROR);
        }

        if(!eventRepository.existsByName(eventName)){
            return new MessageResponse("There is no event with this name. Chose a different name.", ERROR);
        }


        Users user = usersRepository.findByUsername(username);
        Event event = eventRepository.findByName(eventName);

        LocalDateTime startDate = event.getStartDate();
        LocalDateTime enrollDate = LocalDateTime.now();


        if(startDate.isBefore(enrollDate)){
            return new MessageResponse("You cannot register for an event after the start date.", ERROR);
        }


        Set<Users> participantSet = event.getParticipants();
        Set<Event> eventSet = user.getEnrolledEvents();
        Set<EventUser> eventUserSet = event.getEnrollments();

        int oldEnrolledUsersCount = participantSet.size();

        eventSet.add(event);
        participantSet.add(user);

        int newEnrolledUsersCount = participantSet.size();
        int quota = event.getQuota();

        if(oldEnrolledUsersCount == newEnrolledUsersCount){
            return new MessageResponse("You already registered to this event.", ERROR);
        }

        if (newEnrolledUsersCount > quota) {
            return new MessageResponse("Quota is full!", ERROR);
        }

        event.setRegisteredUserCount(newEnrolledUsersCount);

        EventUser eventUser = new EventUser(eventName+"/"+username, event);
        eventUserSet.add(eventUser);
        event.setEnrollments(eventUserSet);


        usersRepository.save(user);
        eventRepository.save(event);
        eventUserRepository.save(eventUser);

        return new MessageResponse("You are registered to event!", SUCCESS);
    }




    public MessageResponse leaveFromEvent(String username, String eventName) {

        if(!usersRepository.existsByUsername(username)){
            return new MessageResponse("There is no user with this name. Chose a different name.", ERROR);
        }

        if(!eventRepository.existsByName(eventName)){
            return new MessageResponse("There is no event with this name. Chose a different name.", ERROR);
        }

        if(!eventUserRepository.existsByEventUserName(eventName + "/" + username)){
            return new MessageResponse("There is no enrollment with this event-user name. Chose a different name.", ERROR);
        }


        Users user = usersRepository.findByUsername(username);
        Event event = eventRepository.findByName(eventName);
        EventUser eventUser = eventUserRepository.findByEventUserName(eventName + "/" + username);

        LocalDateTime startDate = event.getStartDate();
        LocalDateTime leaveDate = LocalDateTime.now();

        if(startDate.isBefore(leaveDate)){
            return new MessageResponse("You cannot leave from an event after the start date.", ERROR);
        }

        Set<Users> participantSet = event.getParticipants();
        Set<Event> eventSet = user.getEnrolledEvents();
        //Set<EventUser> eventUserSet = event.getEnrollments();

        if(!eventSet.contains(event)){
            return new MessageResponse("You are not enrolled to this event!", ERROR);
        }

        eventSet.remove(event);
        participantSet.remove(user);
        eventUserRepository.deleteByEventUserName(eventName + "/" + username);

        int registeredUserCount = participantSet.size();
        event.setRegisteredUserCount(registeredUserCount);
        usersRepository.save(user);
        eventRepository.save(event);
        return new MessageResponse("You are unregistered from event!", SUCCESS);
    }


}
