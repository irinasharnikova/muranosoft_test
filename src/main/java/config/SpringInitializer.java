package config;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @author Irina Sharnikova on 7/12/2018.
 */
public class SpringInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {SpringConfig.class, WebMvcConfig.class};
    }

    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    public static void initialize(){
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(factory);
        applicationContext.register(SpringConfig.class);
        applicationContext.register(WebMvcConfig.class);
        applicationContext.refresh();
    }
}
