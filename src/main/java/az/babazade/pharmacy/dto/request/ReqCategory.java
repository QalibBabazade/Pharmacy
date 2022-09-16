package az.babazade.pharmacy.dto.request;

import lombok.Data;

@Data
public class ReqCategory {

    private Long categoryId;
    private String name;
    private ReqToken reqToken;
}
