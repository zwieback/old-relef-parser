package io.github.zwieback.relef.repositories;

import io.github.zwieback.relef.entities.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    @Query("select id from Catalog where id in :ids")
    List<Long> findExistedIdsByIdIn(@Param("ids") List<Long> ids);
}
