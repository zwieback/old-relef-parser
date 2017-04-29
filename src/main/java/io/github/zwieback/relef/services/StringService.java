package io.github.zwieback.relef.services;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class StringService {

    /**
     * Clean string from redundant symbols.
     * <p>
     * Example input:
     * <li>
     * Jsoup.parse({@literal &nbsp;}) to {@literal \u005Cu00A0}
     * </li>
     *
     * @param toClean source string
     * @return cleaned string
     */
    @NotNull
    public String clean(String toClean) {
        return toClean.replace("\u00A0", "").trim();
    }
}
