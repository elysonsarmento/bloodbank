package com.bloodbank.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(HandlerMethodValidationException ex) {
        List<Map<String, String>> errors = ex.getAllErrors().stream()
                .map(error -> {
                    Map<String, String> errorDetails = new HashMap<>();
                    String fieldPath = error instanceof org.springframework.validation.FieldError ?
                            ((org.springframework.validation.FieldError) error).getField() : "desconhecido";

                    errorDetails.put("campo", extrairCampo(fieldPath));
                    errorDetails.put("erro", error.getDefaultMessage());

                    if (error instanceof org.springframework.validation.FieldError) {
                        Object rejectedValue = ((org.springframework.validation.FieldError) error).getRejectedValue();
                        errorDetails.put("valorRejeitado", Objects.toString(rejectedValue, "null"));
                    }

                    return errorDetails;
                }).collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse("400", "Erro de validação", errors);
        return ResponseEntity.badRequest().body(errorResponse);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String mensagemErro = "Erro de integridade de dados: ";

        // Verifica se a exceção é causada por uma violação de unicidade
        if (ex.getMessage().contains("uc_donor_rg")) {
            mensagemErro += "Já existe um doador cadastrado com este RG.";
        } else {
            mensagemErro += ex.getMostSpecificCause().getMessage();
        }

        ErrorResponse errorResponse = new ErrorResponse("409", mensagemErro);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(mensagemErro);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<Map<String, String>>> handleConstraintViolationException(ConstraintViolationException ex) {
        List<Map<String, String>> errors = new ArrayList<>();

        ex.getConstraintViolations().forEach(violation -> {
            Map<String, String> errorDetails = new HashMap<>();
            String propertyPath = violation.getPropertyPath().toString();
            errorDetails.put("item", extrairIndice(propertyPath)); // Obtém índice do item
            errorDetails.put("campo", extrairCampo(propertyPath)); // Extrai o campo correto
            errorDetails.put("erro", violation.getMessage());
            errors.add(errorDetails);
        });

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Erro de formato JSON: " + ex.getMostSpecificCause().getMessage());
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno: " + ex.getMessage());
    }

    private String extrairIndice(String fieldPath) {
        Pattern pattern = Pattern.compile("\\[(\\d+)\\]"); // Procura por [número]
        Matcher matcher = pattern.matcher(fieldPath);
        return matcher.find() ? matcher.group(1) : "desconhecido";
    }

    private String extrairCampo(String fieldPath) {
        String[] partes = fieldPath.split("\\.");
        return partes[partes.length - 1];
    }
}
