package com.jmatch.requestModel.Categoria;

public class UpdateCategoryRequest extends CreateCategoryRequest {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
