package com.epam.taskeleven.client.presentation.facade;

import java.util.List;

import com.epam.taskeleven.client.model.News;
import com.epam.taskeleven.client.presentation.exception.FacadeException;

public interface NewsFacade 
{
	
	List<News> getNewsList() throws FacadeException;
	News fetchById(Integer id) throws FacadeException;
	News save(News entity) throws FacadeException;
	void remove(Integer... ids) throws FacadeException;
	News updateNews(News entity) throws FacadeException;
}
