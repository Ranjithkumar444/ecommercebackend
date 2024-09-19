package com.ranjith.ecommer_project.Controller;

import com.ranjith.ecommer_project.Model.Products;
import com.ranjith.ecommer_project.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")

@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService service;

    @RequestMapping("/")
    public String greet(){
        return "Hello welcome to new project";
    }

    @GetMapping("/products")
    public List<Products> getProducts(){
        return service.getProducts();
    }

    @GetMapping("/products/{Id}")
    public Products getProductsById(@PathVariable  int Id){
        return service.getProductsById(Id);
    }

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(
            @RequestPart("product") Products product,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
    ){
        try {
            Products savedProduct = service.addProduct(product, imageFile);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/{productId}/Image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){

        Products product = service.getProductsById(productId);
        byte[] imageFile = product.getImageDate();

        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageFile);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<String> updateProducts(@PathVariable int productId,@RequestPart("product") Products product,
                                                 @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        Products product1 = null;
        try{
            product1 = service.updateProducts(productId,product,imageFile);
        }
        catch (IOException e){
            return  new ResponseEntity<>("There is no product",HttpStatus.BAD_REQUEST);
        }

        if(product1 != null){
            return new ResponseEntity<>("updated",HttpStatus.OK);
        }
        else{
            return  new ResponseEntity<>("failed to update",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("products/{Id}")
    public ResponseEntity<String> DeleteProduct(@PathVariable int Id){
        Products product1 = service.getProductsById(Id);
        if(product1 != null){
            service.DeleteProduct(Id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("product not found",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Products>> searchProduct(@RequestParam String keyword){

        List<Products> products  = service.searchProduct(keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
}
