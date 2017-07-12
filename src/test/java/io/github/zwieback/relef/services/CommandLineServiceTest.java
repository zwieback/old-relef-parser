package io.github.zwieback.relef.services;

import io.github.zwieback.relef.configs.ServiceConfig;
import lombok.SneakyThrows;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import static io.github.zwieback.relef.services.CommandLineService.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        ServiceConfig.class
})
public class CommandLineServiceTest {

    @SuppressWarnings("unused")
    @Autowired
    private CommandLineService cmdService;

    @SuppressWarnings("unused")
    @Autowired
    private Charset defaultCharset;

    @Test
    public void test_createOptions_should_contains_help_option() {
        Options options = cmdService.createOptions();
        assertThat(options.hasOption(OPTION_HELP)).isTrue();
    }

    @Test
    public void test_createOptions_should_contains_parser_option() {
        Options options = cmdService.createOptions();
        assertThat(options.hasOption(OPTION_PARSER_PRODUCT)).isTrue();
    }

    @Test
    public void test_createOptions_should_contains_export_option() {
        Options options = cmdService.createOptions();
        assertThat(options.hasOption(OPTION_EXPORT_PRODUCT)).isTrue();
    }

    @Test
    public void test_createCommandLine_should_contains_help_argument() {
        CommandLine cmd = buildCommandLine(new String[]{OPTION_HELP});
        assertThat(cmd.getArgList().contains(OPTION_HELP)).isTrue();
    }

    @Test
    public void test_createCommandLine_should_contains_parsers_and_exports_and_help_arguments() {
        CommandLine cmd = buildCommandLine(new String[]{OPTION_HELP, OPTION_PARSER_PRODUCT,
                OPTION_PARSER_CATALOG, OPTION_EXPORT_PRODUCT, OPTION_EXPORT_CATALOG});
        assertThat(cmd.getArgList()).contains(OPTION_HELP);
    }

    @SneakyThrows(UnsupportedEncodingException.class)
    @Test
    public void test_printHelp_should_print_help_argument() {
        PrintStream defaultStdOut = System.out;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream, false, defaultCharset.name()));

            Options options = cmdService.createOptions();
            cmdService.printHelp(options);

            String help = outputStream.toString(defaultCharset.name());
            assertThat(help).contains(OPTION_HELP);
        } finally {
            System.setOut(defaultStdOut);
        }
    }

    @Test
    public void test_doesCommandLineContainsAnyParserOptions_should_return_false() {
        CommandLine cmd = buildEmptyCommandLine();
        assertThat(cmdService.doesCommandLineContainsAnyParserOptions(cmd)).isFalse();
    }

    @Test
    public void test_doesCommandLineContainsAnyParserOptions_should_return_true() {
        CommandLine cmd = buildCommandLine(new String[]{"-" + OPTION_PARSER_PRODUCT, "0"});
        assertThat(cmdService.doesCommandLineContainsAnyParserOptions(cmd)).isTrue();
    }

    @Test
    public void test_doesCommandLineContainsAnyExportOptions_should_return_false() {
        CommandLine cmd = buildEmptyCommandLine();
        assertThat(cmdService.doesCommandLineContainsAnyExportOptions(cmd)).isFalse();
    }

    @Test
    public void test_doesCommandLineContainsAnyExportOptions_should_return_true() {
        CommandLine cmd = buildCommandLine(new String[]{"-" + OPTION_EXPORT_PRODUCT});
        assertThat(cmdService.doesCommandLineContainsAnyExportOptions(cmd)).isTrue();
    }

    @Test
    public void test_doesCommandLineContainsAnyImportOptions_should_return_false() {
        CommandLine cmd = buildEmptyCommandLine();
        assertThat(cmdService.doesCommandLineContainsAnyImportOptions(cmd)).isFalse();
    }

    @Test
    public void test_doesCommandLineContainsAnyImportOptions_should_return_true() {
        CommandLine cmd = buildCommandLine(new String[]{"-" + OPTION_IMPORT_MY_SKLAD_PRODUCT, "f.xlsx"});
        assertThat(cmdService.doesCommandLineContainsAnyImportOptions(cmd)).isTrue();
    }

    @Test
    public void test_doesCommandLineContainsAnyDownloadOptions_should_return_false() {
        CommandLine cmd = buildEmptyCommandLine();
        assertThat(cmdService.doesCommandLineContainsAnyDownloadOptions(cmd)).isFalse();
    }

    @Test
    public void test_doesCommandLineContainsAnyDownloadOptions_should_return_true() {
        CommandLine cmd = buildCommandLine(new String[]{"-" + OPTION_DOWNLOAD_PRODUCT_IMAGE});
        assertThat(cmdService.doesCommandLineContainsAnyDownloadOptions(cmd)).isTrue();
    }

    @Test
    public void test_doesCommandLineContainsAnyAnalyzeOptions_should_return_false() {
        CommandLine cmd = buildEmptyCommandLine();
        assertThat(cmdService.doesCommandLineContainsAnyAnalyzeOptions(cmd)).isFalse();
    }

    @Test
    public void test_doesCommandLineContainsAnyAnalyzeOptions_should_return_true() {
        CommandLine cmd = buildCommandLine(new String[]{"-" + OPTION_ANALYZE_MY_SKLAD_PRODUCT, "f.xlsx"});
        assertThat(cmdService.doesCommandLineContainsAnyAnalyzeOptions(cmd)).isTrue();
    }

    @Test
    public void test_doesCommandLineContainsHelpOption_should_return_true() {
        CommandLine cmd = buildEmptyCommandLine();
        assertThat(cmdService.doesCommandLineContainsHelpOption(cmd)).isTrue();
    }

    @Test
    public void test_doesCommandLineContainsHelpOption_should_return_true_too() {
        CommandLine cmd = buildCommandLine(new String[]{"-" + OPTION_HELP});
        assertThat(cmdService.doesCommandLineContainsHelpOption(cmd)).isTrue();
    }

    @Test
    public void test_doesCommandLineContainsHelpOption_should_return_false() {
        CommandLine cmd = buildCommandLine(new String[]{"-" + OPTION_EXPORT_PRODUCT});
        assertThat(cmdService.doesCommandLineContainsHelpOption(cmd)).isFalse();
    }

    private CommandLine buildEmptyCommandLine() {
        return buildCommandLine(new String[]{});
    }

    private CommandLine buildCommandLine(String[] args) {
        Options options = cmdService.createOptions();
        return cmdService.createCommandLine(options, args);
    }
}
