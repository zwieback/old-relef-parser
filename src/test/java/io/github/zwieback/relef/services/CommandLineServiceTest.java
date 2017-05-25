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

import static io.github.zwieback.relef.services.CommandLineService.OPTION_EXPORT_PRODUCT;
import static io.github.zwieback.relef.services.CommandLineService.OPTION_HELP;
import static io.github.zwieback.relef.services.CommandLineService.OPTION_PARSER_PRODUCT;
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

    @Test
    public void test_createOptions_should_contains_help_option() {
        Options options = cmdService.createOptions();
        assertTrue(options.hasOption(OPTION_HELP));
    }

    @Test
    public void test_createCommandLine_should_contains_help_argument() throws ParseException {
        Options options = cmdService.createOptions();
        CommandLine cmd = cmdService.createCommandLine(options, new String[]{OPTION_HELP});
        assertTrue(cmd.getArgList().contains(OPTION_HELP));
    }

    @Test
    public void test_printHelp_should_print_help_argument() throws ParseException {
        PrintStream defaultStdOut = System.out;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            Options options = cmdService.createOptions();
            cmdService.printHelp(options);

            String help = outputStream.toString();
            assertTrue(help.contains(OPTION_HELP));
        } finally {
            System.setOut(defaultStdOut);
        }
    }

    @Test
    public void test_doesCommandLineContainsAnyParserOptions_should_return_false() throws ParseException {
        Options options = cmdService.createOptions();
        CommandLine cmd = cmdService.createCommandLine(options, new String[]{});
        assertFalse(cmdService.doesCommandLineContainsAnyParserOptions(cmd));
    }

    @Test
    public void test_doesCommandLineContainsAnyParserOptions_should_return_true() throws ParseException {
        Options options = cmdService.createOptions();
        CommandLine cmd = cmdService.createCommandLine(options, new String[]{"-" + OPTION_PARSER_PRODUCT, "0"});
        assertTrue(cmdService.doesCommandLineContainsAnyParserOptions(cmd));
    }

    @Test
    public void test_doesCommandLineContainsAnyExportOptions_should_return_false() throws ParseException {
        Options options = cmdService.createOptions();
        CommandLine cmd = cmdService.createCommandLine(options, new String[]{});
        assertFalse(cmdService.doesCommandLineContainsAnyExportOptions(cmd));
    }

    @Test
    public void test_doesCommandLineContainsAnyExportOptions_should_return_true() throws ParseException {
        Options options = cmdService.createOptions();
        CommandLine cmd = cmdService.createCommandLine(options, new String[]{"-" + OPTION_EXPORT_PRODUCT});
        assertTrue(cmdService.doesCommandLineContainsAnyExportOptions(cmd));
    }

    @Test
    public void test_doesCommandLineContainsHelpOption_should_return_true() throws ParseException {
        Options options = cmdService.createOptions();
        CommandLine cmd = cmdService.createCommandLine(options, new String[]{});
        assertTrue(cmdService.doesCommandLineContainsHelpOption(cmd));
    }

    @Test
    public void test_doesCommandLineContainsHelpOption_should_return_true_too() throws ParseException {
        Options options = cmdService.createOptions();
        CommandLine cmd = cmdService.createCommandLine(options, new String[]{"-" + OPTION_HELP});
        assertTrue(cmdService.doesCommandLineContainsHelpOption(cmd));
    }

    @Test
    public void test_doesCommandLineContainsHelpOption_should_return_false() throws ParseException {
        Options options = cmdService.createOptions();
        CommandLine cmd = cmdService.createCommandLine(options, new String[]{"-" + OPTION_EXPORT_PRODUCT});
        assertFalse(cmdService.doesCommandLineContainsHelpOption(cmd));
    }
}
