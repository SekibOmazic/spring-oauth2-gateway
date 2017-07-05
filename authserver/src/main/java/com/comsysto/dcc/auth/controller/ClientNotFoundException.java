package com.comsysto.dcc.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class ClientNotFoundException extends RuntimeException {

	public ClientNotFoundException(String id) {
        super("could not find client '" + id + "'.");
	}
}