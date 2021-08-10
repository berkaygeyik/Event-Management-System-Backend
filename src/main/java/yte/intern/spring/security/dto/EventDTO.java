package yte.intern.spring.security.dto;

import lombok.Getter;
import lombok.Setter;
import yte.intern.spring.security.entity.Users;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class EventDTO {

    @Size(max = 64, message = "Event name can't be longer than 64 character")
    private String name;

    @Size(max = 64, message = "Organizer name can't be longer than 64 character")
    private String organizer;

    private String eventAdmin;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String details;

    @Size(max = 64, message = "Location can't be longer than 64 character")
    private String location;

    @Size(max = 64, message = "Address can't be longer than 64 character")
    private String address;

    private int quota;

    @Max(value = 10, message = "The number of participants of the event cannot exceed the quota.")
    private int registeredUserCount;

    private String imageURL;

    private String giftDrawUser;

}
