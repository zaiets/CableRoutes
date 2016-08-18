package app.service.functionalityTODO.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * This is temporary solution, all properties will be transferred into 'project' entity
 * (TODO this later)
 */
@Component
@PropertySource("classpath:/props/functionality.properties")
public class PropertiesManager {
    public PropertiesManager() {
    }

    @Autowired
    private Environment environment;

    public <T> T get(String key, Class<T> expectedPropertyClass) {
        return environment.getProperty(key, expectedPropertyClass);
    }

    public String get(String key) {
        return environment.getProperty(key);
    }

}
	
