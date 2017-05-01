package io.github.zwieback.relef.repositories;

import io.github.zwieback.relef.entities.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, String> {
}
