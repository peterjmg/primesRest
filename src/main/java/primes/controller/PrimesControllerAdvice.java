package primes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import primes.exception.InvalidOptionException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(annotations = RestController.class)
@RequestMapping(value = "/primes")
public class PrimesControllerAdvice {

    static final Logger logger = LoggerFactory.getLogger(PrimesController.class);

    @ExceptionHandler(InvalidOptionException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid option specified")
    @ResponseBody
    public ModelAndView invalidOption(HttpServletRequest request, InvalidOptionException exception) {

        StringBuffer requestUrl = request.getRequestURL();
        String option = request.getParameter(PrimesController.OPTION_PARAMETER);
        logger.info("Exception Raised: " + exception);
        logger.info("Requested URL: " + requestUrl);
        logger.info("Requested option: " + option);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url", requestUrl);
        modelAndView.addObject(PrimesController.OPTION_PARAMETER, option);
        modelAndView.addObject("exception", exception);

        modelAndView.setViewName("Error");
        return modelAndView;
    }
}
/*
public class PrimesExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidOptionException.class)
    protected ResponseEntity<Object> handleInvalidOption(Exception exception, WebRequest request) {

        InvalidOptionException ire = (InvalidOptionException) exception;
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("Url", request.toString());
        errorResponse.put("Error", exception.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(exception, errorResponse, headers, HttpStatus.BAD_REQUEST, request);
    }
}
*/