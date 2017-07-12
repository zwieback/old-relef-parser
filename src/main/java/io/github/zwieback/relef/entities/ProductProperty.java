package io.github.zwieback.relef.entities;

import com.google.common.base.MoreObjects;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@IdClass(ProductProperty.Pk.class)
@Table(name = "T_PRODUCT_PROPERTY")
public class ProductProperty extends BaseEntity {

    private static final long serialVersionUID = -1631635019093322789L;

    @Data
    public static class Pk implements Serializable {

        private static final long serialVersionUID = -5815488450521721603L;

        private Long productId;
        private Property property;
    }

    @Id
    @Column(name = "product_id")
    @NotNull
    private Long productId = 0L;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    @Nullable
    private Product product;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "name")
    @NotNull
    private Property property = new Property("");

    @Column(name = "value_")
    @NotNull
    private String value = "";

    public ProductProperty(@NotNull Long productId, @NotNull String name, @NotNull String value) {
        this.productId = productId;
        this.property = new Property(name);
        this.value = value;
    }

    @NotNull
    public String getName() {
        return property.getName();
    }

    public void setName(@NotNull String name) {
        this.property = new Property(name);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", property.getName())
                .add("value", value)
                .toString();
    }
}
