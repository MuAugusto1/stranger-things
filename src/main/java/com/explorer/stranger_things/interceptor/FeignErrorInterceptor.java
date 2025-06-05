package com.explorer.stranger_things.interceptor;

        import feign.Response;
        import feign.codec.ErrorDecoder;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.http.HttpStatus;
        import org.springframework.web.server.ResponseStatusException;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.nio.charset.StandardCharsets;
        import java.util.stream.Collectors;

        /**
         * Interceptor para tratar erros de comunicação com a API externa do Stranger Things.
         * Implementa ErrorDecoder do Feign para personalizar o tratamento de erros HTTP.
         */
        public class FeignErrorInterceptor implements ErrorDecoder {

            private static final Logger logger = LoggerFactory.getLogger(FeignErrorInterceptor.class);
            private final ErrorDecoder defaultErrorDecoder = new Default();

            /**
             * Decodifica a resposta de erro do Feign Client e converte para exceções apropriadas
             *
             * @param methodKey chave do método que gerou o erro
             * @param response  resposta contendo o erro
             * @return Exception apropriada para o erro encontrado
             */
            @Override
            public Exception decode(String methodKey, Response response) {
                String requestUrl = response.request().url();
                int status = response.status();

                // Log detalhado do erro
                logger.error("Erro na chamada à API externa: {} - Status: {}", requestUrl, status);

                // Extrai o corpo da resposta para logging e análise
                String responseBody = extractResponseBody(response);
                logger.debug("Corpo da resposta de erro: {}", responseBody);

                // Tratamento específico baseado no código de status HTTP
                switch (status) {
                    case 400:
                        return new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "A API externa reportou um erro de requisição inválida: " + responseBody);
                    case 404:
                        return new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Recurso não encontrado na API externa: " + responseBody);
                    case 429:
                        return new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,
                                "Limite de requisições excedido na API externa. Tente novamente mais tarde.");
                    case 500:
                    case 502:
                    case 503:
                    case 504:
                        return new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                                "A API externa está temporariamente indisponível. Tente novamente mais tarde.");
                    default:
                        // Para outros erros, usa o decodificador padrão
                        logger.warn("Erro não mapeado na API externa: {} - Status: {}", requestUrl, status);
                        return defaultErrorDecoder.decode(methodKey, response);
                }
            }

            /**
             * Extrai o corpo da resposta de erro
             *
             * @param response resposta contendo o erro
             * @return String contendo o corpo da resposta ou mensagem de erro
             */
            private String extractResponseBody(Response response) {
                if (response.body() == null) {
                    return "Sem corpo na resposta";
                }

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(response.body().asInputStream(), StandardCharsets.UTF_8))) {
                    return reader.lines().collect(Collectors.joining("\n"));
                } catch (IOException e) {
                    logger.error("Erro ao ler corpo da resposta", e);
                    return "Erro ao ler corpo da resposta: " + e.getMessage();
                }
            }
        }