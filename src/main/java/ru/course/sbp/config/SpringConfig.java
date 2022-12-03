package ru.course.sbp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class SpringConfig implements WebMvcConfigurer {

    /**
     * Configure the {@link HttpMessageConverter HttpMessageConverters} to use for reading or writing
     * to the body of the request or response. If no converters are added, a
     * default list of converters is registered.
     * <p><strong>Note</strong> that adding converters to the list, turns off
     * default converter registration. To simply add a converter without impacting
     * default registration, consider using the method
     * {@link #extendMessageConverters(java.util.List)} instead.
     * <p>Переопределён для решения проблемы с кодировкой в ответах тестов и консоли.</p>
     *
     * @param converters initially an empty list of converters
     * @see <a href="https://stackoverflow.com/questions/58525387/mockmvc-no-longer-handles-utf-8-characters-with-spring-boot-2-2-0-release">stackoverflow.com</a>
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters
                .stream()
                .filter(MappingJackson2HttpMessageConverter.class::isInstance)
                .findFirst()
                .ifPresent(httpMessageConverter -> ((MappingJackson2HttpMessageConverter) httpMessageConverter)
                        .setDefaultCharset(StandardCharsets.UTF_8));
    }

}
