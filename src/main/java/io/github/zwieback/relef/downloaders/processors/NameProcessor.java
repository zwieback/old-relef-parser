package io.github.zwieback.relef.downloaders.processors;

import io.github.zwieback.relef.services.StringService;
import io.github.zwieback.relef.services.TransliterateService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import java.nio.charset.Charset;

@Component
public class NameProcessor {

    private final Charset defaultCharset;
    private final StringService stringService;
    private final TransliterateService transliterateService;

    @Autowired
    public NameProcessor(Charset defaultCharset,
                         StringService stringService,
                         TransliterateService transliterateService) {
        this.defaultCharset = defaultCharset;
        this.stringService = stringService;
        this.transliterateService = transliterateService;
    }

    /**
     * Transliterate and replace special characters in source string.
     *
     * @param name source string
     * @return processed file name
     */
    @NotNull
    public String getProcessedFileName(@NotNull String name) {
        String transliterated = transliterateService.transliterate(name);
        return stringService.replaceSpecialCharsByUnderscore(transliterated);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.web.util.HtmlUtils#htmlEscape(String, String)
     */
    @NotNull
    public String getProcessedName(@NotNull String name) {
        return HtmlUtils.htmlEscape(name, defaultCharset.name());
    }
}
