package com.adv.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.adv.payloads.apiresponse.BasicApiResponse;

public class InvalidateLoginResponse {
 
	 
	public ResponseEntity<BasicApiResponse> getInvalidateResponse(BasicApiResponse bar) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bar);
	}
}
