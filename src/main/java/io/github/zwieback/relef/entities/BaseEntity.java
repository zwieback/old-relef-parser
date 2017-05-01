package io.github.zwieback.relef.entities;

import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

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
