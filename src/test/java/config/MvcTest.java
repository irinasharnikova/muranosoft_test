package config;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.annotation.*;

/**
 * @author Irina Sharnikova on 7/12/2018.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ContextHierarchy({
        @ContextConfiguration("../config/MvcTestConfig")
})
@WebAppConfiguration
public @interface MvcTest {
}
