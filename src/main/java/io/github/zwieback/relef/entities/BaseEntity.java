package io.github.zwieback.relef.entities;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 341024496048209619L;

    @Column(name = "last_update")
    @NotNull
    private LocalDateTime lastUpdate = LocalDateTime.now();

    @PreUpdate
    private void preUpdate() {
        lastUpdate = LocalDateTime.now();
    }
}
