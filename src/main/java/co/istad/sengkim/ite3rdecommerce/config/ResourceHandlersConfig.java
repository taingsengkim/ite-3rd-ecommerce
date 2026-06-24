package co.istad.sengkim.ite3rdecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceHandlersConfig implements WebMvcConfigurer {
    @Value("${file.storage-location}") // for file system
    private String storageLocation;

    @Value("${file.client-path}")
    private String clientPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(clientPath + "/**")
                /**
                 * File system configuration (uncomment if using external storage):
                 * .addResourceLocations("file:" + storageLocation);
                 */
                .addResourceLocations("file:" + storageLocation);

                /**
                 * Classpath configuration:
                 * No need to include "/resources" because Spring Boot automatically
                 * maps and looks inside the resources folder by default.
                 */
//                .addResourceLocations("classpath:/static/");

        /**
         * Example URL structure: /file/myimg.jpg
         * Note: The "/file" prefix is dynamically matched based on the
         * configuration property: @Value("${file.client-path}")
         */
    }
}
