package com.jmatch.services;

import com.jmatch.models.Project;
import com.jmatch.models.Response;
import com.jmatch.models.User;

import org.springframework.lang.NonNull;

import java.util.List;

public class BaseService<T> {

    public <S> Response<T> res(@NonNull S... values) {
        boolean success = false;
        String message = null;
        List<T> dataList = null;
        T data = null;

        for (S value : values) {
            if (value instanceof Boolean) {
                success = (Boolean) value;
            } else if (value instanceof String) {
                message = (String) value;
            } else if (value instanceof List<?>) {
                dataList = (List<T>) value;
            } else {
                data = (T) value;
            }
        }
        return new Response<>(success, message, data, dataList);
    }
}
