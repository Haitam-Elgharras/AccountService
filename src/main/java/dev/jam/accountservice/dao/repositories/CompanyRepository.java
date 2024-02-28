package dev.jam.accountservice.dao.repositories;

import dev.jam.accountservice.dao.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByName(String name);
    Optional<Company> findByEmail(String email);
    Optional<Company> findByUserId(Long userId);
    Optional<Company> findByUserEmail(String email);

    // find companies by industryId
    List<Company> findByIndustryId(Long industryId);
}
