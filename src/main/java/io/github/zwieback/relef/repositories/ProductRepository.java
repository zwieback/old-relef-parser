package io.github.zwieback.relef.repositories;

import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.entities.dto.my.sklad.MsProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select id from Product where id in :ids")
    List<Long> findExistedIdsByIdIn(@Param("ids") List<Long> ids);

    @Query("select new io.github.zwieback.relef.entities.dto.my.sklad.MsProductDto(" +
            "  c1.name || '/' || c2.name || '/' || c3.name || '/' || c4.name," +
            "  p.code," +
            "  p.article," +
            "  p.name," +
            "  p.description," +
            "  p.price," +
            "  p.oldPrice," +
            "  p.barcode," +
            "  p.manufacturerCountry," +
            "  m.name," +
            "  tm.name," +
            "  p.weight," +
            "  p.volume" +
            ")" +
            "  from Product p" +
            "    inner join p.catalog c4" +
            "       inner join c4.parent c3" +
            "       inner join c3.parent c2" +
            "       inner join c2.parent c1" +
            "    inner join p.manufacturer m" +
            "    inner join p.tradeMark tm")
    List<MsProductDto> findAllMsProducts();
}
