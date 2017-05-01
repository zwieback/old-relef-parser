package io.github.zwieback.relef.repositories;

import io.github.zwieback.relef.entities.TradeMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeMarkRepository extends JpaRepository<TradeMark, String> {
}
