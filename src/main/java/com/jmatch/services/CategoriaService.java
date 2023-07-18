package com.jmatch.services;

import com.jmatch.models.Categoria;
import com.jmatch.models.Response;
import com.jmatch.repositories.CategoriaRepository;
import com.jmatch.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService extends BaseService<Categoria> {

    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Response<Categoria> createCategory(String nombre, String descripcion) {
        Categoria categoria = categoriaRepository.save(new Categoria(nombre, descripcion));
        if (categoria.getId() != 0) return res(true, categoria);
        else return res(false);
    }

    public Response<Categoria> findOne(int id) {
        Optional<Categoria> categoryFound = categoriaRepository.findById((long) id);
        return categoryFound.map(categoria -> res(true, categoria))
                .orElseGet(() -> res(false, "La categoria no existe"));
    }

    public Response<Categoria> findAll() {
        return res(true, categoriaRepository.findAll());
    }

    public Response<Categoria> removeCategory(int id) {
        try {
            categoriaRepository.deleteById((long) id);
            return res(true);
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            return res(false, Const.Errors.SOMETHING_WENT_WRONG);
        }
    }

    public Response<Categoria> updateCategory(Categoria categoria) {
        Long id = (long) categoria.getId();
        Optional<Categoria> categoryFound = categoriaRepository.findById(id);
        if (categoryFound.isPresent()) {
            Categoria category = categoryFound.get();
            category.setNombre(categoria.getNombre());
            category.setDescripcion(categoria.getDescripcion());
            Categoria savedCategory = categoriaRepository.save(category);
            if (savedCategory.getId() != 0) return res(true);
            else return res(false, Const.Errors.SOMETHING_WENT_WRONG);
        } else return res(false, "La categoria no existe");
    }
}
