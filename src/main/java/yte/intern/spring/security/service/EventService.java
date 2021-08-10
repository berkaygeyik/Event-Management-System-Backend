package yte.intern.spring.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yte.intern.spring.security.dto.MessageResponse;

import yte.intern.spring.security.entity.Event;
import yte.intern.spring.security.entity.EventUser;
import yte.intern.spring.security.entity.Users;
import yte.intern.spring.security.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static yte.intern.spring.security.dto.MessageType.SUCCESS;
import static yte.intern.spring.security.dto.MessageType.ERROR;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public List<Event> listAllComments(String userType) {
        LocalDateTime localDateTime = LocalDateTime.now();
        
        if(userType.equals("admin")){
            return eventRepository.findByStartDateOrderByStartDateDesc(localDateTime);
        }
        else if(userType.equals("user")){
            return eventRepository.findByStartDateGreaterThanOrderByStartDateDesc(localDateTime);
        }
        return null;
    }

    public Users getAdminFromEventName(String eventName) {
        if(!eventRepository.existsByName(eventName)){
            return null;
        }
        Event event = eventRepository.findByName(eventName);
        return event.getUsers();
    }

    public MessageResponse addEvent(Event event) {

        if(eventRepository.existsByName(event.getName())){
            System.out.println();
            return new MessageResponse("The event with this Name has been already created. Chose a different name.", ERROR);
        }

        LocalDateTime startDate = event.getStartDate();
        LocalDateTime endDate = event.getEndDate();

        if(!startDate.isBefore(endDate)){
            return new MessageResponse("The start date of the event must be before the end date.", ERROR);
        }

        eventRepository.save(event);

        return new MessageResponse("The event has been successfully added.", SUCCESS);
    }

    public Event getEventByEventName(String eventName) {

        if(!eventRepository.existsByName(eventName)){
            System.out.println("There is no event with this name. Chose a different name.");
            return null;
        }

        return eventRepository.findByName(eventName);
    }

    public MessageResponse updateEvent(String eventName, Event newEvent) {

        if(!eventRepository.existsByName(eventName)){
            return new MessageResponse("There is no event with this name. Chose a different name.", ERROR);
        }

        LocalDateTime startDate = newEvent.getStartDate();
        LocalDateTime endDate = newEvent.getEndDate();
        LocalDateTime updateDate = LocalDateTime.now();

        if(!startDate.isBefore(endDate)){
            return new MessageResponse("The start date of the event must be before the end date.", ERROR);
        }

        if(startDate.isBefore(updateDate)){
            return new MessageResponse("After the start date, the event cannot be updated.", ERROR);
        }

        Event eventFromDB = eventRepository.findByName(eventName);
        newEvent.setId(eventFromDB.getId());

        update(eventFromDB, newEvent);
        eventRepository.save(eventFromDB);

        return new MessageResponse("The event has been successfully updated.", SUCCESS);
    }

    private void update(Event eventFromDB, Event newEvent) {
        eventFromDB.setName(newEvent.getName());
        eventFromDB.setOrganizer(newEvent.getOrganizer());
        eventFromDB.setStartDate(newEvent.getStartDate());
        eventFromDB.setEndDate(newEvent.getEndDate());
        eventFromDB.setDetails(newEvent.getDetails());
        eventFromDB.setLocation(newEvent.getLocation());
        eventFromDB.setAddress(newEvent.getAddress());
        eventFromDB.setQuota(newEvent.getQuota());
        eventFromDB.setImageURL(newEvent.getImageURL());
    }

    public MessageResponse deleteEventByEventName(String eventName) {

        if(!eventRepository.existsByName(eventName)){
            return new MessageResponse("There is no event with this name. Chose a different name.", ERROR);
        }

        Event event = eventRepository.findByName(eventName);

        LocalDateTime startDate = event.getStartDate();
        LocalDateTime deleteDate = LocalDateTime.now();

        if(startDate.isBefore(deleteDate)){
            return new MessageResponse("After the start date, the event cannot be deleted.", ERROR);
        }


        for (Users user : event.getParticipants()) {
            user.getEnrolledEvents().remove(event);
        }
        eventRepository.deleteByName(eventName);

        return new MessageResponse("The event has been successfully deleted.", SUCCESS);
    }

    public List<Users> getEventParticipants(String eventName) {
        if(!eventRepository.existsByName(eventName)){
            System.out.println("There is no event with this name. Chose a different name.");
            return null;
        }

        Event event = eventRepository.findByName(eventName);

        return new ArrayList<>(event.getParticipants());
    }

    public List<Integer> getDayCounts(String eventName) {
        List<Integer> dayCounts = Arrays.asList(0, 0, 0, 0, 0, 0, 0);

        if(!eventRepository.existsByName(eventName)){
            System.out.println("There is no event with this name. Chose a different name.");
        }
        Event event = eventRepository.findByName(eventName);
        for(EventUser enrollment: event.getEnrollments() ){
            int value = enrollment.getCreationDate().getDayOfWeek().getValue();
            dayCounts.set(value-1, dayCounts.get(value-1) +1);
        }
        return dayCounts;
    }


    public MessageResponse giftDraw(String eventName) {
        if(!eventRepository.existsByName(eventName)){
            System.out.println("There is no event with this name. Chose a different name.");
        }
        Event event = eventRepository.findByName(eventName);
        Set<Users> usersSet = event.getParticipants();

        if(usersSet.size() == 0){
            return new MessageResponse("There is not any participant in this event!", ERROR);
        }
        int randomUser = ThreadLocalRandom.current().nextInt(0, usersSet.size());
        int i = 0;
        for(Users user : usersSet)
        {
            if (i == randomUser){
                event.setGiftDrawUser(user.getUsername());
                eventRepository.save(event);
                return new MessageResponse("The gift draw was done. The winner of the gift draw is " + event.getGiftDrawUser() + "." , SUCCESS);
            }
            i++;
        }

        return new MessageResponse("There is an error in gift draw!", ERROR);

    }
}
