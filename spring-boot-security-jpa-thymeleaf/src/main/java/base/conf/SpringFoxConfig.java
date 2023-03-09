package base.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableWebMvc
public class SpringFoxConfig {                                    
    
	@Bean
    public Docket api() { 
		
		var apiInfo = new ApiInfoBuilder()
                .title("base-rest-api")
                .version("1.0")
                .license(null)
                .licenseUrl(null)
                .termsOfServiceUrl(null)
                .description("The base-rest-api to show the integration of swagger ui.")
                .build();
		
        return new Docket(DocumentationType.SWAGGER_2)
        		.apiInfo(apiInfo)
        		.useDefaultResponseMessages(false)
        		.select()
        		.apis(RequestHandlerSelectors.basePackage("base.web.rest"))
        		//.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)) // Alternative to RequestHandlerSelectors.basePackage(...)
        		.paths(PathSelectors.any())
        		.build();                                           
    }
}
