package dev.jam.accountservice.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    private String description;
//    private String logoImageUrl;

    public Company(String name, String address, String tel, String email, String description) {
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.email = email;
        this.description = description;
    }

    @OneToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserAccount userAccount;

    @OneToMany(mappedBy = "company")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Event> events;

//    @Transient
    // @OneToOne(mappedBy = "company")
    // private Location location;

    @ManyToOne
    private Industry industry;

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
