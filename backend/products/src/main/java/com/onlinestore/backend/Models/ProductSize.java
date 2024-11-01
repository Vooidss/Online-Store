package com.onlinestore.backend.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "product_size")
@IdClass(ProductSizeId.class)
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductSize {

    @Id
    @Column(name = "product_id") 
    private Integer productId;

    @Id
    @Column(name = "size_id")
    private Long sizeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Products product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Sizes size;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSize that = (ProductSize) o;
        return Objects.equals(productId, that.productId) && Objects.equals(sizeId, that.sizeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, sizeId);
    }
}
class ProductSizeId implements Serializable {
    private Integer productId;
    private Long sizeId;

    public ProductSizeId() {}
}
