package com.example.security.dto.user;

import java.time.LocalDate;

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

	private String name;

	private String provider;

	private LocalDate birthday;

	private String imageUrl;

	private String Ward;

	private String District;

	private String Province;

}
