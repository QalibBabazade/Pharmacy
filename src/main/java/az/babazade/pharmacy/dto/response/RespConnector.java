package az.babazade.pharmacy.dto.response;

import az.babazade.pharmacy.entity.Category;
import az.babazade.pharmacy.entity.Drug;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RespConnector {

    private Long connectorId;
    @JsonProperty(value = "category")
    private RespCategory respCategory;
    @JsonProperty(value = "drug")
    private RespDrug respDrug;
}
