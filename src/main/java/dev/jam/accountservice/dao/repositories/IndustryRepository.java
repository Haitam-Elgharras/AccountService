package dev.jam.accountservice.dao.repositories;

import dev.jam.accountservice.dao.entities.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, Long> {
}
