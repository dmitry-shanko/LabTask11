package com.epam.taskeleven.server.core.service;

import java.util.Collections;
import java.util.List;

import com.epam.taskeleven.server.core.database.GeneralDao;
import com.epam.taskeleven.server.core.database.exception.DaoException;
import com.epam.taskeleven.server.core.service.exception.ServiceException;
import com.epam.taskeleven.server.core.service.exception.ServiceExceptionCode;
import com.epam.taskeleven.server.model.News;

public class NewsServiceImpl implements NewsService
{
	private GeneralDao<News, Integer> newsDao;
	
	public void setNewsDao(GeneralDao<News, Integer> newsDao)
	{
		this.newsDao = newsDao;
	}
	
	public GeneralDao<News,Integer> getNewsDao()
	{
		return this.newsDao;
	}

	@Override
	public List<News> getNewsList() throws ServiceException 
	{
		List<News> news = null;
		try 
		{
			news = newsDao.getList();
		} 
		catch (DaoException e) 
		{
			throw new ServiceException(ServiceExceptionCode.DATABASE_GETALL, e, News.class);
		}
		if (null != news)
		{
			Collections.sort(news);
		}
		return news;
	}

	@Override
	public News fetchById(Integer id) throws ServiceException 
	{
		try 
		{
			return newsDao.fetchById(id);
		} 
		catch (DaoException e) 
		{
			throw new ServiceException(ServiceExceptionCode.DATABASE_GET, e, News.class);
		}
	}

	@Override
	public News save(News entity) throws ServiceException 
	{
		try 
		{
			return newsDao.save(entity);
		} 
		catch (DaoException e) 
		{
			throw new ServiceException(ServiceExceptionCode.DATABASE_SAVE, e, News.class);
		}
		
	}

	@Override
	public void remove(Integer... ids) throws ServiceException 
	{
		try 
		{
			newsDao.remove(ids);
		} 
		catch (DaoException e) 
		{
			throw new ServiceException(ServiceExceptionCode.DATABASE_DELETE, e, News.class);
		}		
	}

	@Override
	public News updateNews(News entity) throws ServiceException 
	{
		try 
		{
			return newsDao.update(entity);
		} 
		catch (DaoException e) 
		{
			throw new ServiceException(ServiceExceptionCode.DATABASE_UPDATE, e, News.class);
		}
	}
}