package io.github.nhomble.repro;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.function.context.FunctionalSpringApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;

@SpringBootConfiguration
public class Application implements ApplicationContextInitializer<GenericApplicationContext> {

    @Override
    public void initialize(GenericApplicationContext genericApplicationContext) {
        genericApplicationContext.registerBeanDefinition("myDependency",
                BeanDefinitionBuilder.genericBeanDefinition(Dependency.class, Dependency::new).getBeanDefinition());
        genericApplicationContext.registerBeanDefinition("myBeanWithDependency",
                BeanDefinitionBuilder.genericBeanDefinition(HasDependency.class).getBeanDefinition());
    }

    static class Dependency {

    }

    static class HasDependency {
        private final Dependency dependency;

        HasDependency(Dependency dependency) {
            this.dependency = dependency;
        }
    }

    public static void main(String[] args) {
        FunctionalSpringApplication.run(Application.class, args);
    }
}
