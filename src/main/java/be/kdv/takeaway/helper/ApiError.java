package be.kdv.takeaway.helper;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ApiError {

    private HttpStatus status;
    private Instant timestamp;
    private String message;
    private String details;
    private List<String> errors;

    public ApiError(String message, String details){
        super();
        this.timestamp = Instant.now();
        this.message = message;
        this.details = details;
        this.errors = new ArrayList<>();
    }

    public ApiError(HttpStatus status, String message){
        super();
        this.timestamp = Instant.now();
        this.status = status;
        this.message = message;
        this.errors = new ArrayList<>();
    }

   public ApiError(HttpStatus status, String message, String details){
        super();
        this.timestamp = Instant.now();
        this.status = status;
        this.message = message;
        this.details = details;
        this.errors = new ArrayList<>();
    }

    public ApiError(HttpStatus status, String message, List<String> errors){
        super();
        this.timestamp = Instant.now();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}

