package az.babazade.pharmacy.dto.request;

import lombok.Data;

@Data
public class ReqConnector {
    private Long connectorId;
    private Long categoryId;
    private Long drugId;
    private ReqToken reqToken;
}
