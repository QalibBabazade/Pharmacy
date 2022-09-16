package az.babazade.pharmacy.dto.request;

import az.babazade.pharmacy.dto.response.RespDrug;
import lombok.Data;

@Data
public class ReqSales {
    private Long salesId;
    private Integer boxNumber;
    private Double amount;
    private Long drugId;
    private ReqToken reqToken;
}
