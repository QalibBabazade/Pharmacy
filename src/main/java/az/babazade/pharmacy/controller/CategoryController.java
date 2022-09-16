package az.babazade.pharmacy.controller;

import az.babazade.pharmacy.dto.request.ReqCategory;
import az.babazade.pharmacy.dto.request.ReqGeneral;
import az.babazade.pharmacy.dto.request.ReqToken;
import az.babazade.pharmacy.dto.response.RespCategory;
import az.babazade.pharmacy.dto.response.RespStatusList;
import az.babazade.pharmacy.dto.response.Response;
import az.babazade.pharmacy.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = "/GetCategoryList")
    public Response<List<RespCategory>> getCategoryList(@RequestBody ReqToken reqToken) throws Exception {
        return categoryService.getCategoryList(reqToken);
    }

    @PostMapping(value = "/GetCategoryById")
    public Response<RespCategory> getCategoryById(@RequestBody ReqGeneral reqGeneral) throws Exception {
        return categoryService.getCategoryById(reqGeneral);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/AddCategory")
    public RespStatusList addCategory(@RequestBody ReqCategory reqCategory) throws Exception {
        return categoryService.addCategory(reqCategory);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/UpdateCategory")
    public RespStatusList updateCategory(@RequestBody ReqCategory reqCategory) throws Exception {
        return categoryService.updateCategory(reqCategory);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/DeleteCategory")
    public RespStatusList deleteCategory(@RequestBody ReqGeneral reqGeneral) throws Exception {
        return categoryService.deleteCategory(reqGeneral);
    }
}
