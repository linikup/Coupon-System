package phase3.core;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import phase3.core.filters.AdminFilter;
import phase3.core.filters.CompanyFilter;
import phase3.core.filters.CustomerFilter;
import phase3.core.sessions.SessionContext;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@EnableSwagger2
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}
	
	
	
	
	@Bean
	public FilterRegistrationBean<AdminFilter> filterRegistrationBean(SessionContext sessionContext){
		FilterRegistrationBean<AdminFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		AdminFilter loginFilter = new AdminFilter(sessionContext);
		filterRegistrationBean.setFilter(loginFilter);
		filterRegistrationBean.addUrlPatterns("/admin/*");
		return filterRegistrationBean;
	}
	
	@Bean
	public FilterRegistrationBean<CompanyFilter> CompanyfilterRegistrationBean(SessionContext sessionContext){
		FilterRegistrationBean<CompanyFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		CompanyFilter companyFilter = new CompanyFilter(sessionContext);
		filterRegistrationBean.setFilter(companyFilter);
		filterRegistrationBean.addUrlPatterns("/company/*");
		return filterRegistrationBean;
	}
	
	@Bean
	public FilterRegistrationBean<CustomerFilter> CustomerfilterRegistrationBean(SessionContext sessionContext){
		FilterRegistrationBean<CustomerFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		CustomerFilter customerFilter = new CustomerFilter(sessionContext);
		filterRegistrationBean.setFilter(customerFilter);
		filterRegistrationBean.addUrlPatterns("/customer/*");
		return filterRegistrationBean;
	}
	
	

}
