package az.babazade.pharmacy.service.impl;

import az.babazade.pharmacy.dto.request.ReqLogin;
import az.babazade.pharmacy.dto.request.ReqToken;
import az.babazade.pharmacy.dto.response.RespLogin;
import az.babazade.pharmacy.dto.response.RespStatus;
import az.babazade.pharmacy.dto.response.RespStatusList;
import az.babazade.pharmacy.dto.response.Response;
import az.babazade.pharmacy.entity.Login;
import az.babazade.pharmacy.enums.EnumAvailableStatus;
import az.babazade.pharmacy.exception.ExceptionConstants;
import az.babazade.pharmacy.exception.PharmacyException;
import az.babazade.pharmacy.repository.LoginDao;
import az.babazade.pharmacy.service.LoginService;
import az.babazade.pharmacy.util.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginDao loginDao;

    @Autowired
    private Utility utility;

    @Autowired
    private HttpServletRequest request;

    private static final Logger LOGGER = LogManager.getLogger(LoginServiceImpl.class);

    /*************************** Login *****************************/
    @Override
    public Response<RespLogin> login(ReqLogin reqLogin) {
        Response<RespLogin> response = new Response<>();
        RespLogin respLogin = new RespLogin();
        try{
            LOGGER.info("Client Ip: "+utility.getClientIp(request)+" ,Login request: "+reqLogin);
            String username = reqLogin.getUsername();
            String password = reqLogin.getPassword();
            /*****Check Username and Password******/
            if(username == null || password == null){
                throw new PharmacyException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data!");
            }
            Login login= loginDao.findLoginByUsernameAndPasswordAndActive(username,password, EnumAvailableStatus.ACTIVE.getValue());
            /*****Check Login******/
            if(login == null){
                throw new PharmacyException(ExceptionConstants.USER_NOT_FOUND,"User not found!");
            }
            if(login.getToken() != null){
                throw new PharmacyException(ExceptionConstants.USER_IS_ALREADY_IN_SESSION,"User is already in session!");
            }
            String token = UUID.randomUUID().toString();
            login.setToken(token);
            login.setDataDate(new java.util.Date());
            loginDao.save(login);
            respLogin.setUserId(login.getId());
            respLogin.setUsername(login.getUsername());
            respLogin.setToken(login.getToken());
            respLogin.setLoginDate(login.getDataDate());
            response.setT(respLogin);
            response.setRespStatus(RespStatus.getSuccessMessage());
        }catch (PharmacyException ex){
            response.setRespStatus(new RespStatus(ex.getCode(),ex.getMessage()));
           ex.printStackTrace();
        } catch (Exception ex){
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION,"Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    /*************************** Logout ***********************************/
    @Override
    public RespStatusList logout(ReqToken reqToken) {
        RespStatusList response = new RespStatusList();
        try{
            Long userId = reqToken.getUserId();
            String token = reqToken.getToken();
            /*****Check Token******/
            utility.checkToken(userId,token);
            Login login = loginDao.findLoginByIdAndTokenAndActive(userId,token,EnumAvailableStatus.ACTIVE.getValue());
            /*****Check Login******/
            utility.checkLogin(login);
            login.setToken(null);
            loginDao.save(login);
            response.setRespStatus(RespStatus.getSuccessMessage());
        }catch (PharmacyException ex){
            response.setRespStatus(new RespStatus(ex.getCode(),ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex){
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION,"Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }
}
