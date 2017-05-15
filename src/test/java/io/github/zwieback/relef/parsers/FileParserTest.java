package io.github.zwieback.relef.parsers;

import io.github.zwieback.relef.configs.ParserConfig;
import io.github.zwieback.relef.configs.ServiceConfig;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        ParserConfig.class,
        ServiceConfig.class
})
public class FileParserTest {

    @SuppressWarnings("unused")
    @Autowired
    private FileParser fileParser;


    @Test(expected = UncheckedIOException.class)
    public void test_parseInputStream_should_throws_exception() throws IOException {
        fileParser.parseInputStream(getInputStreamThatThrowsIOException());
    }

    @NotNull
    private static InputStream getInputStreamThatThrowsIOException() {
        return new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException();
            }
        };
    }
}
