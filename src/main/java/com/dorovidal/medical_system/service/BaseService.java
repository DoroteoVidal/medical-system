package com.dorovidal.medical_system.service;

import java.util.List;

public interface BaseService<T, V> {

    T save (V dto) throws Exception;

    T update (Long id, V dto) throws Exception;

    void delete (Long id) throws Exception;

    T getById(Long id) throws Exception;

    List<T> getAll();
}
