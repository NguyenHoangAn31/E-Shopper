package com.example.security.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(name = "short_name")
    private String shortName;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    // @Override
    // public String toString() {
    //     return "Role{id=" + id + ", name='" + name + "', shortName='" + shortName + "'}";
    // }

}
