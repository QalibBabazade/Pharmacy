package az.babazade.pharmacy.service;

import az.babazade.pharmacy.dto.request.ReqConnector;
import az.babazade.pharmacy.dto.request.ReqGeneral;
import az.babazade.pharmacy.dto.request.ReqToken;
import az.babazade.pharmacy.dto.response.RespConnector;
import az.babazade.pharmacy.dto.response.RespStatusList;
import az.babazade.pharmacy.dto.response.Response;

import java.util.List;

public interface ConnectorService {

    Response<List<RespConnector>> getConnectorList(ReqToken reqToken) throws Exception;

    Response<RespConnector> getConnectorById(ReqGeneral reqGeneral) throws Exception;

    RespStatusList addConnector(ReqConnector reqConnector) throws Exception;

    RespStatusList updateConnector(ReqConnector reqConnector) throws Exception;

    RespStatusList deleteConnector(ReqGeneral reqGeneral) throws Exception;
}
