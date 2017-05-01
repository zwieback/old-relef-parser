package io.github.zwieback.relef;

import io.github.zwieback.relef.configs.ApplicationConfig;
import io.github.zwieback.relef.parsers.ParseRunner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class Application {

    public static void main(String[] args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        ParseRunner parseRunner = context.getBean(ParseRunner.class);
        parseRunner.parse();

        context.close();
    }
}
