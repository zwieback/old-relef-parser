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
@Table(name = "T_TRADE_MARK")
public class TradeMark extends BaseEntity {

    private static final long serialVersionUID = 8861559741783219433L;

    @Id
    @NotNull
    private String name = "";

    @NotNull
    private String url = "";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tradeMark")
    private List<Product> products = new ArrayList<>();

    public TradeMark(@NotNull String name, @NotNull String url) {
        this.name = name;
        this.url = url;
    }
}
