package com.epam.taskeleven.client.presentation.form;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.struts.action.ActionForm;

public class NewsForm extends ActionForm
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2042849036398988864L;
	
	private static final Logger log = LoggerFactory.getLogger(ActionForm.class);
	
	private Locale locale;

	public NewsForm()
	{

	}
	/**
	 * @return the locale
	 */
	public Locale getLocale() 
	{
		return locale;
	}
	/**
	 * @param locale
	 * the locale to set
	 */
	public void setLocale(Locale locale) 
	{
		this.locale = locale;
		log.debug("Locale in {} set to {}", this.toString(), locale);
	}
}

