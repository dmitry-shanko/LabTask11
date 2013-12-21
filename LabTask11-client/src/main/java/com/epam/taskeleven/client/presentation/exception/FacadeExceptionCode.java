package com.epam.taskeleven.client.presentation.exception;

import java.util.Locale;
import java.util.ResourceBundle;

public enum FacadeExceptionCode 
{
	UNIDENTIFIED("facade_exception.000"),
	NO_RESPONSE("facade_exception.100"),
	UNKNOWN_RESPONSE("facade_exception.101"),
	UNKNOWN_REQUEST("facade_exception.102");
	
	private static final String resources = "system.error-codes";
	
	private final String key;
	
	private FacadeExceptionCode(final String key) 
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
