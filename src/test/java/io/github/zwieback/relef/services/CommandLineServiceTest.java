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

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        ServiceConfig.class
})
public class CommandLineServiceTest {

    @SuppressWarnings("unused")
    @Autowired
    private CommandLineService commandLineService;

    @Test
    public void test_createOptions_should_contains_help_option() {
        Options options = commandLineService.createOptions();
        assertTrue(options.hasOption(CommandLineService.OPTION_HELP));
    }

    @Test
    public void test_createCommandLine_should_contains_help_argument() throws ParseException {
        Options options = commandLineService.createOptions();
        CommandLine cmd = commandLineService.createCommandLine(options, new String[]{CommandLineService.OPTION_HELP});
        assertTrue(cmd.getArgList().contains(CommandLineService.OPTION_HELP));
    }

    @Test
    public void test_printHelp_should_print_help_argument() throws ParseException {
        PrintStream defaultStdOut = System.out;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            Options options = commandLineService.createOptions();
            commandLineService.printHelp(options);

            String help = outputStream.toString();
            assertTrue(help.contains(CommandLineService.OPTION_HELP));
        } finally {
            System.setOut(defaultStdOut);
        }
    }
}
