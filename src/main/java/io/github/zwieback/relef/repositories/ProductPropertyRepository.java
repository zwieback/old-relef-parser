package io.github.zwieback.relef.repositories;

import io.github.zwieback.relef.entities.ProductProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPropertyRepository extends JpaRepository<ProductProperty, ProductProperty.Pk> {
}
