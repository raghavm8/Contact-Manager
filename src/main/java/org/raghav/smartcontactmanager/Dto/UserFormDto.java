package org.raghav.smartcontactmanager.Dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserFormDto {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone is required")
    @Size(min = 10, max = 10, message = "Phone number must be between 10 and 15 digits")
    @Pattern(regexp = "^[0-9]+$", message = "Phone number must contain only digits")
    private String phone;

    @NotBlank(message = "Password is required")
    @Size(min = 1, max = 20, message = "Password must be between 6 and 20 characters")
    // @Pattern(regexp =
    // "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,20}$",
    // message = "Password must contain at least one letter, one number, and one
    // special character")
    private String password;

    @Size(max = 200, message = "About section cannot exceed 200 characters")
    private String about;
}
