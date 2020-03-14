package ru.semi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс логирование входящих Http запросов
 * Должен быть инициализирован как @Bean
 *
 * @Autowire этого класса запрещён!!!
 */
public class SpringRequestFilter extends AbstractRequestLoggingFilter {


    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        logger.info(message);
    }

    private void sendRequestToKibana(HttpServletRequest request, String message) {
        // split message to String array and cleanup
        List<String> requestParams = new LinkedList<>();
        requestParams.add(request.getMethod());
        for (String part : message.split(";")) {
            requestParams.add(cleanUp(part));
        }

    }

    private String cleanUp(String part) {
        String result;
        if (part.contains("=")) {
            result = part.substring(part.indexOf("=") + 1);
        } else {
            return part;
        }
        return result;
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        logger.info(message);
        sendRequestToKibana(request, message);
    }
}
