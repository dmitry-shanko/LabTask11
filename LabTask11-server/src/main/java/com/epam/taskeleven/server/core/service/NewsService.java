package com.epam.taskeleven.server.core.service;

import java.util.List;

import com.epam.taskeleven.server.core.service.exception.ServiceException;
import com.epam.taskeleven.server.model.News;

public interface NewsService 
{
	
	List<News> getNewsList() throws ServiceException;
	News fetchById(Integer id) throws ServiceException;
	News save(News entity) throws ServiceException;
	void remove(Integer... ids) throws ServiceException;
	News updateNews(News entity) throws ServiceException;
}
