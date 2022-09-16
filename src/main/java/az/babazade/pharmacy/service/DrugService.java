package az.babazade.pharmacy.service;

import az.babazade.pharmacy.dto.request.ReqDrug;
import az.babazade.pharmacy.dto.request.ReqGeneral;
import az.babazade.pharmacy.dto.request.ReqToken;
import az.babazade.pharmacy.dto.response.RespDrug;
import az.babazade.pharmacy.dto.response.RespStatusList;
import az.babazade.pharmacy.dto.response.Response;

import java.util.List;


public interface DrugService {

    Response<List<RespDrug>> getDrugList(ReqToken reqToken) throws Exception;

    Response<RespDrug> getDrugById(ReqGeneral reqGeneral) throws Exception;

    RespStatusList addDrug(ReqDrug reqDrug) throws Exception;

    RespStatusList updateDrug(ReqDrug reqDrug) throws Exception;

    RespStatusList deleteDrug(ReqGeneral reqGeneral) throws Exception;
}
