package io.github.zwieback.relef.entities;

import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "T_CATALOG")
public class Catalog extends BaseEntity {

    private static final long serialVersionUID = -4120555435017913704L;

    @Id
    @NotNull
    private Long id = 0L;

    @NotNull
    private String url = "";

    @NotNull
    private String name = "";

    @Enumerated(EnumType.STRING)
    @NotNull
    private CatalogLevel level = CatalogLevel.NONE;

    @Column(name = "parent_id", insertable = false, updatable = false)
    @Nullable
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Nullable
    private Catalog parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    @NotFound(action = NotFoundAction.IGNORE)               // workaround to save tree of catalogs with not null id
    @NotNull
    private List<Catalog> children = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "catalog")
    @NotNull
    private List<Product> products = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Catalog catalog = (Catalog) o;
        return Objects.equals(id, catalog.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
