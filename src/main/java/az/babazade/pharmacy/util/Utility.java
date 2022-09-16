package az.babazade.pharmacy.util;

import az.babazade.pharmacy.dto.request.ReqGeneral;
import az.babazade.pharmacy.dto.request.ReqToken;
import az.babazade.pharmacy.entity.Login;
import az.babazade.pharmacy.enums.EnumAvailableStatus;
import az.babazade.pharmacy.exception.ExceptionConstants;
import az.babazade.pharmacy.exception.PharmacyException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class Utility {

    public void checkToken(Long userId,String token){
        if(userId == null || token == null){
            throw new PharmacyException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data!");
        }
    }
    public void checkLogin(Login login){
        if(login == null){
            throw new PharmacyException(ExceptionConstants.INVALID_TOKEN,"Invalid token!");
        }
        if(login.getActive() == EnumAvailableStatus.DEACTIVE.getValue()){
            throw new PharmacyException(ExceptionConstants.TOKEN_EXPIRED,"Token expired!");
        }
    }

    public void checkGeneral(ReqGeneral reqGeneral){
        Long drugId = reqGeneral.getId();
        ReqToken reqToken = reqGeneral.getReqToken();
        /***** Check ReqToken ******/
        if (reqToken == null) {
            throw new PharmacyException(ExceptionConstants.EMPTY_TOKEN, "Empty token!");
        }
        /***** Check Drug Id *****/
        if (drugId == null) {
            throw new PharmacyException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data!");
        }
    }

    public void checkCompany(String name,String surname,String username,String password){
        if(name == null || surname == null || username == null || password == null){
            throw new PharmacyException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data!");
        }
    }

    public void checkUpdate(Long id, String name, String surname) {
        if(id == null || name == null || surname == null ){
            throw new PharmacyException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data!");
        }
    }

    public void checkId(Long id){
        if(id == null){
            throw new PharmacyException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data!");
        }
    }

    public String getClientIp(HttpServletRequest request){
        return request.getRemoteAddr();
    }
}
