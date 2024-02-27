package dev.jam.accountservice.web;

import dev.jam.accountservice.dao.entities.Company;
import dev.jam.accountservice.dao.entities.User;
import dev.jam.accountservice.exceptions.CompanyNotFoundException;
import dev.jam.accountservice.exceptions.UserNotFoundException;
import dev.jam.accountservice.service.ICompanyService;
import dev.jam.accountservice.service.IUserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import dev.jam.accountservice.dao.repositories.UserRepository;

import java.util.List;

@Transactional
@RestController
@RequestMapping(value="/companies")
public class CompanyController {
    private final IUserService userService;
    private final ICompanyService companyService;

    public CompanyController(IUserService userService, ICompanyService companyService) {
        this.userService = userService;
        this.companyService = companyService;
    }

    @GetMapping
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/{companyId}")
    public Company getCompanyById(@PathVariable Long companyId) {
        return companyService.getCompanyById(companyId);
    }


    @GetMapping("/user/{userId}")
    public Company getCompanyByUserId(@PathVariable Long userId) {
        Company company = companyService.getCompanyByUserId(userId);
        if (company == null) {
            throw new CompanyNotFoundException("Company with user id " + userId + " not found");
        }
        return company;
    }

    @DeleteMapping("/{companyId}")
    public void deleteCompany(@PathVariable Long companyId) {
        // check if company exists
        Company company = companyService.getCompanyById(companyId);
        if (company == null) {
            throw new CompanyNotFoundException("Company with id " + companyId + " not found");
        }
        companyService.deleteCompanyById(companyId);
    }

    @PutMapping("/{companyId}")
    public void updateCompany(@PathVariable Long companyId, @RequestBody Company companyDetails) {
        Company company = companyService.getCompanyById(companyId);
        if (company == null)
            throw new CompanyNotFoundException("Company with id " + companyId + " not found");

        if (companyDetails.getName() != null)
            company.setName(companyDetails.getName());
        if (companyDetails.getAddress() != null)
            company.setAddress(companyDetails.getAddress());
        if (companyDetails.getDescription() != null)
            company.setDescription(companyDetails.getDescription());
        if (companyDetails.getTel() != null)
            company.setTel(companyDetails.getTel());
        if (companyDetails.getEmail() != null)
            company.setEmail(companyDetails.getEmail());

        companyService.updateCompany(company);
    }

    @PostMapping("/{userId}")
    public Company addCompanyToUser(@PathVariable Long userId, @RequestBody Company company) {
        User user = userService.getUserById(userId);
        if (user == null)
            throw new UserNotFoundException("User with id " + userId + " not found");

        company.setUser(user);
        companyService.addCompany(company);

        return company;
    }

    @PostMapping("/{companyId}/industry/{industryId}")
    public Company addIndustryToCompany(@PathVariable Long companyId, @PathVariable Long industryId) {
        return companyService.addIndustryToCompany(companyId, industryId);
    }

    // delete industry from company
    @DeleteMapping("/{companyId}/industry/{industryId}")
    public void deleteIndustryFromCompany(@PathVariable Long companyId, @PathVariable Long industryId) {
        companyService.deleteIndustryFromCompany(companyId, industryId);
    }

    @GetMapping("/industry/{industryId}")
    public List<Company> getCompaniesByIndustry(@PathVariable Long industryId) {
        return companyService.getCompaniesByIndustry(industryId);
    }

}
