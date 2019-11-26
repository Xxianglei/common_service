package com.xianglei.temppark_service.service;

import com.xianglei.temppark_service.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> listProduct();

    Product findById(int id);


}
