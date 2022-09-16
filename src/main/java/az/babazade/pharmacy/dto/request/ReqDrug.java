package az.babazade.pharmacy.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class ReqDrug {

    private Long drugId;
    private String name;
    private String barkod;
    private String productCountry;
    private Double price;
    private Date expDate;
    private ReqToken reqToken;
}
