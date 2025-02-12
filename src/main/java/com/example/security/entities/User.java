package com.example.security.entities;

import java.time.LocalDate;
import java.util.ArrayList;
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
public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true)
	private String email;

	private boolean enabled;

	private String name;

	private String phone;

	private String password;

	private String provider;

	private LocalDate birthday;

	private String imageUrl;

	private String Ward;

	private String District;

	private String Province;

	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)

	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@NotEmpty(message = "Please select at least one role")
	private List<Role> roles;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Cart> carts = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Order> orders = new ArrayList<>();

	public List<String> getAuthorities() {
		return roles.stream().map(Role::getShortName).toList();
	}

	public List<SimpleGrantedAuthority> getGrantedAuthorities() {
		return roles.stream().map(o -> new SimpleGrantedAuthority(o.getShortName())).toList();
	}

}
