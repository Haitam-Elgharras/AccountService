package dev.jam.accountservice.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String tel;
    private String email;
    @OneToOne
    private User user;

    @OneToMany(mappedBy = "company")
    private List<Event> events;

    // @OneToOne(mappedBy = "company")
    // private Location location;

}
