package com.example.security.dto.user;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private int id;
    private String email;
    private boolean enabled;

    @NotBlank(message = "Name is required!")
    private String name;

    @NotBlank(message = "Phone is required!")
    private String phone;

    private String provider;

    @NotBlank(message = "Birthday is required!")
    private String birthday;  

    private String imageUrl;

    @NotBlank(message = "Address is required!")
    private String address;

    @NotBlank(message = "Ward is required!")
    private String ward;

    @NotBlank(message = "District is required!")
    private String district;

    @NotBlank(message = "Province is required!")
    private String province;
}
