package com.epam.taskeleven.server.core.soap;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;

import com.epam.taskeleven.server.core.service.NewsService;
import com.epam.taskeleven.server.core.service.exception.ServiceException;
import com.epam.taskeleven.server.model.News;
import com.epam.taskeleven.server.soap.NewsTypeDocument.NewsType;
import com.epam.taskeleven.server.soap.ServiceRequestDocument;
import com.epam.taskeleven.server.soap.ServiceRequestDocument.ServiceRequest;
import com.epam.taskeleven.server.soap.ServiceResponseDocument;
import com.epam.taskeleven.server.soap.ServiceResponseDocument.ServiceResponse;
import com.epam.taskeleven.server.soap.ServiceRequestDocument.ServiceRequest.Action.Enum;

@Endpoint
public class NewsSericeEndpoint 
{

	private static final Logger log = LoggerFactory.getLogger(NewsSericeEndpoint.class);
	private static final String namespaceUri = "http://www.epam.com/taskeleven/server/soap"; 
	private NewsService newsService; 

	public NewsSericeEndpoint()
	{
		log.debug("NewsSerivceEndpoint created");
	}
	
	@Autowired
	public void HelloService (NewsService newsService) 
	{
		this.newsService = newsService;
	} 

	@PayloadRoot(localPart = "ServiceRequest", namespace = namespaceUri)
	public ServiceResponseDocument getService(ServiceRequestDocument request)
	{
		ServiceRequestDocument reqDoc = request;
		ServiceRequest req = reqDoc.getServiceRequest(); 
		ServiceResponseDocument respDoc = ServiceResponseDocument.Factory.newInstance();
		ServiceResponse resp = respDoc.addNewServiceResponse();
		Enum action = req.getAction();
		log.debug("Request received. Request action={}, request={}", action, req);
		if (action != null)
		{
			int actionRequest = action.intValue();
			log.info("Action in request={}", actionRequest);
			switch(actionRequest)
			{
			case 1: 
				log.debug("Got \"FINDALL\" request.");
				List<News> newsList = null;
				try
				{
					newsList = newsService.getNewsList();
				}
				catch (ServiceException e)
				{
					log.error("NewsService has thrown na exception.", e);
				}
				log.debug("Got News List from service. Resultsize={}, result={}", ((newsList != null) ? newsList.size() : null), newsList);
				if (newsList != null)
				{					
					for (News news : newsList)
					{
						NewsType newsType = resp.addNewNewsType();
						newsType.setNewsId(news.getId());
						newsType.setBrief(news.getBrief());
						newsType.setTitle(news.getTitle());
						newsType.setNewsDate(news.getNewsDate());
						newsType.setContent(news.getContent());
					}
				}
				break;
			case 2:
				int[] ids = req.getIdArray();
				log.debug("Got \"FINDBYID\" request. Ids={}", ids);
				if (ids != null)
				{
					for (int i : ids)
					{
						News news = null;
						try
						{
							news = newsService.fetchById(i);
						}
						catch (ServiceException e)
						{
							log.error("NewsService has thrown na exception.", e);
						}
						if (news != null)
						{
							NewsType newsType = resp.addNewNewsType();
							newsType.setNewsId(news.getId());
							newsType.setBrief(news.getBrief());
							newsType.setTitle(news.getTitle());
							newsType.setNewsDate(news.getNewsDate());
							newsType.setContent(news.getContent());
						}
					}
				}
				break;
			case 3: 
				int[] idsDel = req.getIdArray();
				log.debug("Got \"DELETE\" request. IdsDel={}", idsDel);
				if (idsDel != null)
				{
					for (int i : idsDel)
					{
						try 
						{
							newsService.remove(i);
						} 
						catch (ServiceException e) 
						{
							log.error("NewsService has thrown na exception.", e);
						}
					}
				}
				break;
			case 4:
				log.debug("Got \"UPDATE\" request.");
				NewsType newsType = req.getNewsType();
				log.debug("newsType from request: newsType={}", newsType);
				if (null != newsType)
				{
					News news = new News();
					news.setId(newsType.getNewsId());
					if (null == news.getId())
					{
						news.setId(0);
					}
					news.setTitle(newsType.getTitle());
					news.setBrief(newsType.getBrief());
					news.setContent(newsType.getContent());
					news.setNewsDate(newsType.getNewsDate());
					if (news.getId() < 1)
					{
						try 
						{
							news = newsService.save(news);
						} 
						catch (ServiceException e) 
						{
							log.error("NewsService has thrown na exception.", e);
						}
					}
					else
					{
						try 
						{
							news = newsService.updateNews(news);
						} 
						catch (ServiceException e) 
						{
							log.error("NewsService has thrown na exception.", e);
						}
					}
					if (news != null)
					{
						newsType = resp.addNewNewsType();
						newsType.setNewsId(news.getId());
						newsType.setBrief(news.getBrief());
						newsType.setTitle(news.getTitle());
						newsType.setNewsDate(news.getNewsDate());
						newsType.setContent(news.getContent());
					}
				}
				break;
			default:
				log.warn("Unknown action in request. Can't proceed");
				break;					
			}
		}
		else
		{
			log.warn("Action in request is null. Can't answer on such request");
		}
		return respDoc;
	} 

}
