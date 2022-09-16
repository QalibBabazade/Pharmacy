package az.babazade.pharmacy.controller;

import az.babazade.pharmacy.dto.request.ReqCompany;
import az.babazade.pharmacy.dto.request.ReqGeneral;
import az.babazade.pharmacy.dto.request.ReqToken;
import az.babazade.pharmacy.dto.response.RespCompany;
import az.babazade.pharmacy.dto.response.RespStatusList;
import az.babazade.pharmacy.dto.response.Response;
import az.babazade.pharmacy.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/GetCompanyList")
    public Response<List<RespCompany>> getCompanyList(@RequestBody ReqToken reqToken) throws Exception {
        return companyService.getCompanyList(reqToken);
    }

    @PostMapping(value = "/GetCompanyById")
    public Response<RespCompany> getCompanyById(@RequestBody ReqGeneral reqGeneral) throws Exception {
        return companyService.getCompanyById(reqGeneral);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/AddCompany")
    public RespStatusList addCompany(@RequestBody ReqCompany reqCompany) throws Exception {
        return companyService.addCompany(reqCompany);
    }

    @PostMapping(value = "/UpdateCompany")
    public RespStatusList updateCompany(@RequestBody ReqCompany reqCompany) throws Exception {
        return companyService.updateCompany(reqCompany);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/DeleteCompany")
    public RespStatusList deleteCompany(@RequestBody ReqGeneral reqGeneral) throws Exception {
        return companyService.deleteCompany(reqGeneral);
    }
}
