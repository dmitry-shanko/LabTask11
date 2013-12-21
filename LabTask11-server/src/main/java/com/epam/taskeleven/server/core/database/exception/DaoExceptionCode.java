package com.epam.taskeleven.server.core.database.exception;

import java.util.Locale;
import java.util.ResourceBundle;

public enum DaoExceptionCode 
{
	UNIDENTIFIED("dao_exception.000"),
	INITIALIZATION_EXCEPTION("dao_exception.010"),
	SESSION_EXCEPTION("dao_exception.011"),
	SESSION_OPEN_EXCEPTION("dao_exception.012"),
	READ_EXCEPTION("dao_exception.100"),
	UPDATE_EXCEPTION("dao_exception.101"),
	DELETE_EXCEPTION("dao_exception.102"),
	CRITERIA_FIND_EXCEPTION("dao_exception.201")
	;
	
	private static final String resources = "system.error-codes";
	
	private final String key;
	
	private DaoExceptionCode(final String key) 
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
