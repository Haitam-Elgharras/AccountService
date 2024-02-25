package dev.jam.accountservice.dao.entities;

import dev.jam.accountservice.enumerations.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity @Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String email;
    private Role role;
//    private boolean active;
    @OneToOne(mappedBy = "user")
    private Company company;

    @ManyToMany
    @JoinTable(
            name = "review",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private List<Event> events;




}
