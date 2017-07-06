package io.github.zwieback.relef.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "T_MANUFACTURER")
public class Manufacturer extends BaseEntity {

    private static final long serialVersionUID = -4916567614029320314L;

    @Id
    @NotNull
    private String name = "";

    @NotNull
    private String url = "";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "manufacturer")
    private List<Product> products = new ArrayList<>();

    public Manufacturer(@NotNull String name, @NotNull String url) {
        this.name = name;
        this.url = url;
    }
}
