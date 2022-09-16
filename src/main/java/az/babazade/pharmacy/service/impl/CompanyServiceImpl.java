package az.babazade.pharmacy.service.impl;

import az.babazade.pharmacy.dto.request.ReqCompany;
import az.babazade.pharmacy.dto.request.ReqGeneral;
import az.babazade.pharmacy.dto.request.ReqToken;
import az.babazade.pharmacy.dto.response.*;
import az.babazade.pharmacy.entity.Company;
import az.babazade.pharmacy.entity.Login;
import az.babazade.pharmacy.entity.Role;
import az.babazade.pharmacy.enums.EnumAvailableRole;
import az.babazade.pharmacy.enums.EnumAvailableStatus;
import az.babazade.pharmacy.exception.ExceptionConstants;
import az.babazade.pharmacy.exception.PharmacyException;
import az.babazade.pharmacy.repository.CompanyDao;
import az.babazade.pharmacy.repository.LoginDao;
import az.babazade.pharmacy.repository.RoleDao;
import az.babazade.pharmacy.service.CompanyService;
import az.babazade.pharmacy.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private LoginDao loginDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private Utility utility;

    /********************* Get Company List ***************************/
    @Override
    public Response<List<RespCompany>> getCompanyList(ReqToken reqToken) throws Exception {
        Response<List<RespCompany>> response = new Response<>();
        List<RespCompany> respCompanyList = new ArrayList<>();
        try {
            Long userId = reqToken.getUserId();
            String token = reqToken.getToken();
            /****** Check Token *******/
            utility.checkToken(userId, token);
            List<Company> companyList = companyDao.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            /************ Check Company List *************/
            if (companyList.isEmpty()) {
                throw new PharmacyException(ExceptionConstants.COMPANY_NOT_FOUND, "Company not found!");
            }
            for (Company company : companyList) {
                RespCompany respCompany = new RespCompany();
                RespLogin respLogin = new RespLogin();
                RespRole respRole = new RespRole();
                /*** Role Name ***/
                respRole.setName(company.getLogin().getRole().getName());
                /**** Response Login ****/
                respLogin.setUsername(company.getLogin().getUsername());
                respLogin.setToken(company.getLogin().getToken());
                respLogin.setLoginDate(company.getLogin().getDataDate());
                respLogin.setRespRole(respRole);
                respCompany.setCompanyId(company.getId());
                respCompany.setName(company.getName());
                respCompany.setSurname(company.getSurname());
                respCompany.setAddress(company.getAddress());
                respCompany.setDob(company.getDob());
                respCompany.setPhone(company.getPhone());
                respCompany.setRespLogin(respLogin);
                respCompanyList.add(respCompany);
            }
            response.setT(respCompanyList);
            response.setRespStatus(RespStatus.getSuccessMessage());

        } catch (PharmacyException ex) {
            response.setRespStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    /*********************** Get Company By Id ***************************/
    @Override
    public Response<RespCompany> getCompanyById(ReqGeneral reqGeneral) throws Exception {
        Response<RespCompany> response = new Response<>();
        try {
            Long companyId = reqGeneral.getId();
            ReqToken reqToken = reqGeneral.getReqToken();
            /******* Check Company Id ********/
            /******** Check Id ********/
            utility.checkId(companyId);
            /********Check ReqToken*********/
            if (reqToken == null) {
                throw new PharmacyException(ExceptionConstants.EMPTY_TOKEN, "Empty token!");
            }
            Long userId = reqGeneral.getReqToken().getUserId();
            String token = reqGeneral.getReqToken().getToken();
            /***** Check Token ******/
            utility.checkToken(userId, token);
            Company company = companyDao.findCompanyByIdAndActive(companyId, EnumAvailableStatus.ACTIVE.getValue());
            if (company == null) {
                throw new PharmacyException(ExceptionConstants.COMPANY_NOT_FOUND, "Company not found!");
            }
            RespCompany respCompany = new RespCompany();
            RespLogin respLogin = new RespLogin();
            RespRole respRole = new RespRole();
            /*** Role Name ***/
            respRole.setRoleId(company.getLogin().getRole().getId());
            respRole.setName(company.getLogin().getRole().getName());
            /**** Response Login ****/
            respLogin.setUserId(company.getLogin().getId());
            respLogin.setUsername(company.getLogin().getUsername());
            respLogin.setToken(company.getLogin().getToken());
            respLogin.setLoginDate(company.getLogin().getDataDate());
            respLogin.setRespRole(respRole);
            respCompany.setCompanyId(company.getId());
            respCompany.setName(company.getName());
            respCompany.setSurname(company.getSurname());
            respCompany.setAddress(company.getAddress());
            respCompany.setDob(company.getDob());
            respCompany.setPhone(company.getPhone());
            respCompany.setRespLogin(respLogin);
            response.setT(respCompany);
            response.setRespStatus(RespStatus.getSuccessMessage());
        } catch (PharmacyException ex) {
            response.setRespStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    /*************************** Add Company *********************************/
    @Override
    public RespStatusList addCompany(ReqCompany reqCompany) throws Exception {
        RespStatusList response = new RespStatusList();
        try {
            String name = reqCompany.getName();
            String surname = reqCompany.getSurname();
            String address = reqCompany.getAddress();
            Date dob = reqCompany.getDob();
            String phone = reqCompany.getPhone();
            String username = reqCompany.getReqLogin().getUsername();
            String password = reqCompany.getReqLogin().getPassword();
            utility.checkCompany(name, surname, username, password);
            Login log = loginDao.findLoginByUsernameAndPasswordAndActive(username, password, EnumAvailableStatus.ACTIVE.getValue());
            if (log != null) {
                throw new PharmacyException(ExceptionConstants.AVAILABLE_USERNAME, "Available username!");
            }
            Company company = new Company();
            Login login = new Login();
            Role role = roleDao.findRoleByNameAndActive(EnumAvailableRole.USER.getValue(), EnumAvailableStatus.ACTIVE.getValue());
            login.setUsername(username);
            login.setPassword(password);
            login.setRole(role);
            loginDao.save(login);
            company.setName(name);
            company.setSurname(surname);
            company.setAddress(address);
            company.setDob(dob);
            company.setPhone(phone);
            company.setLogin(login);
            companyDao.save(company);
            response.setRespStatus(RespStatus.getSuccessMessage());
        } catch (PharmacyException ex) {
            response.setRespStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    /**************************** Update Company *******************************/
    @Override
    public RespStatusList updateCompany(ReqCompany reqCompany) throws Exception {
        RespStatusList response = new RespStatusList();
        try {
            Long companyId = reqCompany.getCompanyId();
            String name = reqCompany.getName();
            String surname = reqCompany.getSurname();
            String address = reqCompany.getAddress();
            Date dob = reqCompany.getDob();
            String phone = reqCompany.getPhone();
            utility.checkUpdate(companyId, name, surname);
            Company company = companyDao.findCompanyByIdAndActive(companyId, EnumAvailableStatus.ACTIVE.getValue());
            if(company == null){
                throw new PharmacyException(ExceptionConstants.COMPANY_NOT_FOUND,"Company not found!");
            }
            company.setName(name);
            company.setSurname(surname);
            company.setAddress(address);
            company.setDob(dob);
            company.setPhone(phone);
            companyDao.save(company);
            response.setRespStatus(RespStatus.getSuccessMessage());
        } catch (PharmacyException ex) {
            response.setRespStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    /*********************** Delete Company ****************************/
    @Override
    public RespStatusList deleteCompany(ReqGeneral reqGeneral) throws Exception {
        RespStatusList response = new RespStatusList();
        try {
            Long companyId = reqGeneral.getId();
            ReqToken reqToken = reqGeneral.getReqToken();
            /***** Check ReqToken ******/
            if (reqToken == null) {
                throw new PharmacyException(ExceptionConstants.EMPTY_TOKEN, "Empty token!");
            }
            Long userId = reqGeneral.getReqToken().getUserId();
            String token = reqGeneral.getReqToken().getToken();
            /***** Check Token ******/
            utility.checkToken(userId, token);
            /***** Check Company Id *****/
            /******** Check Id ********/
            utility.checkId(companyId);
            Company company = companyDao.findCompanyByIdAndActive(companyId, EnumAvailableStatus.ACTIVE.getValue());
            if(company == null){
                throw new PharmacyException(ExceptionConstants.COMPANY_NOT_FOUND,"Company not found!");
            }
            company.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            companyDao.save(company);
            response.setRespStatus(RespStatus.getSuccessMessage());

        } catch (PharmacyException ex) {
            response.setRespStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }
}
