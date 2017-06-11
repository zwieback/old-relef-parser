package io.github.zwieback.relef.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "T_BRAND")
public class Brand extends BaseEntity {

    private static final long serialVersionUID = 8102240085155881341L;

    @Id
    @NotNull
    private Long id = 0L;

    @Nullable
    private String name;

    @Nullable
    private String url;

    @Column(name = "image_url")
    @Nullable
    private String imageUrl;

    public Brand(@NotNull Long id, @Nullable String url, @Nullable String imageUrl) {
        this.id = id;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand = (Brand) o;
        return Objects.equals(id, brand.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
