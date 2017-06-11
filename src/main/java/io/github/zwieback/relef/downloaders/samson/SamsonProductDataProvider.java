package io.github.zwieback.relef.downloaders.samson;

import io.github.zwieback.relef.entities.dto.samson.SamsonProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class SamsonProductDataProvider {

    private final List<SamsonProductDto> products;

    public SamsonProductDataProvider(List<SamsonProductDto> products) {
        this.products = products;
    }

    public Page<SamsonProductDto> extractSubList(Pageable pageable) {
        int start = pageable.getOffset();
        int end = start + pageable.getPageSize() > products.size() ? products.size() : start + pageable.getPageSize();
        return new PageImpl<>(products.subList(start, end), pageable, products.size());
    }
}
