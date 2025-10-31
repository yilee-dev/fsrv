package yilee.fsrv.global;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException exception, Locale locale) {
        var errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> {
                            Map<String, Object> m = new LinkedHashMap<>();
                            m.put("field", fieldError.getField());
                            m.put("code", fieldError.getCode());
                            m.put("rejectedValue", fieldError.getRejectedValue());
                            m.put("status", 400);
                            m.put("errors", resolve(fieldError, locale));
                            return m;
                }).toList();

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", "VALIDATION_FAILED");
        body.put("errors", errors);

        return ResponseEntity.badRequest().body(body);
    }

    public String resolve(FieldError fieldError, Locale locale) {
        Locale validatedLocale = locale != null ? locale : Locale.KOREA;
        String defaultMessage = fieldError.getDefaultMessage();

        if (defaultMessage.startsWith("{") && defaultMessage.endsWith("}")) {
            try {
                String key = defaultMessage.substring(1, defaultMessage.length() - 1);
                return messageSource.getMessage(key, fieldError.getArguments(), validatedLocale);
            } catch (Exception ignore) {}
        }

        return defaultMessage;
    }
}
