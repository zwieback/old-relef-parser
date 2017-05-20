package io.github.zwieback.relef.services.mergers;

import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.entities.dto.product.prices.ProductDto;
import io.github.zwieback.relef.entities.dto.product.prices.ProductPricesDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductPriceMerger {

    /**
     * Merge prices for products. Only if price of product exists.
     *
     * @param products source products
     * @param productPricesDto sources prices
     */
    public void mergePrices(List<Product> products, ProductPricesDto productPricesDto) {
        Map<Long, ProductDto> productMap = productPricesDto.getProductMap();
        if (productMap.isEmpty()) {
            return;
        }
        products.stream()
                .filter(product -> productMap.containsKey(product.getId()))
                .forEach(product -> {
                    ProductDto productDto = productMap.get(product.getId());
                    product
                            .setPrice(productDto.getPrice())
                            // not merge, because amount is intended for internal use
//                            .setAmount(productDto.getAmount())
                            .setAvailable(productDto.getAvailable())
                            .setOldPrice(productDto.getOldPrice())
                            .setBlackFriday(productDto.getBlackFriday());
                });
    }
}
