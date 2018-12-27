package be.kdv.takeaway.exception.handler;

import be.kdv.takeaway.exception.BadRequestException;
import be.kdv.takeaway.exception.EntityNotFoundException;
import be.kdv.takeaway.exception.InputNotValidException;
import be.kdv.takeaway.helper.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RepositoryRestController
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    // general error handler
  @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> handleBadRequests(BadRequestException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), Collections.singletonList("Error occured"));
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> notFoundException(final EntityNotFoundException ex, WebRequest request) {
        ApiError apiError = new ApiError(ex.getLocalizedMessage(), request.getDescription(false));
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InputNotValidException.class})
    public ResponseEntity<Object> inputNotValidException(final InputNotValidException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request
    ) {
        List<String> errors = new ArrayList<>();
        if (ex.getBindingResult() != null) {
            ex.getBindingResult().getFieldErrors().forEach(fieldError -> errors.add(buildErrorMessage(fieldError.getField(), fieldError.getDefaultMessage())));
            ex.getBindingResult().getGlobalErrors().forEach(globalError -> errors.add(buildErrorMessage(globalError.getObjectName(), globalError.getDefaultMessage())));

        }

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);

        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    private String buildErrorMessage(String name, String message) {
        return name + ": " + message;
    }

    /**
     * Thrown when the input method argument is of wrong/unexpected type.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex) {
        String error = ex.getName() + " should be of type " + Objects.requireNonNull(ex.getRequiredType()).getName();

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), Collections.singletonList(error));

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * Replaces a standard 404 response
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), Collections.singletonList(error));
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
