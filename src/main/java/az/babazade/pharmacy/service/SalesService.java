package az.babazade.pharmacy.service;


import az.babazade.pharmacy.dto.request.ReqGeneral;
import az.babazade.pharmacy.dto.request.ReqSales;
import az.babazade.pharmacy.dto.request.ReqToken;
import az.babazade.pharmacy.dto.response.RespSales;
import az.babazade.pharmacy.dto.response.RespStatusList;
import az.babazade.pharmacy.dto.response.Response;

import java.util.List;


public interface SalesService {

    Response<List<RespSales>> getSalesList(ReqToken reqToken) throws Exception;

    Response<RespSales> getSalesById(ReqGeneral reqGeneral) throws Exception;

    RespStatusList addSales(ReqSales reqSales) throws Exception;

    RespStatusList updateSales(ReqSales reqSales) throws Exception;

    RespStatusList deleteSales(ReqGeneral reqGeneral) throws Exception;
}
