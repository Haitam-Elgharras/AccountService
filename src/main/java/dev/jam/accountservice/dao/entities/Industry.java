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
public class Industry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String label;

    @OneToMany(mappedBy = "industry")
    private List<Company> companies;

}
