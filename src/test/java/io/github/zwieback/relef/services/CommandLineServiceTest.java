package io.github.zwieback.relef.services;

import io.github.zwieback.relef.configs.ServiceConfig;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        assertTrue(options.hasOption(OPTION_HELP));
    }

    @Test
    public void test_createOptions_should_contains_parser_option() {
        Options options = cmdService.createOptions();
        assertTrue(options.hasOption(OPTION_PARSER_PRODUCT));
    }

    @Test
    public void test_createOptions_should_contains_export_option() {
        Options options = cmdService.createOptions();
        assertTrue(options.hasOption(OPTION_EXPORT_PRODUCT));
    }

    @Test
    public void test_createCommandLine_should_contains_help_argument() throws ParseException {
        CommandLine cmd = buildCommandLine(new String[]{OPTION_HELP});
        assertTrue(cmd.getArgList().contains(OPTION_HELP));
    }

    @Test
    public void test_createCommandLine_should_contains_parsers_and_exports_and_help_arguments() throws ParseException {
        CommandLine cmd = buildCommandLine(new String[]{OPTION_HELP, OPTION_PARSER_PRODUCT,
                OPTION_PARSER_CATALOG, OPTION_EXPORT_PRODUCT, OPTION_EXPORT_CATALOG});
        assertTrue(cmd.getArgList().contains(OPTION_HELP));
    }

    @Test
    public void test_printHelp_should_print_help_argument() throws ParseException, UnsupportedEncodingException {
        PrintStream defaultStdOut = System.out;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream, false, defaultCharset.name()));

            Options options = cmdService.createOptions();
            cmdService.printHelp(options);

            String help = outputStream.toString(defaultCharset.name());
            assertTrue(help.contains(OPTION_HELP));
        } finally {
            System.setOut(defaultStdOut);
        }
    }

    @Test
    public void test_doesCommandLineContainsAnyParserOptions_should_return_false() throws ParseException {
        CommandLine cmd = buildEmptyCommandLine();
        assertFalse(cmdService.doesCommandLineContainsAnyParserOptions(cmd));
    }

    @Test
    public void test_doesCommandLineContainsAnyParserOptions_should_return_true() throws ParseException {
        CommandLine cmd = buildCommandLine(new String[]{"-" + OPTION_PARSER_PRODUCT, "0"});
        assertTrue(cmdService.doesCommandLineContainsAnyParserOptions(cmd));
    }

    @Test
    public void test_doesCommandLineContainsAnyExportOptions_should_return_false() throws ParseException {
        CommandLine cmd = buildEmptyCommandLine();
        assertFalse(cmdService.doesCommandLineContainsAnyExportOptions(cmd));
    }

    @Test
    public void test_doesCommandLineContainsAnyExportOptions_should_return_true() throws ParseException {
        CommandLine cmd = buildCommandLine(new String[]{"-" + OPTION_EXPORT_PRODUCT});
        assertTrue(cmdService.doesCommandLineContainsAnyExportOptions(cmd));
    }

    @Test
    public void test_doesCommandLineContainsAnyImportOptions_should_return_false() throws ParseException {
        CommandLine cmd = buildEmptyCommandLine();
        assertFalse(cmdService.doesCommandLineContainsAnyImportOptions(cmd));
    }

    @Test
    public void test_doesCommandLineContainsAnyImportOptions_should_return_true() throws ParseException {
        CommandLine cmd = buildCommandLine(new String[]{"-" + OPTION_IMPORT_MY_SKLAD_PRODUCT, "f.xlsx"});
        assertTrue(cmdService.doesCommandLineContainsAnyImportOptions(cmd));
    }

    @Test
    public void test_doesCommandLineContainsAnyDownloadOptions_should_return_false() throws ParseException {
        CommandLine cmd = buildEmptyCommandLine();
        assertFalse(cmdService.doesCommandLineContainsAnyDownloadOptions(cmd));
    }

    @Test
    public void test_doesCommandLineContainsAnyDownloadOptions_should_return_true() throws ParseException {
        CommandLine cmd = buildCommandLine(new String[]{"-" + OPTION_DOWNLOAD_PRODUCT_IMAGE});
        assertTrue(cmdService.doesCommandLineContainsAnyDownloadOptions(cmd));
    }

    @Test
    public void test_doesCommandLineContainsHelpOption_should_return_true() throws ParseException {
        CommandLine cmd = buildEmptyCommandLine();
        assertTrue(cmdService.doesCommandLineContainsHelpOption(cmd));
    }

    @Test
    public void test_doesCommandLineContainsHelpOption_should_return_true_too() throws ParseException {
        CommandLine cmd = buildCommandLine(new String[]{"-" + OPTION_HELP});
        assertTrue(cmdService.doesCommandLineContainsHelpOption(cmd));
    }

    @Test
    public void test_doesCommandLineContainsHelpOption_should_return_false() throws ParseException {
        CommandLine cmd = buildCommandLine(new String[]{"-" + OPTION_EXPORT_PRODUCT});
        assertFalse(cmdService.doesCommandLineContainsHelpOption(cmd));
    }

    private CommandLine buildEmptyCommandLine() throws ParseException {
        return buildCommandLine(new String[]{});
    }

    private CommandLine buildCommandLine(String[] args) throws ParseException {
        Options options = cmdService.createOptions();
        return cmdService.createCommandLine(options, args);
    }
}
