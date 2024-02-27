package dev.jam.accountservice.dao.repositories;

import dev.jam.accountservice.dao.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // get category by name
    Category findByName(String name);

}
