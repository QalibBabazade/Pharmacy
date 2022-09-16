package az.babazade.pharmacy.service.impl;

import az.babazade.pharmacy.dto.request.ReqDrug;
import az.babazade.pharmacy.dto.request.ReqGeneral;
import az.babazade.pharmacy.dto.request.ReqToken;
import az.babazade.pharmacy.dto.response.RespDrug;
import az.babazade.pharmacy.dto.response.RespStatus;
import az.babazade.pharmacy.dto.response.RespStatusList;
import az.babazade.pharmacy.dto.response.Response;
import az.babazade.pharmacy.entity.Drug;
import az.babazade.pharmacy.enums.EnumAvailableStatus;
import az.babazade.pharmacy.exception.ExceptionConstants;
import az.babazade.pharmacy.exception.PharmacyException;
import az.babazade.pharmacy.repository.DrugDao;
import az.babazade.pharmacy.service.DrugService;
import az.babazade.pharmacy.util.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Service
public class DrugServiceImpl implements DrugService {

    @Autowired
    private DrugDao drugDao;

    @Autowired
    private Utility utility;

    @Autowired
    private HttpServletRequest request;

    public static final Logger LOGGER = LogManager.getLogger(DrugServiceImpl.class);

    /********************** Get Drug List ******************************/
    @Override
    public Response<List<RespDrug>> getDrugList(ReqToken reqToken) throws Exception {
        Response<List<RespDrug>> response = new Response<>();
        List<RespDrug> respDrugList = new ArrayList<>();
        try {
            Long userId = reqToken.getUserId();
            String token = reqToken.getToken();
            /***** Check Token ******/
            utility.checkToken(userId, token);
            List<Drug> drugList = drugDao.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (drugList.isEmpty()) {
                throw new PharmacyException(ExceptionConstants.DRUG_NOT_FOUND, "Drug not found!");
            }
            for (Drug drug : drugList) {
                RespDrug respDrug = new RespDrug();
                respDrug.setDrugId(drug.getId());
                respDrug.setName(drug.getName());
                respDrug.setBarkod(drug.getBarkod());
                respDrug.setProductCountry(drug.getProductCountry());
                respDrug.setPrice(drug.getPrice());
                respDrug.setExpDate(drug.getExpDate());
                respDrugList.add(respDrug);
            }
            response.setT(respDrugList);
            response.setRespStatus(RespStatus.getSuccessMessage());
            LOGGER.info("Response is success!");
        } catch (PharmacyException ex) {
            LOGGER.error("Pharmacy Exception",ex);
            response.setRespStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    /********************** Get Drug By Id ******************************/
    @Override
    public Response<RespDrug> getDrugById(ReqGeneral reqGeneral) throws Exception {
        Response<RespDrug> response = new Response<>();
        try {
            Long drugId = reqGeneral.getId();
            ReqToken reqToken = reqGeneral.getReqToken();
            /***** Check ReqToken ******/
            if (reqToken == null) {
                throw new PharmacyException(ExceptionConstants.EMPTY_TOKEN, "Empty token!");
            }
            Long userId = reqGeneral.getReqToken().getUserId();
            String token = reqGeneral.getReqToken().getToken();
            /***** Check Token ******/
            utility.checkToken(userId, token);
            /******** Check Id ********/
            utility.checkId(drugId);
            Drug drug = drugDao.findDrugByIdAndActive(drugId, EnumAvailableStatus.ACTIVE.getValue());
            /***** Check Drug ******/
            if (drug == null) {
                throw new PharmacyException(ExceptionConstants.DRUG_NOT_FOUND, "Drug not found!");
            }
            RespDrug respDrug = new RespDrug();
            respDrug.setDrugId(drug.getId());
            respDrug.setName(drug.getName());
            respDrug.setBarkod(drug.getBarkod());
            respDrug.setProductCountry(drug.getProductCountry());
            respDrug.setPrice(drug.getPrice());
            respDrug.setExpDate(drug.getExpDate());
            response.setT(respDrug);
            response.setRespStatus(RespStatus.getSuccessMessage());

        } catch (PharmacyException ex) {
            response.setRespStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    /********************** Add Drug ******************************/
    @Override
    public RespStatusList addDrug(ReqDrug reqDrug) throws Exception {
        RespStatusList response = new RespStatusList();
        try {
            String name = reqDrug.getName();
            String barkod = reqDrug.getBarkod();
            String productCountry = reqDrug.getProductCountry();
            Double price = reqDrug.getPrice();
            Date expDate = reqDrug.getExpDate();
            ReqToken reqToken = reqDrug.getReqToken();
            /******** Check ReqToken *********/
            if (reqToken == null) {
                throw new PharmacyException(ExceptionConstants.EMPTY_TOKEN, "Empty token!");
            }
            Long userId = reqToken.getUserId();
            String token = reqToken.getToken();
            /***** Check Token ******/
            utility.checkToken(userId, token);
            /***** Check Name and Barkod ******/
            if (name == null || barkod == null) {
                throw new PharmacyException(ExceptionConstants.INVALID_REQUEST_DATA, "Inavlid request data!");
            }
            Drug drug = new Drug();
            drug.setName(name);
            drug.setBarkod(barkod);
            drug.setProductCountry(productCountry);
            drug.setPrice(price);
            drug.setExpDate(expDate);
            drugDao.save(drug);
            response.setRespStatus(RespStatus.getSuccessMessage());
        } catch (PharmacyException ex) {
            response.setRespStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    /********************** Update Drug ******************************/
    @Override
    public RespStatusList updateDrug(ReqDrug reqDrug) throws Exception {
        RespStatusList response = new RespStatusList();
        try {
            Long drugId = reqDrug.getDrugId();
            String name = reqDrug.getName();
            String barkod = reqDrug.getBarkod();
            String productCountry = reqDrug.getProductCountry();
            Double price = reqDrug.getPrice();
            Date expDate = reqDrug.getExpDate();
            /***** Check Drug Id ,Name and Barkod *****/
            if (drugId == null || name == null || barkod == null) {
                throw new PharmacyException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data!");
            }
            Drug drug = drugDao.findDrugByIdAndActive(drugId, EnumAvailableStatus.ACTIVE.getValue());
            /***** Check Drug *****/
            if (drug == null) {
                throw new PharmacyException(ExceptionConstants.DRUG_NOT_FOUND, "Drug not found!");
            }
            drug.setName(name);
            drug.setBarkod(barkod);
            drug.setProductCountry(productCountry);
            drug.setPrice(price);
            drug.setExpDate(expDate);
            drugDao.save(drug);
            response.setRespStatus(RespStatus.getSuccessMessage());
        } catch (PharmacyException ex) {
            response.setRespStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    /**********************Delete Drug******************************/
    @Override
    public RespStatusList deleteDrug(ReqGeneral reqGeneral) throws Exception {
        RespStatusList response = new RespStatusList();
        try {
            Long drugId = reqGeneral.getId();
            ReqToken reqToken = reqGeneral.getReqToken();
            /***** Check ReqToken ******/
            if (reqToken == null) {
                throw new PharmacyException(ExceptionConstants.EMPTY_TOKEN, "Empty token!");
            }
            Long userId = reqGeneral.getReqToken().getUserId();
            String token = reqGeneral.getReqToken().getToken();
            /***** Check Token ******/
            utility.checkToken(userId, token);
            /***** Check Drug Id *****/
            utility.checkId(drugId);
            Drug drug = drugDao.findDrugByIdAndActive(drugId, EnumAvailableStatus.ACTIVE.getValue());
            /***** Check Drug *****/
            if (drug == null) {
                throw new PharmacyException(ExceptionConstants.DRUG_NOT_FOUND, "Drug not found!");
            }
            drug.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            drugDao.save(drug);
            response.setRespStatus(RespStatus.getSuccessMessage());

        } catch (PharmacyException ex) {
            response.setRespStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }


}
