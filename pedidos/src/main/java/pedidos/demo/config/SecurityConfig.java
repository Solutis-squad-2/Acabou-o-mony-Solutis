package pedidos.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())// Desabilita CSRF para facilitar as requisições com token
                .authorizeRequests()
                .requestMatchers("/pedidos/swagger-ui/**", "/pedidos/v3/api-docs/**", "/pedidos/swagger-resources/**", "/pedidos/webjars/**")
                .permitAll()
                .requestMatchers("/pedidos/**").authenticated()  // Requer autenticação para /pedidos
                .anyRequest().permitAll()  // Permite todas as outras requisições sem autenticação
                .and()
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);  // Adiciona o filtro de autenticação JWT
        return http.build();
    }

    // Caso você precise configurar um AuthenticationManager (se estiver usando autenticação baseada em usuário e senha)
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

}
