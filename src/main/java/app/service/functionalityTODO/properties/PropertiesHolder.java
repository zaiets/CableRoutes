package app.service.functionalityTODO.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/props/application.properties")
public class PropertiesHolder {
    public PropertiesHolder() {
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
	
