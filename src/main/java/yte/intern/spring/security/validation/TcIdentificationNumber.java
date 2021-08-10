package yte.intern.spring.security.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TcIdentificationNumberValidator.class)
public @interface TcIdentificationNumber {
    String message() default "TC Identification Number format is not appropriate !";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
