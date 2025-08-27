package com.mclods.store_app.repositories;

import com.mclods.store_app.domain.dtos.product.ProductSummary;
import com.mclods.store_app.domain.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    @Query(value = "SELECT * FROM products WHERE price BETWEEN :minPrice AND :maxPrice", nativeQuery = true)
    List<Product> findProductsByPriceRange(Double minPrice, Double maxPrice);

    @Query("SELECT new com.mclods.store_app.domain.dtos.product.ProductSummary(p.id, p.name, p.description, p.price, c.name) " +
            "FROM Product p JOIN p.category c")
    List<ProductSummary> findProductSummary();
}
