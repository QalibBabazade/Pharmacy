package az.babazade.pharmacy.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
@Data
public class RespLogin {

    private Long userId;
    private String username;
    private String token;
    private Date loginDate;
    @JsonProperty(value = "role")
    private RespRole respRole;
}
