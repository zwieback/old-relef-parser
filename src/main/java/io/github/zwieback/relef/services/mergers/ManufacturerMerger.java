package io.github.zwieback.relef.services.mergers;

import io.github.zwieback.relef.entities.Manufacturer;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class ManufacturerMerger extends Merger<Manufacturer, String> {

    @Override
    public Manufacturer merge(@NotNull Manufacturer existedManufacturer, @NotNull Manufacturer parsedManufacturer) {
        if (!parsedManufacturer.getName().equals(existedManufacturer.getName())) {
            return parsedManufacturer;
        }
        if (needToMerge(existedManufacturer.getUrl(), parsedManufacturer.getUrl())) {
            existedManufacturer.setUrl(parsedManufacturer.getUrl());
        }
        return existedManufacturer;
    }

    @NotNull
    @Override
    String getId(Manufacturer manufacturer) {
        return manufacturer.getName();
    }
}
