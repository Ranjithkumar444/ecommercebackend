package com.ranjith.ecommer_project.Service;

import com.ranjith.ecommer_project.Model.Products;
import com.ranjith.ecommer_project.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    public List<Products> getProducts() {
        return repo.findAll();
    }

    public Products getProductsById(int id) {
        return repo.findById(id).get();
    }

    public Products addProduct(Products product, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
            product.setImageDate(imageFile.getBytes());
        }
        return repo.save(product);
    }

    public Products updateProducts(int productId, Products product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getName());
        product.setImageType(imageFile.getOriginalFilename());
        product.setImageDate(imageFile.getBytes());

        return  repo.save(product);
    }

    public void DeleteProduct(int id) {
        repo.deleteById(id);
    }

    public List<Products> searchProduct(String keyword) {
        return repo.searchproduct(keyword);
    }
}
