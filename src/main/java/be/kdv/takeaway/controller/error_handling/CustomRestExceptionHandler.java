package be.kdv.takeaway.controller.error_handling;

import be.kdv.takeaway.exception.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    // general error handler
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequests(BadRequestException ex){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), "Error occured");
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }
}
