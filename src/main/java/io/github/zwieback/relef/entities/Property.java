package io.github.zwieback.relef.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@Entity
@Table(name = "T_PROPERTY")
public class Property extends BaseEntity {

    private static final long serialVersionUID = 6908666417340046198L;

    @Id
    @NotNull
    private String name;
}
