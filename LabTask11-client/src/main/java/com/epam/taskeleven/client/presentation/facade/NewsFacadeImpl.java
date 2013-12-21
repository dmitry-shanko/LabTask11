package com.epam.taskeleven.client.presentation.facade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.epam.taskeleven.client.model.News;
import com.epam.taskeleven.client.presentation.exception.FacadeException;
import com.epam.taskeleven.client.presentation.exception.FacadeExceptionCode;
import com.epam.taskeleven.server.soap.NewsType;
import com.epam.taskeleven.server.soap.ServiceRequest;
import com.epam.taskeleven.server.soap.ServiceResponse;

public class NewsFacadeImpl implements NewsFacade
{
	private static final Logger log = LoggerFactory.getLogger(NewsFacadeImpl.class);
	private static final String FINDALL = "FINDALL";
	private static final String FINDBYID = "FINDBYID";
	private static final String UPDATE = "UPDATE";
	private static final String DELETE = "DELETE";
	private WebServiceTemplate webServiceTemplate;
	private DatatypeFactory xmlDatatypeFactory;

	@SuppressWarnings("unused")
	private void initLogging()
	{
		try 
		{
			xmlDatatypeFactory = DatatypeFactory.newInstance();
		} 
		catch (DatatypeConfigurationException e) 
		{
			log.error("Can't initialize DatatypeFactory", e);
		}
		log.debug("Initialized params for working with web service: uri={}", webServiceTemplate.getDefaultUri());
	}

	public WebServiceTemplate getWebServiceTemplate() 
	{
		return webServiceTemplate;
	}

	public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) 
	{
		this.webServiceTemplate = webServiceTemplate;
	}

	@Override
	public List<News> getNewsList() throws FacadeException 
	{
		ServiceRequest request = new ServiceRequest();
		log.debug("Attempt to get newsList using {} action in SOAP request. Request has been created.", FINDALL);
		request.setAction(FINDALL);
		ServiceResponse response = (ServiceResponse) webServiceTemplate.marshalSendAndReceive(request);
		if (null == response)
		{
			throw new FacadeException(FacadeExceptionCode.NO_RESPONSE);
		}
		log.debug("Received response for action={}. NewsTypes in response:={}, response={}", new Object[]{FINDALL, (null != response ? response.getNewsType(): null), response});
		List<NewsType> newstypes = response.getNewsType();
		List<News> newsList = new ArrayList<News>();
		for (NewsType newsType : newstypes)
		{
			News news = new News();
			news.setId(newsType.getNewsId());
			news.setTitle(newsType.getTitle());
			news.setBrief(newsType.getBrief());
			news.setContent(newsType.getContent());
			news.setNewsDate(newsType.getNewsDate().toGregorianCalendar());
			newsList.add(news);
		}
		log.warn("Got after\"FINDALL\" result={}", newsList);
		if (null != newsList)
		{
			Collections.sort(newsList);
		}
		return newsList;
	}

	@Override
	public News fetchById(Integer id) throws FacadeException 
	{
		ServiceRequest request = new ServiceRequest();
		log.debug("Attempt to get newsById using {} action in SOAP request. Request has been created.", FINDBYID);
		if ((null == id) || (id < 1))
		{
			log.debug("Incorrect ID={}. Id must be > 0", id);
			throw new FacadeException(FacadeExceptionCode.UNKNOWN_REQUEST, "id");
		}
		log.debug("Params for request: newsId={}", id);
		request.setAction(FINDBYID);
		request.getId().add(id);
		ServiceResponse response = (ServiceResponse) webServiceTemplate.marshalSendAndReceive(request);
		if (null == response)
		{
			throw new FacadeException(FacadeExceptionCode.NO_RESPONSE);
		}
		log.debug("Received response for action={}. NewsTypes in response:={}, response={}", new Object[]{FINDBYID, (null != response ? response.getNewsType(): null), response});
		List<NewsType> newstypes = response.getNewsType();
		List<News> newsList = new ArrayList<News>(1);
		for (NewsType newsType : newstypes)
		{
			News news = new News();
			news.setId(newsType.getNewsId());
			news.setTitle(newsType.getTitle());
			news.setBrief(newsType.getBrief());
			news.setContent(newsType.getContent());
			news.setNewsDate(newsType.getNewsDate().toGregorianCalendar());
			newsList.add(news);
		}
		log.warn("Got after\"FINDBYID\" result={}", newsList);
		return newsList.get(0);
	}

	@Override
	public News save(News entity) throws FacadeException 
	{
		ServiceRequest request = new ServiceRequest();
		log.debug("Attempt to save News using {} action in SOAP request. Request has been created.", UPDATE);
		if (null == entity)
		{
			log.debug("Incorrect news={}. Id must be not null", entity);
			throw new FacadeException(FacadeExceptionCode.UNKNOWN_REQUEST, "newsType");
		}
		log.debug("Params for request: news={}", entity);
		request.setAction(UPDATE);
		NewsType newsTypeToSave = new NewsType();
		newsTypeToSave.setBrief(entity.getBrief());
		newsTypeToSave.setNewsId(entity.getId());
		newsTypeToSave.setTitle(entity.getTitle());
		newsTypeToSave.setContent(entity.getContent());
		if (entity.getNewsDate().getClass().equals(GregorianCalendar.class))
		{
			newsTypeToSave.setNewsDate(xmlDatatypeFactory.newXMLGregorianCalendar((GregorianCalendar)entity.getNewsDate()));
		}
		else
		{
			log.debug("NewsDate in news has unknown form using {}", entity.getNewsDate().getClass());
			throw new FacadeException(FacadeExceptionCode.UNKNOWN_REQUEST, "newsDate");
		}
		request.setNewsType(newsTypeToSave);
		ServiceResponse response = (ServiceResponse) webServiceTemplate.marshalSendAndReceive(request);
		if (null == response)
		{
			throw new FacadeException(FacadeExceptionCode.NO_RESPONSE);
		}
		log.debug("Received response for action={}. NewsTypes in response:={}, response={}", new Object[]{UPDATE, (null != response ? response.getNewsType(): null), response});
		List<NewsType> newstypes = response.getNewsType();
		List<News> newsList = new ArrayList<News>(1);
		for (NewsType newsType : newstypes)
		{
			entity.setId(newsType.getNewsId());
			entity.setTitle(newsType.getTitle());
			entity.setBrief(newsType.getBrief());
			entity.setContent(newsType.getContent());
			entity.setNewsDate(newsType.getNewsDate().toGregorianCalendar());
			newsList.add(entity);
		}
		log.warn("Got after\"FINDBYID\" result={}", newsList);
		return newsList.get(0);
	}

	@Override
	public void remove(Integer... ids) throws FacadeException 
	{
		ServiceRequest request = new ServiceRequest();
		log.debug("Attempt to get newsById using {} action in SOAP request. Request has been created.", DELETE);
		if ((null == ids) || (ids.length < 1))
		{
			log.debug("Incorrect IDs={}. Ids must be not null and must contain at least 1 number", ids);
			throw new FacadeException(FacadeExceptionCode.UNKNOWN_REQUEST, "id");
		}
		log.debug("Params for request: ids={}", ids);
		request.setAction(DELETE);
		for (Integer id : ids)
		{
			request.getId().add(id);
		}
		ServiceResponse response = (ServiceResponse) webServiceTemplate.marshalSendAndReceive(request);
		if (null == response)
		{
			throw new FacadeException(FacadeExceptionCode.NO_RESPONSE);
		}
		log.debug("Received response for action={}. NewsTypes in response:={}, response={}", new Object[]{DELETE, (null != response ? response.getNewsType(): null), response});
		return;
	}

	@Override
	public News updateNews(News entity) throws FacadeException 
	{
		return save(entity);
	}
}