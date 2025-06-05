package com.explorer.stranger_things.config;

        import com.explorer.stranger_things.interceptor.FeignErrorInterceptor;
        import feign.Logger;
        import feign.Request;
        import feign.codec.ErrorDecoder;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;

        import java.util.concurrent.TimeUnit;

        /**
         * Configuração do Feign Client para a aplicação Stranger Things.
         * Define comportamentos globais para todos os clientes Feign na aplicação.
         */
        @Configuration
        public class FeignClientConfig {

            /**
             * Configura o interceptor de erros personalizado para o Feign Client
             *
             * @return ErrorDecoder personalizado para tratamento de erros
             */
            @Bean
            public ErrorDecoder errorDecoder() {
                return new FeignErrorInterceptor();
            }

            /**
             * Configura o nível de log para o Feign Client
             *
             * @return Nível de log FULL para capturar detalhes completos das requisições
             */
            @Bean
            public Logger.Level feignLoggerLevel() {
                return Logger.Level.FULL;
            }

            /**
             * Configura os timeouts padrão para as requisições do Feign Client
             *
             * @return Configuração de timeout para conexão e leitura
             */
            @Bean
            public Request.Options requestOptions() {
                return new Request.Options(
                        10, TimeUnit.SECONDS,  // Connection Timeout
                        15, TimeUnit.SECONDS,  // Read Timeout
                        true                  // Follow Redirects
                );
            }
        }