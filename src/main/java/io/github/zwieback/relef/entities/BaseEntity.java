package io.github.zwieback.relef.entities;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 341024496048209619L;

    @Column(name = "last_update")
    @NotNull
    private LocalDateTime lastUpdate;

    BaseEntity() {
        lastUpdate = LocalDateTime.now();
    }

    @NotNull
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(@NotNull LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @PreUpdate
    private void preUpdate() {
        lastUpdate = LocalDateTime.now();
    }
}
