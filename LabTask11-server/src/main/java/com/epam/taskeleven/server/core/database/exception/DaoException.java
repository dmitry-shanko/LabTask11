package com.epam.taskeleven.server.core.database.exception;

import com.epam.taskeleven.server.core.service.exception.ServiceExceptionCode;

public class DaoException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4362825538703123185L;
	
	private final DaoExceptionCode exceptionCode;
	private String message;
	
	public DaoException()
	{
		super();
		this.exceptionCode = DaoExceptionCode.UNIDENTIFIED;
		this.init();
	}

	public DaoException(DaoExceptionCode exceptionCode, Object...args)
	{
		super((exceptionCode != null ? exceptionCode.toString() : ServiceExceptionCode.UNIDENTIFIED.toString()));
		this.exceptionCode = exceptionCode;
		this.init(args);
	}

	public DaoException(Throwable e)
	{
		super(e);
		this.exceptionCode = DaoExceptionCode.UNIDENTIFIED;
		this.init();
	}

	public DaoException(DaoExceptionCode exceptionCode, Throwable e, Object...args)
	{
		super((exceptionCode != null ? exceptionCode.toString() : ServiceExceptionCode.UNIDENTIFIED.toString()), e);
		this.exceptionCode = exceptionCode;
		this.init(args);
	}

	private void init(Object...args)
	{
		message = String.format(exceptionCode.toString(), args);
	}
	
	@Override
	public String getLocalizedMessage() 
	{
		return message;
	}
	
	@Override
	public String toString() 
	{
		return message.concat(".\n ").concat(super.toString());
	}
}