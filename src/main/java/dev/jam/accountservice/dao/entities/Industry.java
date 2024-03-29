package dev.jam.accountservice.dao.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Company> companies;

    public Industry(String label) {
        this.label = label;
    }

}
