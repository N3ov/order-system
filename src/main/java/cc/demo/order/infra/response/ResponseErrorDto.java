package cc.demo.order.infra.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseErrorDto {

    private final Map<String, String> errors = new HashMap<>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Setter
    private String message;

    public void addError(String key, String value) {
        errors.put(key, value);
    }

    public ResponseEntity<ResponseErrorDto> toResponseEntity(HttpStatus status) {
        return new ResponseEntity<>(this, status);
    }

}
