package com.mitocode.exceptions;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Order(
    -1) // Indica que este clase carga o inicia en primer orden, con esto Spring no gestionara el
        // error, sera manejado por esta clase
@Component
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

  public GlobalErrorWebExceptionHandler(
      ErrorAttributes errorAttributes,
      ResourceProperties resources, // Tiene una dependencia spring-boot-autoconfigure
      ApplicationContext applicationContext,
      ServerCodecConfigurer serverCodecConfigurer) {
    super(errorAttributes, resources, applicationContext);
    this.setMessageWriters(serverCodecConfigurer.getWriters());
  }

  @Override
  protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
    return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
  }

  private Mono<ServerResponse> renderErrorResponse(ServerRequest serverRequest) {
    Map<String, Object> errorGeneral = getErrorAttributes(serverRequest, false);
    Map<String, Object> mapException = new HashMap<>();

    var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    String statusCode = String.valueOf(errorGeneral.get("status"));

    switch (statusCode) {
      case "500":
        mapException.put("error", "500");
        mapException.put("exception", "Error general del backend");
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        break;
      case "400":
        mapException.put("error", "400");
        mapException.put("exception", "Peticion incorrecta");
        httpStatus = HttpStatus.BAD_REQUEST;
        break;
      default:
        mapException.put("error", "900");
        mapException.put("exception", errorGeneral.get("error"));
        httpStatus = HttpStatus.CONFLICT;
        break;
    }
    return ServerResponse.status(httpStatus)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(mapException));
  }
}
