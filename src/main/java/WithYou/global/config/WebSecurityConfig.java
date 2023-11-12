package WithYou.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://with-you-eight.vercel.app", "http://10.114.10.15:3000"
                ,"http://192.168.0.26:3000") // 허용할 도메인(Origin)을 명시적으로 지정
                .allowedHeaders("*")
                .exposedHeaders("accessToken", "refreshToken", "validation")
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name())
                .allowCredentials(true); // withCredentials 활성화
    }
}

