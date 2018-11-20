package be.kdv.takeaway.controller.error_handling;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Data
public class ApiError {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Instant timestamp;
    private String message;
    private List<String> errors;

    public ApiError(HttpStatus status, String message, List<String> errors){
        super();
        this.status = status;
        this.timestamp = Instant.now();
        this.message = message;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String message, String error){
        super();
        this.status = status;
        this.timestamp = Instant.now();
        this.message = message;
        this.errors = Arrays.asList(error);
    }
}

