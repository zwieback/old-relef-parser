package io.github.zwieback.relef.services;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
public class StringService {

    private static final Pattern SPECIAL_CHARS = Pattern.compile("\\W");
    private static final String UNDERSCORE = "_";

    private final NumberFormat numberFormat;

    @Autowired
    public StringService(Locale defaultLocale) {
        this.numberFormat = NumberFormat.getInstance(defaultLocale);
    }

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
     * Replace all special characters by underscore character.
     *
     * @param source source string
     * @return string with replaced characters
     */
    public String replaceSpecialCharsByUnderscore(String source) {
        return SPECIAL_CHARS.matcher(source).replaceAll(UNDERSCORE);
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

    /**
     * Parse the string and return Double as the value representation.
     *
     * @param value source string value
     * @return parsed Double
     */
    @SneakyThrows(ParseException.class)
    @Nullable
    public Double parseToDouble(@Nullable String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        Number number = numberFormat.parse(value);
        return number.doubleValue();
    }

    /**
     * Parse the string and return Boolean as the value representation.
     *
     * @param value source string value
     * @return parsed Boolean
     */
    @Nullable
    public Boolean parseToBoolean(@Nullable String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        switch (value.toLowerCase()) {
            case "да":
                return Boolean.TRUE;
            default:
                return Boolean.valueOf(value);
        }
    }
}
