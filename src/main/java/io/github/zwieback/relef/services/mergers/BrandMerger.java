package io.github.zwieback.relef.services.mergers;

import io.github.zwieback.relef.entities.Brand;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class BrandMerger extends Merger<Brand, Long> {

    @Override
    public Brand merge(@NotNull Brand existedBrand, @NotNull Brand parsedBrand) {
        if (!parsedBrand.getId().equals(existedBrand.getId())) {
            return parsedBrand;
        }
        if (needToMerge(existedBrand.getName(), parsedBrand.getName())) {
            existedBrand.setName(parsedBrand.getName());
        }
        if (needToMerge(existedBrand.getUrl(), parsedBrand.getUrl())) {
            existedBrand.setUrl(parsedBrand.getUrl());
        }
        if (needToMerge(existedBrand.getImageUrl(), parsedBrand.getImageUrl())) {
            existedBrand.setImageUrl(parsedBrand.getImageUrl());
        }
        return existedBrand;
    }

    @NotNull
    @Override
    Long getId(Brand brand) {
        return brand.getId();
    }
}
