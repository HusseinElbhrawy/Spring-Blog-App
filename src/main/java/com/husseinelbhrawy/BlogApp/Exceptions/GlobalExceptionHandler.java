package com.husseinelbhrawy.BlogApp.Exceptions;

import com.husseinelbhrawy.BlogApp.Payload.ErrorDetails;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler  {
//    extends ResponseEntityExceptionHandler


    //! Handle Specific Exceptions like `ResourceNotFoundException` , `BlogAPIException`
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception , WebRequest webRequest){

        ErrorDetails errorDetails = new ErrorDetails();

        errorDetails.setMessage(exception.getMessage());
        errorDetails.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorDetails.setTimeStamp(new Date());
        errorDetails.setDetails(webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorDetails> handleBlogAPIExceptionException(BlogAPIException exception , WebRequest webRequest){

        ErrorDetails errorDetails = new ErrorDetails();

        errorDetails.setMessage(exception.getMessage());
        errorDetails.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDetails.setTimeStamp(new Date());
        errorDetails.setDetails(webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){

        Map<String , String> errors = new HashMap<>();

        for (ObjectError allError : exception.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) allError).getField();
            String message =allError.getDefaultMessage() ;

            errors.put(fieldName , message);
        }
        return  new ResponseEntity<>(errors , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDetails> handleDataIntegrityViolationException(DataIntegrityViolationException exception , WebRequest webRequest){


        ErrorDetails errorDetails =  ErrorDetails.builder()
                .message(exception.getMostSpecificCause().getLocalizedMessage())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timeStamp(new Date())
                .details(webRequest.getDescription(false))
                .build();


        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Object> handleAuthorizationDeniedException(AuthorizationDeniedException exception , WebRequest webRequest){


        ErrorDetails errorDetails = new ErrorDetails();

//        errorDetails.setMessage(exception.getMessage());
        errorDetails.setMessage("You Don't have permission to access this resource");
        errorDetails.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        errorDetails.setTimeStamp(new Date());
        errorDetails.setDetails(webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }



    //! Handle Global Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception , WebRequest webRequest){

        ErrorDetails errorDetails = new ErrorDetails();

        errorDetails.setMessage(exception.getMessage());
        errorDetails.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDetails.setTimeStamp(new Date());
        errorDetails.setDetails(webRequest. getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        Map<String , String> errors = new HashMap<>();
//
//        for (ObjectError allError : ex.getBindingResult().getAllErrors()) {
//            System.out.println("All Errors " + Arrays.toString(allError.getArguments()));
//            String fieldName = ((FieldError) allError).getField();
//            String message = allError.getDefaultMessage();
//
//            errors.put(fieldName , message);
//        }
//        return  new ResponseEntity<>(errors , HttpStatus.BAD_REQUEST);
//    }


}
