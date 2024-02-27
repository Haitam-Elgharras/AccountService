package dev.jam.accountservice.service;

import dev.jam.accountservice.dao.entities.Industry;

import java.util.List;

public interface IIndustryService {
    List<Industry> getAllIndustries();

    Industry getIndustryById(Long id);

    Industry addIndustry(Industry industry);

    void deleteIndustry(Long id);
}
