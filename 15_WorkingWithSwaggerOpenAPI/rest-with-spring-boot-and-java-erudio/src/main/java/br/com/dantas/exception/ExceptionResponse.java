package br.com.dantas.exception;

import java.util.Date;

public record ExceptionResponse(Date timeStamp, String message, String details) {}
