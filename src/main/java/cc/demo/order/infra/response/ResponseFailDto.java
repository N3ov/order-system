package cc.demo.order.infra.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseFailDto implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer errorCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    public ResponseEntity<ResponseFailDto> toResponseEntity(HttpStatus status) {
        return new ResponseEntity<>(this, status);
    }

}
