package az.babazade.pharmacy.service;

import az.babazade.pharmacy.dto.request.ReqCategory;
import az.babazade.pharmacy.dto.request.ReqGeneral;
import az.babazade.pharmacy.dto.request.ReqToken;
import az.babazade.pharmacy.dto.response.RespCategory;
import az.babazade.pharmacy.dto.response.RespStatusList;
import az.babazade.pharmacy.dto.response.Response;

import java.util.List;

public interface CategoryService {

    Response<List<RespCategory>> getCategoryList(ReqToken reqToken) throws Exception;

    Response<RespCategory> getCategoryById(ReqGeneral reqGeneral) throws Exception;

    RespStatusList addCategory(ReqCategory reqCategory) throws Exception;

    RespStatusList updateCategory(ReqCategory reqCategory) throws Exception;

    RespStatusList deleteCategory(ReqGeneral reqGeneral) throws Exception;
}
