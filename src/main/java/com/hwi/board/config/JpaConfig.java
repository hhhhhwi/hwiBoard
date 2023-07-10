package com.hwi.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {
    /**
     * JPA 자동완성 기능 ~By에 대한 설정
     * TODO Spring Security 인증 기능 구현 후 수정 예정
     * @return
     */
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("hwi");
    }

}
