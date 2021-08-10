package yte.intern.spring.security.dto;

import lombok.Getter;
import lombok.Setter;
import yte.intern.spring.security.entity.Authority;
import yte.intern.spring.security.entity.Event;
import yte.intern.spring.security.validation.TcIdentificationNumber;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserDTO {

    @Email(message = "This is not a valid Email!")
    @NotBlank(message = "Email field is required!")
    private String email;

    @Size(min=3, max = 32, message = "Username must be at least 3, at most 32 characters!")
    @NotBlank(message = "Username field is required!")
    private String username;

    @Size(min=8, max = 32, message = "Username must be at least 8, at most 32 characters!")
    @NotBlank(message = "Password field is required!")
    private String password;

    private String userRole;

    @Size(min=3, max = 32, message = "Name must be at least 3, at most 32 characters!")
    @NotBlank(message = "Name field is required!")
    private String name;

    @Size(min=3, max = 32, message = "Surname must be at least 3, at most 32 characters!")
    @NotBlank(message = "Surname field is required!")
    private String surname;

    @Size(min = 11, max = 11, message = "TC Identification Number must be 11 characters long!")
    @TcIdentificationNumber(message = "TC Identification Number must be valid!")
    private String tcIdentificationNumber;

    private String imageURL;
}
