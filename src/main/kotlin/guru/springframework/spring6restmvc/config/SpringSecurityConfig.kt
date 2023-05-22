package guru.springframework.spring6restmvc.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SpringSecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests()
            .anyRequest().authenticated()
            .and().httpBasic(Customizer.withDefaults())
            .csrf().ignoringRequestMatchers("/api/**")
        return http.build()
    }
}