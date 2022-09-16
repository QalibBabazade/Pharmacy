package az.babazade.pharmacy.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class RespCompany {

    private Long companyId;
    private String name;
    private String surname;
    private String address;
    private Date dob;
    private String phone;
    @JsonProperty(value = "login")
    private RespLogin respLogin;
}
