package com.example.security.entities;


import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

	private boolean enabled; 

	private String name;

    private String password;

    private String provider;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)

	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@NotEmpty(message = "Please select at least one role")
	private List<Role> roles;

	public List<String> getAuthorities() {
		return roles.stream().map(Role::getShortName).toList();
	}

	public List<SimpleGrantedAuthority> getGrantedAuthorities() {
		return roles.stream().map(o -> new SimpleGrantedAuthority(o.getShortName())).toList();
	}

}
