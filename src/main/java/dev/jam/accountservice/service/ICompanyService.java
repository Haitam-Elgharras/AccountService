package dev.jam.accountservice.service;

import dev.jam.accountservice.dao.entities.Company;
import dev.jam.accountservice.dao.entities.Event;

import java.util.List;

public interface ICompanyService {
    void addCompany(Company company);
    void deleteCompanyById(long id);
    void updateCompany(Company company);

    List<Company> getAllCompanies();
    Company getCompanyById(long id);
    Company getCompanyByUserId(long userId);
    Company getCompanyByUserEmail(String email);
    Company getCompanyByName(String name);
    void addEventToCompany(long companyId, Event event);

    Company addIndustryToCompany(Long companyId, Long industryId);

    List<Company> getCompaniesByIndustry(Long industryId);

    void deleteIndustryFromCompany(Long companyId, Long industryId);
}
