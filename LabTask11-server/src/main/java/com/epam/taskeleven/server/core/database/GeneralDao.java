package com.epam.taskeleven.server.core.database;

import java.io.Serializable;
import java.util.List;

import com.epam.taskeleven.server.core.database.exception.DaoException;

public interface GeneralDao<T, ID extends Serializable> 
{
	List<T> getList() throws DaoException;
	T fetchById(ID id) throws DaoException;
	T save(T entity) throws DaoException;
	void remove(ID[] ids) throws DaoException;
	T update(T entity) throws DaoException;
}
