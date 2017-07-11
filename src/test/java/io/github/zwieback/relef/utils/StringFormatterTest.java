package io.github.zwieback.relef.utils;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Note that @TestExecutionListeners has been configured with an empty list in order to disable the default
 * listeners, which would otherwise require that an ApplicationContext be configured via @ContextConfiguration.
 * <p>
 * Defines class-level metadata for configuring which TestExecutionListeners should be registered with a
 * TestContextManager.
 * Typically, @TestExecutionListeners will be used in conjunction with @ContextConfiguration.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners
public class StringFormatterTest {

    @SneakyThrows(Exception.class)
    @Test
    public void test_constructor_should_be_private() {
        Constructor<StringFormatter> constructor = StringFormatter.class.getDeclaredConstructor();
        assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void test_formatDouble_of_PI_should_be_correctly_formatted() {
        char decimalSeparator = getDecimalSeparator();
        String PI_CORRECTLY_FORMATTED = "3" + decimalSeparator + "142";

        String piFormatted = StringFormatter.formatDouble(Math.PI);
        assertThat(piFormatted).isEqualTo(PI_CORRECTLY_FORMATTED);
    }

    @Test
    public void test_build_with_headers_should_not_be_empty() {
        char decimalSeparator = getDecimalSeparator();
        String E_CORRECTLY_FORMATTED = "2" + decimalSeparator + "718";

        String eFormatted = StringFormatter.formatDouble(Math.E);
        assertThat(eFormatted).isEqualTo(E_CORRECTLY_FORMATTED);
    }

    private static char getDecimalSeparator() {
        NumberFormat nf = DecimalFormat.getInstance();
        DecimalFormat df = (DecimalFormat) nf;
        DecimalFormatSymbols symbols = df.getDecimalFormatSymbols();
        return symbols.getDecimalSeparator();
    }
}
