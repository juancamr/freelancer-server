package com.jmatch.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jmatch.models.Categoria;
import com.jmatch.models.Response;
import com.jmatch.requestModel.Categoria.CreateCategoryRequest;
import com.jmatch.requestModel.Categoria.UpdateCategoryRequest;
import com.jmatch.services.CategoriaService;
import com.jmatch.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/categoria")
public class CategoriaController extends BaseController {
    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping("/")
    public ResponseEntity<ObjectNode> createCategory(@RequestBody CreateCategoryRequest createCategoryRequest) {
        if (Utils.checkRequestParams(createCategoryRequest)) {
            String nombre = createCategoryRequest.getNombre();
            String descripcion = createCategoryRequest.getDescripcion();
            Response<Categoria> res = categoriaService.createCategory(nombre, descripcion);
            if (res.isSuccess()) return res(201, successJson().putPOJO("categoria", res.getData()));
            else return unprocessable(res.getError());
        } else return insuficientParams();
    }

    @PutMapping("/")
    public ResponseEntity<ObjectNode> updateCategory(@RequestBody UpdateCategoryRequest updateCategoryRequest) {
        if (Utils.checkRequestParams(updateCategoryRequest)) {
            int id = updateCategoryRequest.getId();
            String nombre = updateCategoryRequest.getNombre();
            String descripcion = updateCategoryRequest.getDescripcion();
            Response<Categoria> res = categoriaService.updateCategory(new Categoria(nombre, descripcion, id));
            if (res.isSuccess()) return res(successJson());
            else return unprocessable(res.getError());
        } else return insuficientParams();
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCategorys() {
        Response<Categoria> res = categoriaService.findAll();
        return res(successJson().putPOJO("categoria", res.getDataList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjectNode> getCategory(@PathVariable("id") int id) {
        if (id != 0) {
            Response<Categoria> res = categoriaService.findOne(id);
            if (res.isSuccess()) return res(successJson());
            else return unprocessable(res.getError());
        } else return insuficientParams();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") int id) {
        if (id != 0) {
            Response<Categoria> res = categoriaService.removeCategory(id);
            if (res.isSuccess()) return res(204, successJson());
            else return unprocessable(res.getError());
        } else return insuficientParams();
    }
}
