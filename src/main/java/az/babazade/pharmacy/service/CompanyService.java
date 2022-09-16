package az.babazade.pharmacy.service;

import az.babazade.pharmacy.dto.request.ReqCompany;
import az.babazade.pharmacy.dto.request.ReqGeneral;
import az.babazade.pharmacy.dto.request.ReqToken;
import az.babazade.pharmacy.dto.response.RespCompany;
import az.babazade.pharmacy.dto.response.RespStatusList;
import az.babazade.pharmacy.dto.response.Response;

import java.util.List;

public interface CompanyService {

    Response<List<RespCompany>> getCompanyList(ReqToken reqToken) throws Exception;

    Response<RespCompany> getCompanyById(ReqGeneral reqGeneral) throws Exception;

    RespStatusList addCompany(ReqCompany reqCompany) throws Exception;

    RespStatusList updateCompany(ReqCompany reqCompany) throws Exception;

    RespStatusList deleteCompany(ReqGeneral reqGeneral) throws Exception;
}
