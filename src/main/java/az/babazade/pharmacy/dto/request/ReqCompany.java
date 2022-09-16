package az.babazade.pharmacy.dto.request;

import az.babazade.pharmacy.dto.response.RespLogin;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class ReqCompany {
    private Long companyId;
    private String name;
    private String surname;
    private String address;
    private Date dob;
    private String phone;
    @JsonProperty(value = "login")
    private ReqLogin reqLogin;
}
