package com.ranjith.ecommer_project.Repo;

import com.ranjith.ecommer_project.Model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Products,Integer> {

    @Query("SELECT p FROM Products p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Products> searchproduct(String keyword);
}
