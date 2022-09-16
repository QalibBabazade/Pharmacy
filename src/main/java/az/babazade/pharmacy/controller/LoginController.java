package az.babazade.pharmacy.controller;

import az.babazade.pharmacy.dto.request.ReqLogin;
import az.babazade.pharmacy.dto.request.ReqToken;
import az.babazade.pharmacy.dto.response.RespLogin;
import az.babazade.pharmacy.dto.response.RespStatusList;
import az.babazade.pharmacy.dto.response.Response;
import az.babazade.pharmacy.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/login")
    public Response<RespLogin> login(@RequestBody ReqLogin reqLogin)throws Exception{
        return loginService.login(reqLogin);
    }

    @PostMapping(value = "/logout")
    public RespStatusList logout(@RequestBody ReqToken reqToken)throws Exception{
        return loginService.logout(reqToken);
    }

}
