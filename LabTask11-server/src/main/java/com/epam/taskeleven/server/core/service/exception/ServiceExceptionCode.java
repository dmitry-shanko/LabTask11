package com.epam.taskeleven.server.core.service.exception;

import java.util.Locale;
import java.util.ResourceBundle;

public enum ServiceExceptionCode 
{
	UNIDENTIFIED("service_exception.000"),
	DATABASE_GET("service_exception.201"),
	DATABASE_GETALL("service_exception.202"),
	DATABASE_SAVE("service_exception.203"),
	DATABASE_UPDATE("service_exception.204"),
	DATABASE_DELETE("service_exception.205")
	;
	
	private static final String resources = "system.error-codes";
	
	private final String key;
	
	private ServiceExceptionCode(final String key) 
	{
		this.key = key;
	}
	
	@Override
	public String toString() 
	{
		ResourceBundle bundle = ResourceBundle.getBundle(resources, Locale.getDefault());
		String message = bundle.getString(key);
		return message;
	}
}
