package io.github.zwieback.relef.services;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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

    /**
     * Returns either the passed in String, or if the String is null, an empty String ("").
     * <pre>
     * StringUtils.defaultString(null)  = ""
     * StringUtils.defaultString("")    = ""
     * StringUtils.defaultString("bat") = "bat"
     * </pre>
     *
     * @param nullableString the String to check, may be null
     * @return the passed in String, or the empty String if it was null
     */
    @NotNull
    public String defaultString(@Nullable String nullableString) {
        return nullableString == null ? "" : nullableString;
    }

    /**
     * Returns either the string representation of passed Object, or if the Object is null, an empty String ("").
     * <pre>
     * StringUtils.defaultString(null)  = ""
     * StringUtils.defaultString(101L) = "101"
     * </pre>
     *
     * @param nullableObject the Object to check, may be null
     * @return the string representation of passed Object, or the empty String if it was null
     */
    @NotNull
    public String defaultString(@Nullable Object nullableObject) {
        return nullableObject == null ? "" : nullableObject.toString();
    }
}
