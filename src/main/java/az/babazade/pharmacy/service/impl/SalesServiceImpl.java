package az.babazade.pharmacy.service.impl;

import az.babazade.pharmacy.dto.request.ReqGeneral;
import az.babazade.pharmacy.dto.request.ReqSales;
import az.babazade.pharmacy.dto.request.ReqToken;
import az.babazade.pharmacy.dto.response.*;
import az.babazade.pharmacy.entity.Drug;
import az.babazade.pharmacy.entity.Sales;
import az.babazade.pharmacy.enums.EnumAvailableStatus;
import az.babazade.pharmacy.exception.ExceptionConstants;
import az.babazade.pharmacy.exception.PharmacyException;
import az.babazade.pharmacy.repository.DrugDao;
import az.babazade.pharmacy.repository.SalesDao;
import az.babazade.pharmacy.service.SalesService;
import az.babazade.pharmacy.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesDao salesDao;

    @Autowired
    private DrugDao drugDao;

    @Autowired
    private Utility utility;

    /******************** Get Sales List ****************************/
    @Override
    public Response<List<RespSales>> getSalesList(ReqToken reqToken) throws Exception {
        Response<List<RespSales>> response = new Response<>();
        List<RespSales> respSalesList = new ArrayList<>();
        try {
            Long userId = reqToken.getUserId();
            String token = reqToken.getToken();
            /***** Check Token ******/
            utility.checkToken(userId, token);
            List<Sales> salesList = salesDao.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (salesList.isEmpty()) {
                throw new PharmacyException(ExceptionConstants.SALES_NOT_FOUND, "Sales not found!");
            }
            for (Sales sales : salesList) {
                RespSales respSales = new RespSales();
                RespDrug respDrug = new RespDrug();
                respDrug.setName(sales.getDrug().getName());
                respDrug.setBarkod(sales.getDrug().getBarkod());
                respDrug.setProductCountry(sales.getDrug().getProductCountry());
                respDrug.setPrice(sales.getDrug().getPrice());
                respDrug.setExpDate(sales.getDrug().getExpDate());
                respSales.setSalesId(sales.getId());
                respSales.setBoxNumber(sales.getBoxNumber());
                respSales.setAmount(sales.getAmount());
                respSales.setRespDrug(respDrug);
                respSalesList.add(respSales);
            }

            response.setT(respSalesList);
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

    /*************************** Get Sales By Id **************************************/
    @Override
    public Response<RespSales> getSalesById(ReqGeneral reqGeneral) throws Exception {
        Response<RespSales> response = new Response<>();
        try {
            Long salesId = reqGeneral.getId();
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
            utility.checkId(salesId);

            Sales sales = salesDao.findSalesByIdAndActive(salesId, EnumAvailableStatus.ACTIVE.getValue());

            if (sales == null) {
                throw new PharmacyException(ExceptionConstants.SALES_NOT_FOUND, "Sales not found!");
            }

            RespSales respSales = new RespSales();
            RespDrug respDrug = new RespDrug();
            respDrug.setName(sales.getDrug().getName());
            respDrug.setBarkod(sales.getDrug().getBarkod());
            respDrug.setProductCountry(sales.getDrug().getProductCountry());
            respDrug.setPrice(sales.getDrug().getPrice());
            respDrug.setExpDate(sales.getDrug().getExpDate());
            respSales.setSalesId(sales.getId());
            respSales.setBoxNumber(sales.getBoxNumber());
            respSales.setAmount(sales.getAmount());
            respSales.setRespDrug(respDrug);

            response.setT(respSales);
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

    /************************ Add Sales *****************************/
    @Override
    public RespStatusList addSales(ReqSales reqSales) throws Exception {
        RespStatusList response = new RespStatusList();
        try {
            ReqToken reqToken = reqSales.getReqToken();
            /******** Check ReqToken *********/
            if (reqToken == null) {
                throw new PharmacyException(ExceptionConstants.EMPTY_TOKEN, "Empty token!");
            }
            Long userId = reqToken.getUserId();
            String token = reqToken.getToken();
            /***** Check Token ******/
            utility.checkToken(userId, token);
            Integer boxNumber = reqSales.getBoxNumber();
            Long drugId = reqSales.getDrugId();
            Double amount = reqSales.getAmount();
            if (drugId == null || boxNumber == null) {
                throw new PharmacyException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data!");
            }
            Drug drug = drugDao.findDrugByIdAndActive(drugId, EnumAvailableStatus.ACTIVE.getValue());
            if (drug == null) {
                throw new PharmacyException(ExceptionConstants.DRUG_NOT_FOUND, "Drug not found!");
            }
            if (amount == null) {
                Double price = drug.getPrice();
                amount = boxNumber * price;
            }
            Sales sales = new Sales();
            sales.setBoxNumber(boxNumber);
            sales.setAmount(amount);
            sales.setDrug(drug);
            salesDao.save(sales);
            /* Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    SendMail sendMail = new SendMail();
                    sendMail.sendSimpleMessage();
                }
            };

            runnable.run();*/
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

    /************************ Update Sales *************************/
    @Override
    public RespStatusList updateSales(ReqSales reqSales) throws Exception {
        RespStatusList response = new RespStatusList();
        try {
            ReqToken reqToken = reqSales.getReqToken();
            /******** Check ReqToken *********/
            if (reqToken == null) {
                throw new PharmacyException(ExceptionConstants.EMPTY_TOKEN, "Empty token!");
            }
            Long userId = reqToken.getUserId();
            String token = reqToken.getToken();
            /***** Check Token ******/
            utility.checkToken(userId, token);
            Long salesId = reqSales.getSalesId();
            Integer boxNumber = reqSales.getBoxNumber();
            Long drugId = reqSales.getDrugId();
            Double amount = reqSales.getAmount();
            if (drugId == null || boxNumber == null || salesId == null) {
                throw new PharmacyException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data!");
            }
            Drug drug = drugDao.findDrugByIdAndActive(drugId, EnumAvailableStatus.ACTIVE.getValue());
            if (drug == null) {
                throw new PharmacyException(ExceptionConstants.DRUG_NOT_FOUND, "Drug not found!");
            }
            if (amount == null) {
                Double price = drug.getPrice();
                amount = boxNumber * price;
            }
            Sales sales = salesDao.findSalesByIdAndActive(salesId, EnumAvailableStatus.ACTIVE.getValue());
            sales.setBoxNumber(boxNumber);
            sales.setAmount(amount);
            sales.setDrug(drug);
            salesDao.save(sales);
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
 /********************** Delete Sales *****************************/
    @Override
    public RespStatusList deleteSales(ReqGeneral reqGeneral) throws Exception {

        RespStatusList response = new RespStatusList();
        try {
            Long salesId = reqGeneral.getId();
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
            utility.checkId(salesId);
            Sales sales = salesDao.findSalesByIdAndActive(salesId,EnumAvailableStatus.ACTIVE.getValue());
            if(sales == null){
                throw  new PharmacyException(ExceptionConstants.SALES_NOT_FOUND,"Sales not found!");
            }
            sales.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            salesDao.save(sales);
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
