package com.comsysto.dcc.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class RoleNotFoundException extends RuntimeException {

	public RoleNotFoundException(String id) {
        super("could not find role '" + id + "'.");
	}
}