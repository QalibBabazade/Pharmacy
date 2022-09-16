package az.babazade.pharmacy.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class RespDrug {

    private Long drugId;
    private String name;
    private String barkod;
    private String productCountry;
    private Double price;
    private Date expDate;
}
