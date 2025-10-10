package br.com.springboot.rest_with_spring_boot_java.exception;

import java.util.Date;

public record ExceptionResponse(Date timestamp, String message, String details) {}
