package be.vdab.personeel.web.config;

import be.vdab.personeel.converters.IdToEmployeeConverter;
import be.vdab.personeel.converters.StringToSocialSecurityNumberConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final IdToEmployeeConverter idToEmployeeConverter;

    @Autowired
    public WebConfig(IdToEmployeeConverter idToEmployeeConverter) {
        this.idToEmployeeConverter = idToEmployeeConverter;
    }

    @Bean
    CookieLocaleResolver localeResolver() {
        final CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setCookieMaxAge(604_800);

        return resolver;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleChangeInterceptor());
    }

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverter(new StringToSocialSecurityNumberConverter());
        registry.addConverter(idToEmployeeConverter);
    }
}
