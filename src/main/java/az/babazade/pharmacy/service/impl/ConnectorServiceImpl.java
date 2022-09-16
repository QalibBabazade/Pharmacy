package az.babazade.pharmacy.service.impl;

import az.babazade.pharmacy.dto.request.ReqConnector;
import az.babazade.pharmacy.dto.request.ReqGeneral;
import az.babazade.pharmacy.dto.request.ReqToken;
import az.babazade.pharmacy.dto.response.*;
import az.babazade.pharmacy.entity.Category;
import az.babazade.pharmacy.entity.Connector;
import az.babazade.pharmacy.entity.Drug;
import az.babazade.pharmacy.enums.EnumAvailableStatus;
import az.babazade.pharmacy.exception.ExceptionConstants;
import az.babazade.pharmacy.exception.PharmacyException;
import az.babazade.pharmacy.repository.CategoryDao;
import az.babazade.pharmacy.repository.ConnectorDao;
import az.babazade.pharmacy.repository.DrugDao;
import az.babazade.pharmacy.service.ConnectorService;
import az.babazade.pharmacy.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConnectorServiceImpl implements ConnectorService {

    @Autowired
    private ConnectorDao connectorDao;

    @Autowired
    private Utility utility;

    @Autowired
    private DrugDao drugDao;

    @Autowired
    private CategoryDao categoryDao;

    /*********************** Get Connector List ******************************/
    @Override
    public Response<List<RespConnector>> getConnectorList(ReqToken reqToken) throws Exception {
        Response<List<RespConnector>> response = new Response<>();
        List<RespConnector> respConnectorList = new ArrayList<>();
        try {
            Long userId = reqToken.getUserId();
            String token = reqToken.getToken();
            /***** Check Token ******/
            utility.checkToken(userId, token);
            List<Connector> connectorList = connectorDao.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (connectorList.isEmpty()) {
                throw new PharmacyException(ExceptionConstants.CONNECTOR_NOT_FOUND, "Connector not found!");
            }
            for (Connector connector : connectorList) {
                RespConnector respConnector = new RespConnector();
                RespCategory respCategory = new RespCategory();
                RespDrug respDrug = new RespDrug();
                respCategory.setName(connector.getCategory().getName());
                respDrug.setName(connector.getDrug().getName());
                respDrug.setBarkod(connector.getDrug().getBarkod());
                respDrug.setProductCountry(connector.getDrug().getProductCountry());
                respDrug.setPrice(connector.getDrug().getPrice());
                respDrug.setExpDate(connector.getDrug().getExpDate());
                respConnector.setConnectorId(connector.getId());
                respConnector.setRespCategory(respCategory);
                respConnector.setRespDrug(respDrug);
                respConnectorList.add(respConnector);

            }
            response.setT(respConnectorList);
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

    /*********************** Get Connector By Id ******************************/
    @Override
    public Response<RespConnector> getConnectorById(ReqGeneral reqGeneral) throws Exception {
        Response<RespConnector> response = new Response<>();
        try {
            Long connectorId = reqGeneral.getId();
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
            utility.checkId(connectorId);
            Connector connector = connectorDao.findConnectorByIdAndActive(connectorId, EnumAvailableStatus.ACTIVE.getValue());
            if (connector == null) {
                throw new PharmacyException(ExceptionConstants.CONNECTOR_NOT_FOUND, "Connector not found!");
            }
            RespConnector respConnector = new RespConnector();
            RespCategory respCategory = new RespCategory();
            RespDrug respDrug = new RespDrug();
            respCategory.setName(connector.getCategory().getName());
            respDrug.setName(connector.getDrug().getName());
            respDrug.setBarkod(connector.getDrug().getBarkod());
            respDrug.setProductCountry(connector.getDrug().getProductCountry());
            respDrug.setPrice(connector.getDrug().getPrice());
            respDrug.setExpDate(connector.getDrug().getExpDate());
            respConnector.setConnectorId(connector.getId());
            respConnector.setRespCategory(respCategory);
            respConnector.setRespDrug(respDrug);
            response.setT(respConnector);
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

    /*********************** Add Connector ******************************/
    @Override
    public RespStatusList addConnector(ReqConnector reqConnector) throws Exception {
        RespStatusList response = new RespStatusList();
        try {
            Long categoryId = reqConnector.getCategoryId();
            Long drugId = reqConnector.getDrugId();
            ReqToken reqToken = reqConnector.getReqToken();
            /******** Check ReqToken *********/
            if (reqToken == null) {
                throw new PharmacyException(ExceptionConstants.EMPTY_TOKEN, "Empty token!");
            }
            Long userId = reqToken.getUserId();
            String token = reqToken.getToken();
            /***** Check Token ******/
            utility.checkToken(userId, token);
            if (categoryId == null || drugId == null) {
                throw new PharmacyException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data!");
            }
            Category category = categoryDao.findCategoryByIdAndActive(categoryId, EnumAvailableStatus.ACTIVE.getValue());
            if (category == null) {
                throw new PharmacyException(ExceptionConstants.CATEGORY_NOT_FOUND, "Category not found!");
            }
            Drug drug = drugDao.findDrugByIdAndActive(drugId, EnumAvailableStatus.ACTIVE.getValue());
            if (drug == null) {
                throw new PharmacyException(ExceptionConstants.DRUG_NOT_FOUND, "Drug not found!");
            }
            Connector connector = new Connector();
            connector.setCategory(category);
            connector.setDrug(drug);
            connectorDao.save(connector);
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


    /*********************** Update Connector ******************************/
    @Override
    public RespStatusList updateConnector(ReqConnector reqConnector) throws Exception {
        RespStatusList response = new RespStatusList();
        try {
            Long connectorId = reqConnector.getConnectorId();
            Long categoryId = reqConnector.getCategoryId();
            Long drugId = reqConnector.getDrugId();
            ReqToken reqToken = reqConnector.getReqToken();
            /******** Check ReqToken *********/
            if (reqToken == null) {
                throw new PharmacyException(ExceptionConstants.EMPTY_TOKEN, "Empty token!");
            }
            Long userId = reqToken.getUserId();
            String token = reqToken.getToken();
            /***** Check Token ******/
            utility.checkToken(userId, token);
            if (connectorId == null || categoryId == null || drugId == null) {
                throw new PharmacyException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data!");
            }
            Category category = categoryDao.findCategoryByIdAndActive(categoryId, EnumAvailableStatus.ACTIVE.getValue());
            if (category == null) {
                throw new PharmacyException(ExceptionConstants.CATEGORY_NOT_FOUND, "Category not found!");
            }
            Drug drug = drugDao.findDrugByIdAndActive(drugId, EnumAvailableStatus.ACTIVE.getValue());
            if (drug == null) {
                throw new PharmacyException(ExceptionConstants.DRUG_NOT_FOUND, "Drug not found!");
            }
            Connector connector = connectorDao.findConnectorByIdAndActive(categoryId, EnumAvailableStatus.ACTIVE.getValue());
            if (connector == null) {
                throw new PharmacyException(ExceptionConstants.CONNECTOR_NOT_FOUND, "Connector not found!");
            }
            connector.setCategory(category);
            connector.setDrug(drug);
            connectorDao.save(connector);
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

    /*********************** DeleteConnector ******************************/
    @Override
    public RespStatusList deleteConnector(ReqGeneral reqGeneral) throws Exception {
        RespStatusList response = new RespStatusList();
        try {
            Long connectorId = reqGeneral.getId();
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
            utility.checkId(connectorId);
            Connector connector = connectorDao.findConnectorByIdAndActive(connectorId, EnumAvailableStatus.ACTIVE.getValue());
            if (connector == null) {
                throw new PharmacyException(ExceptionConstants.CONNECTOR_NOT_FOUND, "Connector not found!");
            }
            connector.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            connectorDao.save(connector);
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
