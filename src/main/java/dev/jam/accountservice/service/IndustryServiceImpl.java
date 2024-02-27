package dev.jam.accountservice.service;

import dev.jam.accountservice.dao.entities.Industry;
import dev.jam.accountservice.dao.repositories.IndustryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndustryServiceImpl implements IIndustryService{

    private final IndustryRepository industryRepository;

    public IndustryServiceImpl(IndustryRepository industryRepository) {
        this.industryRepository = industryRepository;
    }

    @Override
    public List<Industry> getAllIndustries() {
        return industryRepository.findAll();
    }

    @Override
    public Industry getIndustryById(Long id) {
        return industryRepository.findById(id).orElse(null);
    }

    @Override
    public Industry addIndustry(Industry industry) {
        return industryRepository.save(industry);
    }

    @Override
    public void deleteIndustry(Long id) {
        industryRepository.deleteById(id);
    }
}