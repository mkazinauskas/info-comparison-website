package modzo.compare.query.core.api

import org.springframework.http.HttpStatus

class ApiException extends RuntimeException {
    final String id
    final String message
    final HttpStatus httpStatus

    ApiException(String id, String message, HttpStatus httpStatus) {
        super(message)
        this.id = id
        this.message = message
        this.httpStatus = httpStatus
    }
}
