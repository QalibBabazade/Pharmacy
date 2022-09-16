package az.babazade.pharmacy.service.impl;

import az.babazade.pharmacy.dto.request.ReqCategory;
import az.babazade.pharmacy.dto.request.ReqGeneral;
import az.babazade.pharmacy.dto.request.ReqToken;
import az.babazade.pharmacy.dto.response.RespCategory;
import az.babazade.pharmacy.dto.response.RespStatus;
import az.babazade.pharmacy.dto.response.RespStatusList;
import az.babazade.pharmacy.dto.response.Response;
import az.babazade.pharmacy.entity.Category;
import az.babazade.pharmacy.enums.EnumAvailableStatus;
import az.babazade.pharmacy.exception.ExceptionConstants;
import az.babazade.pharmacy.exception.PharmacyException;
import az.babazade.pharmacy.repository.CategoryDao;
import az.babazade.pharmacy.service.CategoryService;
import az.babazade.pharmacy.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private Utility utility;

    /********************** Get Category List *************************/
    @Override
    public Response<List<RespCategory>> getCategoryList(ReqToken reqToken) throws Exception {

        Response<List<RespCategory>> response = new Response<>();
        List<RespCategory> respCategoryList = new ArrayList<>();
        try{
            Long userId = reqToken.getUserId();
            String token = reqToken.getToken();
            /***** Check Token ******/
            utility.checkToken(userId, token);
            List<Category> categoryList = categoryDao.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if(categoryList.isEmpty()){
                throw new PharmacyException(ExceptionConstants.CATEGORY_NOT_FOUND,"Category not found!");
            }
            for(Category category : categoryList){
                RespCategory respCategory = new RespCategory();
                respCategory.setCategoryId(category.getId());
                respCategory.setName(category.getName());
                respCategoryList.add(respCategory);
            }
            response.setT(respCategoryList);
            response.setRespStatus(RespStatus.getSuccessMessage());
        }catch (PharmacyException ex) {
            response.setRespStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    /********************** Get Category By Id *************************/
    @Override
    public Response<RespCategory> getCategoryById(ReqGeneral reqGeneral) throws Exception {
        Response<RespCategory> response = new Response<>();
        try{
            Long categoryId = reqGeneral.getId();
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
            utility.checkId(categoryId);
            Category category = categoryDao.findCategoryByIdAndActive(categoryId,EnumAvailableStatus.ACTIVE.getValue());
            if(category == null){
                throw  new PharmacyException(ExceptionConstants.CATEGORY_NOT_FOUND,"Category not found!");
            }
            RespCategory respCategory = new RespCategory();
            respCategory.setCategoryId(category.getId());
            respCategory.setName(category.getName());
            response.setT(respCategory);
            response.setRespStatus(RespStatus.getSuccessMessage());

        }catch (PharmacyException ex) {
            response.setRespStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    /********************** Add Category *************************/
    @Override
    public RespStatusList addCategory(ReqCategory reqCategory) throws Exception {
        RespStatusList response = new RespStatusList();
        try{
            String name = reqCategory.getName();
            ReqToken reqToken = reqCategory.getReqToken();
            /******** Check ReqToken *********/
            if (reqToken == null) {
                throw new PharmacyException(ExceptionConstants.EMPTY_TOKEN, "Empty token!");
            }
            Long userId = reqToken.getUserId();
            String token = reqToken.getToken();
            /***** Check Token ******/
            utility.checkToken(userId, token);
            if(name == null){
                throw  new PharmacyException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data!");
            }
            Category category = new Category();
            category.setName(name);
            categoryDao.save(category);
            response.setRespStatus(RespStatus.getSuccessMessage());

        }catch (PharmacyException ex) {
            response.setRespStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    /********************** Update Category *************************/
    @Override
    public RespStatusList updateCategory(ReqCategory reqCategory) throws Exception {
        RespStatusList response = new RespStatusList();
        try{
            Long categoryId = reqCategory.getCategoryId();
            String name = reqCategory.getName();
            ReqToken reqToken = reqCategory.getReqToken();
            /******** Check ReqToken *********/
            if (reqToken == null) {
                throw new PharmacyException(ExceptionConstants.EMPTY_TOKEN, "Empty token!");
            }
            Long userId = reqToken.getUserId();
            String token = reqToken.getToken();
            /***** Check Token ******/
            utility.checkToken(userId, token);
            if(name == null || categoryId == null){
                throw  new PharmacyException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data!");
            }
           Category category = categoryDao.findCategoryByIdAndActive(categoryId,EnumAvailableStatus.ACTIVE.getValue());
           if(category == null){
               throw new PharmacyException(ExceptionConstants.CATEGORY_NOT_FOUND,"Category not found!");
           }
           category.setName(name);
           categoryDao.save(category);
           response.setRespStatus(RespStatus.getSuccessMessage());

        }catch (PharmacyException ex) {
            response.setRespStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    /********************** Delete Category *************************/
    @Override
    public RespStatusList deleteCategory(ReqGeneral reqGeneral) throws Exception {
        RespStatusList response = new RespStatusList();
        try{
            Long categoryId = reqGeneral.getId();
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
            utility.checkId(categoryId);
            Category category = categoryDao.findCategoryByIdAndActive(categoryId,EnumAvailableStatus.ACTIVE.getValue());
            if(category == null){
                throw  new PharmacyException(ExceptionConstants.CATEGORY_NOT_FOUND,"Category not found!");
            }
            category.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            categoryDao.save(category);
            response.setRespStatus(RespStatus.getSuccessMessage());

        }catch (PharmacyException ex) {
            response.setRespStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setRespStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }
}
