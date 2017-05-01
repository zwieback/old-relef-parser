package io.github.zwieback.relef.entities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@IdClass(ProductProperty.Pk.class)
public class ProductProperty extends BaseEntity {

    public static class Pk implements Serializable {

        private static final long serialVersionUID = -5815488450521721603L;

        Long productId;
        Property property;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pk pk = (Pk) o;
            return productId.equals(pk.productId) && property.equals(pk.property);
        }

        @Override
        public int hashCode() {
            int result = productId.hashCode();
            result = 31 * result + property.hashCode();
            return result;
        }
    }

    @Id
    @Column(name = "product_id")
    @NotNull
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    @Nullable
    private Product product;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "name")
    @NotNull
    private Property property;

    @NotNull
    private String value;

    public ProductProperty() {
        productId = 0L;
        property = new Property();
        value = "";
    }

    public ProductProperty(@NotNull Long productId, @NotNull String name, @NotNull String value) {
        this.productId = productId;
        this.property = new Property(name);
        this.value = value;
    }

    @NotNull
    public Long getProductId() {
        return productId;
    }

    public void setProductId(@NotNull Long productId) {
        this.productId = productId;
    }

    @Nullable
    public Product getProduct() {
        return product;
    }

    public void setProduct(@Nullable Product product) {
        this.product = product;
    }

    @NotNull
    public String getName() {
        return property.getName();
    }

    public void setName(@NotNull String name) {
        this.property = new Property(name);
    }

    @NotNull
    public Property getProperty() {
        return property;
    }

    public void setProperty(@NotNull Property property) {
        this.property = property;
    }

    @NotNull
    public String getValue() {
        return value;
    }

    public void setValue(@NotNull String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ProductProperty{" +
                "name='" + property.getName() + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
