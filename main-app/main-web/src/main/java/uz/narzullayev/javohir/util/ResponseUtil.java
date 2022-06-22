package uz.narzullayev.javohir.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uz.narzullayev.javohir.constant.AppConstant;
import uz.narzullayev.javohir.dto.base.BaseResponse;

import java.time.LocalDateTime;

public class ResponseUtil {

    private ResponseUtil() {
    }

    public static <T> ResponseEntity<Object> build(AppConstant.ResponseCode responseCode, T data, HttpStatus httpStatus) {
        return new ResponseEntity<>(build(responseCode, data), httpStatus);
    }

    private static <T> BaseResponse<T> build(AppConstant.ResponseCode responseCode, T data) {
        return BaseResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .responseCode(responseCode.name())
                .message(responseCode.getMessage())
                .data(data)
                .build();
    }

}
