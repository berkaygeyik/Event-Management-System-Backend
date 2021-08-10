package yte.intern.spring.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParticipantDetailsDTO {
    private String eventName;
    private String nameSurname;
    private String username;
    private String userEmail;
}
