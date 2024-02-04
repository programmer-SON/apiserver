package org.zerock.apiserver.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zerock.apiserver.controller.formatter.LocalDateFormatter;

@Configuration
@Log4j2
public class CustomServletConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {

        log.info("----------------------------------");
        log.info("addFormatters");

        registry.addFormatter(new LocalDateFormatter());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

                // 모든 경로 적용
        registry.addMapping("/**")
                // response 대기시간. 만약 설정 시간이 넘도록 응답이 없을경우 서버에서 문제가 발생했을수도 있음
                .maxAge(500)
                /*
                 *  OPTIONS : Ajax로 호출시 미리 검증할때 쓰는 메소드
                 *  pre-flight : Ajax로 Json 데이터 호출할때 검증하는 방법
                 */
                .allowedMethods("GET","POST","PUT","DELETE", "HEAD", "OPTIONS")
                // 어디에서 부터 들어오는 연결을 허락해주는것
                .allowedOrigins("*");
    }
}
