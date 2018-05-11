package com.neueda.kgs.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

/**
 * a utility to facilitate the returned responsed from the controller
 * @author MohammadReza Alagheband
 */
public class ResponseUtil {
    private ResponseUtil() {
    }

    /**
     * if optional got anything other than null it will be included in the response
     * otherwise a response with a not found httpstatus will be returned
     * @param <X>           type of the response
     * @param maybeResponse response to return if present
     * @return response containing  maybeResponse if present or NOT_FOUND
     */
    public static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse) {
        return maybeResponse.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
