package io.github.zwieback.relef.web.converters;

import io.github.zwieback.relef.services.HeadersBuilder.Headers;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class HeadersToMultiValueMapConverter implements Converter<Headers, MultiValueMap<String, String>> {

    @Override
    public MultiValueMap<String, String> convert(Headers headers) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.setAll(headers);
        return map;
    }
}
