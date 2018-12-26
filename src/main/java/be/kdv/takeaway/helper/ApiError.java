package be.kdv.takeaway.helper;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
public class ApiError {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Instant timestamp;
    private String message;
    private List<String> errors;

    private ApiError(){
        timestamp = Instant.now();
    }

    public ApiError(HttpStatus status, String message){
        this();
        this.status = status;
        this.message = message;
        this.errors = new ArrayList<>();
    }

    public ApiError(HttpStatus status, String message, List<String> errors){
        this();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}

