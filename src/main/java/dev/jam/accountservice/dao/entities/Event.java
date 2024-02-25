package dev.jam.accountservice.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
//    @Transient
//    private Location location;
//    @Transient
//    private File file;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToMany(mappedBy = "events")
    private List<User> users;

    @ManyToOne
    private Company company;
}
