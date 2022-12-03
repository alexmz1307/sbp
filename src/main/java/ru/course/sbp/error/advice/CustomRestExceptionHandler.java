package ru.course.sbp.error.advice;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.course.sbp.error.ApiError;
import ru.course.sbp.error.ErrorCode;
import ru.course.sbp.error.ErrorResponseException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * A global error handler implementation for a Spring REST API.
 */
@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    private void setPath(WebRequest request, ApiError apiError) {
        if (request instanceof ServletWebRequest) {
            apiError.setPath(((ServletWebRequest) request).getNativeRequest(HttpServletRequest.class).getRequestURI());
        } else {
            apiError.setPath(request.getDescription(false));
        }
    }

    private String getPath(WebRequest request) {
        if (request instanceof ServletWebRequest) {
            return ((ServletWebRequest) request).getNativeRequest(HttpServletRequest.class).getRequestURI();
        } else {
            return (request.getDescription(false));
        }
    }

    /**
     * Обработчик ошибок ErrorResponseException.
     *
     * @param ex Объект ErrorResponseException
     * @return сформированный объект с информацией об ошибке
     */
    @ExceptionHandler(ErrorResponseException.class)
    public ResponseEntity<Object> errorResponseExceptionHandler(ErrorResponseException ex, WebRequest request) {

        ApiError apiError = new ApiError()
                .setError(ex.getHttpStatus())
                .setStatus(ex.getHttpStatus().value())
                .setErrorCode(ex.getErrorCode())
                .setMessage(ObjectUtils.firstNonNull(ex.getLocalizedMessage(), ex.getMessage()))
                .setTimestamp(new Date());

        setPath(request, apiError);

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getError());

    }

    /**
     * Обработчик ошибок HttpServerErrorException.
     * @param ex Объект HttpServerErrorException
     * @return сформированный объект с информацией об ошибке
     */
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<String> errorResponseExceptionHandler(HttpServerErrorException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> errorResponseExceptionHandler(HttpClientErrorException ex) {
        return new ResponseEntity<>(ex.getResponseBodyAsString(), ex.getStatusCode());
    }

    /**
     * Обработка исключений IllegalArgumentException.
     *
     * @param request the current request
     * @param ex      the exception.
     * @return a {@code ResponseEntity} instance
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ApiError apiError = new ApiError()
                .setError(HttpStatus.BAD_REQUEST)
                .setMessage(ObjectUtils.firstNonNull(ex.getLocalizedMessage(), ex.getMessage()))
                .setPath(request.getDescription(false))
                .setStatus(HttpStatus.BAD_REQUEST.value())
                .setTimestamp(new Date());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getError());
    }

    /**
     * Обрабатывает любые исключения.
     *
     * @param ex      the exception
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError()
                .setError(HttpStatus.INTERNAL_SERVER_ERROR)
                .setMessage(ObjectUtils.firstNonNull(ex.getLocalizedMessage(), ex.getMessage()))
                .setPath(request.getDescription(false))
                .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setTimestamp(new Date());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getError());
    }

    /**
     * Customize the response for HttpMediaTypeNotSupportedException.
     * <p>This method sets the "Accept" header and delegates to
     * {@link #handleExceptionInternal}.
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                      HttpHeaders headers,
                                                                      HttpStatus status,
                                                                      WebRequest request) {
        StringBuilder builder = new StringBuilder(String.format("Media type %s не поддерживается", ex.getContentType()));

        if (!ex.getSupportedMediaTypes().isEmpty()) {
            builder
                    .append(". Поддерживаемые media type: ")
                    .append(StringUtils.join(ex.getSupportedMediaTypes(), ", "))
                    .append(".");
        }

        ApiError apiError = new ApiError()
                .setError(status)
                .setMessage(builder.toString())
                .setPath(request.getDescription(false))
                .setStatus(status.value())
                .setTimestamp(new Date());

        return new ResponseEntity<>(apiError, headers, status);
    }

    /**
     * Customize the response for HttpMessageNotReadableException.
     * <p>This method delegates to {@link #handleExceptionInternal}.
     * <p>Обрабатывает ошибки, когда не указано или невалидное тело запроса.
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override

    protected ResponseEntity<Object> handleHttpMessageNotReadable( HttpMessageNotReadableException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                  WebRequest request) {
        ApiError apiError = new ApiError()
                .setError(ErrorCode.BAD_ARGUMENT.getHttpStatus())
                .setErrorCode(ErrorCode.BAD_ARGUMENT)
                .setMessage("Отсутствует или не удалось обработать обязательное тело запроса")
                .setPath(request.getDescription(false))
                .setStatus(ErrorCode.BAD_ARGUMENT.getHttpStatus().value())
                .setTimestamp(new Date());

        return new ResponseEntity<>(apiError, headers, status);
    }

    /**
     * Customize the response for HttpRequestMethodNotSupportedException.
     * <p>This method logs a warning, sets the "Allow" header, and delegates to
     * {@link #handleExceptionInternal}.
     * <p>Обрабатывает ошибки при HTTP status code 405 (метод не поддерживается).
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override

    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        StringBuilder builder = new StringBuilder(String.format("Метод %s не поддерживается", ex.getMethod()));

        if (null != ex.getSupportedMethods()) {
            builder
                    .append(". Поддерживаемые методы: ")
                    .append(Arrays.toString(ex.getSupportedMethods()))
                    .append(".");
        }

        ApiError apiError = new ApiError()
                .setError(status)
                .setMessage(builder.toString())
                .setPath(request.getDescription(false))
                .setStatus(status.value())
                .setTimestamp(new Date());

        return new ResponseEntity<>(apiError, headers, status);
    }

    /**
     * Customize the response for MethodArgumentNotValidException.
     * <p>This method delegates to {@link #handleExceptionInternal}.
     * <p>Обрабатывает ошибки, когда поля тела запроса не указаны или сработали custom constraints.
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        List errors = new ArrayList<ApiError.FieldError>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(new ApiError.FieldError()
                    .setField(error.getField())
                    .setDefaultMessage(error.getDefaultMessage()));
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(new ApiError.FieldError()
                    .setObjectName(error.getObjectName())
                    .setDefaultMessage(error.getDefaultMessage()));
        }
        ApiError apiError = new ApiError()
                .setError(ErrorCode.BAD_ARGUMENT.getHttpStatus())
                .setErrorCode(ErrorCode.BAD_ARGUMENT)
                .setErrors(errors)
                .setMessage(ErrorCode.BAD_ARGUMENT.getMessage())
                .setPath(request.getDescription(false))
                .setStatus(ErrorCode.BAD_ARGUMENT.getHttpStatus().value())
                .setTimestamp(new Date());
        return handleExceptionInternal(ex, apiError, headers, apiError.getError(), request);
    }

}
