package io.github.zwieback.relef.utils.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.github.zwieback.relef.entities.dto.product.prices.ErrorDto;
import io.github.zwieback.relef.entities.dto.product.prices.ProductDto;
import io.github.zwieback.relef.entities.dto.product.prices.ProductPricesDto;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProductPricesDeserializer extends StdDeserializer<ProductPricesDto> {

    private static final long serialVersionUID = -7904441392821302687L;

    private static final TypeReference<Map<Long, ProductDto>> PRODUCT_MAP_TYPE_REF =
            new TypeReference<Map<Long, ProductDto>>() {
            };

    public ProductPricesDeserializer() {
        super(ProductPricesDto.class);
    }

    @SneakyThrows(IOException.class)
    @Override
    public ProductPricesDto deserialize(JsonParser jsonParser, DeserializationContext context) {
        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode rootNode = objectCodec.readTree(jsonParser);

        return new ProductPricesDto()
                .setAction(rootNode.path("action").textValue())
                .setCanBuy(rootNode.path("canbuy").booleanValue())
                .setCompare(rootNode.path("compare").intValue())
                .setSoapm(rootNode.path("soapm").booleanValue())
                .setError(parseError(objectCodec, rootNode))
                .setProductMap(parseProductMap(objectCodec, rootNode));
    }

    @SneakyThrows(JsonProcessingException.class)
    @Nullable
    private ErrorDto parseError(ObjectCodec objectCodec, JsonNode rootNode) {
        if (rootNode.has("error")) {
            return objectCodec.treeToValue(rootNode.path("error"), ErrorDto.class);
        }
        return null;
    }

    @SneakyThrows(IOException.class)
    private Map<Long, ProductDto> parseProductMap(ObjectCodec objectCodec, JsonNode rootNode) {
        if (rootNode.has("product")) {
            JsonNode productsNode = rootNode.path("product");
            if (productsNode.isObject()) {
                JsonParser productsParser = productsNode.traverse(objectCodec);
                return objectCodec.readValue(productsParser, PRODUCT_MAP_TYPE_REF);
            }
        }
        return new LinkedHashMap<>();
    }

    @SneakyThrows(IOException.class)
    private Map<Long, ProductDto> alternativeWayToParseProductMap(ObjectCodec objectCodec, JsonNode rootNode) {
        Map<Long, ProductDto> productMap = new LinkedHashMap<>();
        if (rootNode.has("product")) {
            Iterator<Map.Entry<String, JsonNode>> iterator = rootNode.path("product").fields();
            while (iterator.hasNext()) {
                Map.Entry<String, JsonNode> entry = iterator.next();
                JsonNode productNode = entry.getValue();
                ProductDto product = objectCodec.treeToValue(productNode, ProductDto.class);
                productMap.put(Long.valueOf(entry.getKey()), product);
            }
        }
        return productMap;
    }
}
