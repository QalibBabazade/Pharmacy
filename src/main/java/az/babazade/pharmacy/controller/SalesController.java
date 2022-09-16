package az.babazade.pharmacy.controller;

import az.babazade.pharmacy.dto.request.ReqGeneral;
import az.babazade.pharmacy.dto.request.ReqSales;
import az.babazade.pharmacy.dto.request.ReqToken;
import az.babazade.pharmacy.dto.response.RespSales;
import az.babazade.pharmacy.dto.response.RespStatusList;
import az.babazade.pharmacy.dto.response.Response;
import az.babazade.pharmacy.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @PostMapping(value = "/GetSalesList")
    public Response<List<RespSales>> getSalesList(@RequestBody ReqToken reqToken) throws Exception {
        return salesService.getSalesList(reqToken);
    }

    @PostMapping(value = "/GetSalesById")
    public Response<RespSales> getSalesById(@RequestBody ReqGeneral reqGeneral) throws Exception {
        return salesService.getSalesById(reqGeneral);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/AddSales")
    public RespStatusList addSales(@RequestBody ReqSales reqSales) throws Exception {
        return salesService.addSales(reqSales);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/UpdateSales")
    public RespStatusList updateSales(@RequestBody ReqSales reqSales) throws Exception {
        return salesService.updateSales(reqSales);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/DeleteSales")
    public RespStatusList deleteSales(@RequestBody ReqGeneral reqGeneral) throws Exception {
        return salesService.deleteSales(reqGeneral);
    }

}
