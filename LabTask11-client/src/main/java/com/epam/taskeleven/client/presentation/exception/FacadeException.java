package com.epam.taskeleven.client.presentation.exception;

public class FacadeException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8618345175979862371L;
	
	private final FacadeExceptionCode exceptionCode;
	private String message;
	
	public FacadeException()
	{
		super();
		exceptionCode = FacadeExceptionCode.UNIDENTIFIED;
		this.init();
	}

	public FacadeException(FacadeExceptionCode code, Object...args)
	{
		super((code != null ? code.toString() : FacadeExceptionCode.UNIDENTIFIED.toString()));
		this.exceptionCode = code;
		this.init(args);
	}

	public FacadeException(Throwable e)
	{
		super(e);
		exceptionCode = FacadeExceptionCode.UNIDENTIFIED;
		this.init();
	}

	public FacadeException(FacadeExceptionCode code, Throwable e, Object...args)
	{
		super((code != null ? code.toString() : FacadeExceptionCode.UNIDENTIFIED.toString()), e);
		this.exceptionCode = code;
		this.init(args);
	}
	
	private void init(Object...args)
	{
		message = String.format(exceptionCode.toString(), args);
	}
	
	public FacadeExceptionCode  getExceptionCode()
	{
		return this.exceptionCode;
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