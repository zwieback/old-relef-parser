package io.github.zwieback.relef.services.mergers;

import io.github.zwieback.relef.entities.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class ProductMerger extends Merger<Product, Long> {

    @Override
    public Product merge(@NotNull Product existedProduct, @NotNull Product parsedProduct) {
        if (!parsedProduct.getId().equals(existedProduct.getId())) {
            return parsedProduct;
        }
        if (needToMerge(existedProduct.getCatalogId(), parsedProduct.getCatalogId())) {
            existedProduct.setCatalogId(parsedProduct.getCatalogId());
        }
        if (needToMerge(existedProduct.getCode(), parsedProduct.getCode())) {
            existedProduct.setCode(parsedProduct.getCode());
        }
        if (needToMerge(existedProduct.getArticle(), parsedProduct.getArticle())) {
            existedProduct.setArticle(parsedProduct.getArticle());
        }
        if (needToMerge(existedProduct.getBarcode(), parsedProduct.getBarcode())) {
            existedProduct.setBarcode(parsedProduct.getBarcode());
        }
        if (needToMerge(existedProduct.getManufacturerCountry(), parsedProduct.getManufacturerCountry())) {
            existedProduct.setManufacturerCountry(parsedProduct.getManufacturerCountry());
        }
        if (needToMerge(existedProduct.getName(), parsedProduct.getName())) {
            existedProduct.setName(parsedProduct.getName());
        }
        if (needToMerge(existedProduct.getDescription(), parsedProduct.getDescription())) {
            existedProduct.setDescription(parsedProduct.getDescription());
        }
        if (needToMerge(existedProduct.getUrl(), parsedProduct.getUrl())) {
            existedProduct.setUrl(parsedProduct.getUrl());
        }
        if (needToMerge(existedProduct.getPhotoUrl(), parsedProduct.getPhotoUrl())) {
            existedProduct.setPhotoUrl(parsedProduct.getPhotoUrl());
        }
        if (needToMerge(existedProduct.getPhotoCachedUrl(), parsedProduct.getPhotoCachedUrl())) {
            existedProduct.setPhotoCachedUrl(parsedProduct.getPhotoCachedUrl());
        }
        if (needToMerge(existedProduct.getManufacturer(), parsedProduct.getManufacturer())) {
            existedProduct.setManufacturer(parsedProduct.getManufacturer());
        }
        if (needToMerge(existedProduct.getTradeMark(), parsedProduct.getTradeMark())) {
            existedProduct.setTradeMark(parsedProduct.getTradeMark());
        }
        if (needToMerge(existedProduct.getParty(), parsedProduct.getParty())) {
            existedProduct.setParty(parsedProduct.getParty());
        }
        if (needToMerge(existedProduct.getWeight(), parsedProduct.getWeight())) {
            existedProduct.setWeight(parsedProduct.getWeight());
        }
        if (needToMerge(existedProduct.getVolume(), parsedProduct.getVolume())) {
            existedProduct.setVolume(parsedProduct.getVolume());
        }
        if (needToMerge(existedProduct.getXmlId(), parsedProduct.getXmlId())) {
            existedProduct.setXmlId(parsedProduct.getXmlId());
        }
        if (needToMerge(existedProduct.getDataType(), parsedProduct.getDataType())) {
            existedProduct.setDataType(parsedProduct.getDataType());
        }
        if (needToMerge(existedProduct.getPrice(), parsedProduct.getPrice())) {
            existedProduct.setPrice(parsedProduct.getPrice());
        }
        if (needToMerge(existedProduct.getAmount(), parsedProduct.getAmount())) {
            existedProduct.setAmount(parsedProduct.getAmount());
        }
        if (needToMerge(existedProduct.getAvailable(), parsedProduct.getAvailable())) {
            existedProduct.setAvailable(parsedProduct.getAvailable());
        }
        if (needToMerge(existedProduct.getOldPrice(), parsedProduct.getOldPrice())) {
            existedProduct.setOldPrice(parsedProduct.getOldPrice());
        }
        if (needToMerge(existedProduct.getBlackFriday(), parsedProduct.getBlackFriday())) {
            existedProduct.setBlackFriday(parsedProduct.getBlackFriday());
        }
        existedProduct.getProperties().clear();
        existedProduct.getProperties().addAll(parsedProduct.getProperties());
        return existedProduct;
    }

    @NotNull
    @Override
    Long getId(Product product) {
        return product.getId();
    }
}
