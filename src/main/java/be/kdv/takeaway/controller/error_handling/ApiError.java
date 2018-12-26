package be.kdv.takeaway.controller.error_handling;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
class ApiError {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Instant timestamp;
    private String message;
    private List<String> errors;

    private ApiError(){
        timestamp = Instant.now();
    }

    ApiError(HttpStatus status, String message){
        this();
        this.status = status;
        this.message = message;
        this.errors = new ArrayList<>();
    }

    ApiError(HttpStatus status, String message, List<String> errors){
        this();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    ApiError(HttpStatus status, String message, String error){
        this();
        this.status = status;
        this.message = message;
        this.errors = Arrays.asList(error);
    }
}

