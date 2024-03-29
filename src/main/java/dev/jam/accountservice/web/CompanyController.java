package dev.jam.accountservice.web;

import dev.jam.accountservice.dao.entities.Company;
import dev.jam.accountservice.dao.entities.UserAccount;
import dev.jam.accountservice.enumerations.Role;
import dev.jam.accountservice.exceptions.CompanyNotFoundException;
import dev.jam.accountservice.exceptions.UserAccessDeniedException;
import dev.jam.accountservice.exceptions.UserNotFoundException;
import dev.jam.accountservice.service.ICompanyService;
import dev.jam.accountservice.service.IUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    // for users and company owners
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_COMPANY_OWNER')")
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/{companyId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_COMPANY_OWNER')")
    public Company getCompanyById(@PathVariable Long companyId) {
        return companyService.getCompanyById(companyId);
    }


    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_COMPANY_OWNER')")
    public Company getCompanyByUserId(@PathVariable Long userId) {
        Company company = companyService.getCompanyByUserId(userId);
        if (company == null) {
            throw new CompanyNotFoundException("Company with user id " + userId + " not found");
        }
        return company;
    }

    @DeleteMapping("/{companyId}")
    @PreAuthorize("hasRole('ROLE_COMPANY_OWNER')")
    public void deleteCompany(@PathVariable Long companyId) {
        // check if company exists
        Company company = companyService.getCompanyById(companyId);
        if (company == null) {
            throw new CompanyNotFoundException("Company with id " + companyId + " not found");
        }

        // get authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = auth.getName();

        // check if the authenticated user is the owner of the company
        if (!company.getUserAccount().getUsername().equals(authenticatedUsername)) {
            System.out.println("Authenticated user is not the owner of the company");
            throw new UserAccessDeniedException("Access Denied");
        }

        companyService.deleteCompanyById(companyId);
        // remove role from user
        UserAccount userAccount = userService.getUserByEmail(authenticatedUsername);
        userAccount.setRole(Role.ROLE_USER);

        userService.updateUser(userAccount);
    }

    @PutMapping("/{companyId}")
    @PreAuthorize("hasRole('ROLE_COMPANY_OWNER')")
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

        company.setId(companyId);
        companyService.updateCompany(company);
    }

    @PostMapping("/user/{userId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Company addCompanyToUser(@PathVariable Long userId, @RequestBody Company company) {
        UserAccount userAccount = userService.getUserById(userId);
        if (userAccount == null)
            throw new UserNotFoundException("User with id " + userId + " not found");

        company.setUserAccount(userAccount);
        companyService.addCompany(company);

        // add role to user
        userAccount.setRole(Role.ROLE_COMPANY_OWNER);

        return company;
    }

    @PostMapping("/{companyId}/industry/{industryId}")
    @PreAuthorize("hasRole('ROLE_COMPANY_OWNER')")
    public Company addIndustryToCompany(@PathVariable Long companyId, @PathVariable Long industryId) {
// check if the auth user is the owner of the company
        Company company = companyService.getCompanyById(companyId);
        if (company == null)
            throw new CompanyNotFoundException("Company with id " + companyId + " not found");

        String authUsername= getAuthUsername();

        if (!company.getUserAccount().getUsername().equals(authUsername))
            throw new UserAccessDeniedException("Access Denied");

        return companyService.addIndustryToCompany(companyId, industryId);
    }

    private String getAuthUsername() {
        // get authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    // delete industry from company
    @DeleteMapping("/{companyId}/industry/{industryId}")
    @PreAuthorize("hasRole('ROLE_COMPANY_OWNER')")
    public void deleteIndustryFromCompany(@PathVariable Long companyId, @PathVariable Long industryId) {
        String authUsername= getAuthUsername();
        Company company = companyService.getCompanyById(companyId);
        if (company == null)
            throw new CompanyNotFoundException("Company with id " + companyId + " not found");

        if (!company.getUserAccount().getUsername().equals(authUsername))
            throw new UserAccessDeniedException("Access Denied");

        companyService.deleteIndustryFromCompany(companyId, industryId);
    }

    @GetMapping("/industry/{industryId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_COMPANY_OWNER')")
    public List<Company> getCompaniesByIndustry(@PathVariable Long industryId) {
        return companyService.getCompaniesByIndustry(industryId);
    }

}
