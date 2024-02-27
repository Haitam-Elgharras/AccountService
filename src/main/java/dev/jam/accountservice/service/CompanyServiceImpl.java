package dev.jam.accountservice.service;

import dev.jam.accountservice.dao.entities.Company;
import dev.jam.accountservice.dao.entities.Event;
import dev.jam.accountservice.dao.entities.Industry;
import dev.jam.accountservice.dao.repositories.CompanyRepository;
import dev.jam.accountservice.dao.repositories.IndustryRepository;
import dev.jam.accountservice.exceptions.CompanyNotFoundException;
import dev.jam.accountservice.exceptions.IndustryNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements ICompanyService {
    private final CompanyRepository companyRepository;
    private final IndustryRepository industryRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository, IndustryRepository industryRepository) {
        this.companyRepository = companyRepository;
        this.industryRepository = industryRepository;
    }

    @Override
    public void addCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public void deleteCompanyById(long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public void updateCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public Company getCompanyByName(String name) {
        return companyRepository.findByName(name).orElse(null);
    }

    @Override
    public void addEventToCompany(long companyId, Event event) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company with id " + companyId + " not found"));

        company.getEvents().add(event);
        event.setCompany(company);
    }

    @Override
    public Company getCompanyByUserId(long userId) {
        return companyRepository.findByUserId(userId).orElse(null);
    }

    @Override
    public Company addIndustryToCompany(Long companyId, Long industryId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new CompanyNotFoundException("Company with id " + companyId + " not found"));
        Industry industry = industryRepository.findById(industryId).orElseThrow(() -> new IndustryNotFoundException("Industry with id " + industryId + " not found"));

        company.setIndustry(industry);
        return companyRepository.save(company);
    }

    @Override
    public void deleteIndustryFromCompany(Long companyId, Long industryId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() ->
                new CompanyNotFoundException("Company with id " + companyId + " not found"));

        industryRepository.findById(industryId).orElseThrow(() ->
                new IndustryNotFoundException("Industry with id " + industryId + " not found"));

        company.setIndustry(null);
        companyRepository.save(company);
    }

    @Override
    public List<Company> getCompaniesByIndustry(Long industryId) {
        industryRepository.findById(industryId).orElseThrow(() -> new IndustryNotFoundException("Industry with id " + industryId + " not found"));

        return companyRepository.findByIndustryId(industryId);
    }




}
