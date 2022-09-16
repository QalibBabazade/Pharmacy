package az.babazade.pharmacy.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RespSales {

    private Long salesId;
    private Integer boxNumber;
    private Double amount;
    @JsonProperty(value = "drug")
    private RespDrug respDrug;
}
