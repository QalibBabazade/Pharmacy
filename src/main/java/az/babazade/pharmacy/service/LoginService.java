package az.babazade.pharmacy.service;

import az.babazade.pharmacy.dto.request.ReqLogin;
import az.babazade.pharmacy.dto.request.ReqToken;
import az.babazade.pharmacy.dto.response.RespLogin;
import az.babazade.pharmacy.dto.response.RespStatusList;
import az.babazade.pharmacy.dto.response.Response;

public interface LoginService {

    Response<RespLogin> login(ReqLogin reqLogin) throws Exception;

    RespStatusList logout(ReqToken reqToken) throws Exception;
}
