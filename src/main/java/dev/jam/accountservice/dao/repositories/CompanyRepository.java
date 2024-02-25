package dev.jam.accountservice.dao.repositories;

import dev.jam.accountservice.dao.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByName(String name);
    Optional<Company> findByEmail(String email);
}
