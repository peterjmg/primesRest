package primes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import primes.exception.InvalidOptionException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(annotations = RestController.class)
@RequestMapping(value = "/primesCache")
public class PrimesCacheControllerAdvice {

    static final Logger logger = LoggerFactory.getLogger(PrimesCacheControllerAdvice.class);

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
