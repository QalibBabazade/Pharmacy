package az.babazade.pharmacy.controller;

import az.babazade.pharmacy.dto.request.ReqDrug;
import az.babazade.pharmacy.dto.request.ReqGeneral;
import az.babazade.pharmacy.dto.request.ReqToken;
import az.babazade.pharmacy.dto.response.RespDrug;
import az.babazade.pharmacy.dto.response.RespStatusList;
import az.babazade.pharmacy.dto.response.Response;
import az.babazade.pharmacy.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/drug")
public class DrugController {

    @Autowired
    private DrugService drugService;

    @PostMapping(value = "/GetDrugList")
    public Response<List<RespDrug>> getDrugList(@RequestBody ReqToken reqToken) throws Exception {
        return drugService.getDrugList(reqToken);
    }

    @PostMapping(value = "/GetDrugById")
    public Response<RespDrug> getDrugById(@RequestBody ReqGeneral reqGeneral) throws Exception {
        return drugService.getDrugById(reqGeneral);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/AddDrug")
    public RespStatusList addDrug(@RequestBody ReqDrug reqDrug) throws Exception {
        return drugService.addDrug(reqDrug);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/UpdateDrug")
    public RespStatusList updateDrug(@RequestBody ReqDrug reqDrug) throws Exception {
        return drugService.updateDrug(reqDrug);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/DeleteDrug")
    public RespStatusList deleteDrug(@RequestBody ReqGeneral reqGeneral) throws Exception {
        return drugService.deleteDrug(reqGeneral);
    }

}
