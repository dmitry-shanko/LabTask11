package com.epam.taskeleven.server.core.service.exception;

public class ServiceException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2990516951153061914L;
	
	private final ServiceExceptionCode exceptionCode;
	private String message;

	public ServiceException()
	{
		super();
		this.exceptionCode = ServiceExceptionCode.UNIDENTIFIED;
		this.init();
	}

	public ServiceException(ServiceExceptionCode exceptionCode, Object...args)
	{
		super((exceptionCode != null ? exceptionCode.toString() : ServiceExceptionCode.UNIDENTIFIED.toString()));
		this.exceptionCode = exceptionCode;
		this.init(args);
	}

	public ServiceException(Throwable e)
	{
		super(e);
		this.exceptionCode = ServiceExceptionCode.UNIDENTIFIED;
		this.init();
	}

	public ServiceException(ServiceExceptionCode exceptionCode, Throwable e, Object...args)
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
