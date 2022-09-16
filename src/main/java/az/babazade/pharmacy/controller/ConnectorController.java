package az.babazade.pharmacy.controller;

import az.babazade.pharmacy.dto.request.ReqConnector;
import az.babazade.pharmacy.dto.request.ReqGeneral;
import az.babazade.pharmacy.dto.request.ReqToken;
import az.babazade.pharmacy.dto.response.RespConnector;
import az.babazade.pharmacy.dto.response.RespStatusList;
import az.babazade.pharmacy.dto.response.Response;
import az.babazade.pharmacy.service.ConnectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/connector")
public class ConnectorController {

    @Autowired
    private ConnectorService connectorService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/GetConnectorList")
    public Response<List<RespConnector>> getConnectorList(@RequestBody ReqToken reqToken)throws Exception{
        return  connectorService.getConnectorList(reqToken);
    }

    @PostMapping(value = "/GetConnectorById")
    public Response<RespConnector> getConnectorById(@RequestBody ReqGeneral reqGeneral)throws Exception{
        return  connectorService.getConnectorById(reqGeneral);
    }

    @PostMapping(value = "/AddConnector")
    public RespStatusList addConnector(@RequestBody ReqConnector reqConnector)throws Exception{
        return  connectorService.addConnector(reqConnector);
    }

    @PostMapping(value = "/UpdateConnector")
    public RespStatusList updateConnector(@RequestBody ReqConnector reqConnector)throws Exception{
        return  connectorService.updateConnector(reqConnector);
    }

    @PostMapping(value = "/DeleteConnector")
    public RespStatusList deleteConnector(@RequestBody ReqGeneral reqGeneral)throws Exception{
        return  connectorService.deleteConnector(reqGeneral);
    }
}
