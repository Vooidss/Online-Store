package com.onlinestore.backend.Repositories;

import com.onlinestore.backend.Models.Products;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepositories extends JpaRepository<Products, Integer> {
    public List<Products> findByType(String type);

    @Query("""
        SELECT 'color' AS attribute, p.color as value, count(*) as count  FROM Products p WHERE p.type = :type GROUP BY p.color
        UNION ALL
        SELECT 'brand' AS attribute, p.brand as value, count(*) as count  FROM Products p WHERE p.type = :type GROUP BY p.brand
        UNION ALL
        SELECT 'material' AS attribute, p.material as value, count(*) as count  FROM Products p WHERE p.type = :type GROUP BY p.material
        UNION ALL
        SELECT 'size' AS attribute, p.size as value, count(*) as count  FROM Products p WHERE p.type = :type GROUP BY p.size
        """)
    public List<Object[]> findSpecificationsProducts(@Param("type") String type);

    @Query("""
     SELECT p FROM Products p WHERE p.type = :type ORDER BY p.price DESC
     """)
    public List<Products> findDescPriceByType(@Param("type") String type);

    @Query("""
     SELECT p FROM Products p WHERE p.type = :type ORDER BY p.price ASC
     """)
    public List<Products> findAscPriceByType(@Param("type") String type);

    @Query("""
    SELECT p FROM Products p WHERE p.discount > 0 AND p.type = :type
    """)
    List<Products> findOnlyDiscount(@Param("type") String type);

    @Query("Select min(p.priceWithDiscount) FROM Products p WHERE p.type = :type")
    Integer findProductWithMinPriceByType(@Param("type") String type);

    @Query("Select max(p.priceWithDiscount) FROM Products p WHERE p.type = :type")
    Integer findProductWithMaxPriceByType(@Param("type") String type);

}
