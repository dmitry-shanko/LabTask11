package com.epam.taskeleven.server.core.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;

import com.epam.taskeleven.server.core.database.exception.DaoException;
import com.epam.taskeleven.server.core.database.exception.DaoExceptionCode;

public class HibernateDaoImpl<T, ID extends Serializable> implements GeneralDao<T, ID>
{
	static
	{
		Locale.setDefault(Locale.US);
	}
	
	private static final Logger log = LoggerFactory.getLogger(HibernateDaoImpl.class);
	private SessionFactory sessionFactory;
	private Class<T> persistanceType;
	private Class<ID> countType;
	private String persistanceTypeName;
	private String countTypeName;
	
	private HibernateDaoImpl(Class<T> persistanceType, Class<ID> countType)
	{
		this.setPersistanceType(persistanceType);
		this.setCountType(countType);
		this.persistanceTypeName = persistanceType.getSimpleName();
		this.countTypeName = countType.getSimpleName();
	}
	
	@SuppressWarnings("unused")
	private void init()
	{
		log.debug("Debug message for {}:", getClass());
		log.debug("Params: sessionFactory={}, persistanceType={}, persistanceTypeName={}, countType={}, countTypeName={}", new Object[]{sessionFactory, persistanceType, persistanceTypeName, countType, countTypeName});
	}
	
	public void setSessionFactory(SessionFactory sessionFactory)
	{
		if (null != sessionFactory)
		{
			this.sessionFactory = sessionFactory;
		}
	}
	/**
	 * @return the persistanceType
	 */
	public Class<T> getPersistanceType() 
	{
		return persistanceType;
	}
	/**
	 * @param persistanceType the persistanceType to set
	 */
	public void setPersistanceType(Class<T> persistanceType) 
	{
		this.persistanceType = persistanceType;
	}
	/**
	 * @return the countType
	 */
	public Class<ID> getCountType() 
	{
		return countType;
	}
	/**
	 * @param countType the countType to set
	 */
	public void setCountType(Class<ID> countType) 
	{
		this.countType = countType;
	}
	/**
	 * @return the persistanceTypeName
	 */
	public String getPersistanceTypeName() 
	{
		return persistanceTypeName;
	}
	/**
	 * @param persistanceTypeName the persistanceTypeName to set
	 */
	public void setPersistanceTypeName(String persistanceTypeName) 
	{
		this.persistanceTypeName = persistanceTypeName;
	}
	/**
	 * @return the countTypeName
	 */
	public String getCountTypeName() 
	{
		return countTypeName;
	}
	/**
	 * @param countTypeName the countTypeName to set
	 */
	public void setCountTypeName(String countTypeName) 
	{
		this.countTypeName = countTypeName;
	}

	@Override
	public List<T> getList() throws DaoException 
	{
		log.debug("com.epam.taskeleven.server.database.HibernateDaoImpl public List<T> getList() throws DaoException");
		return findByCriteria();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T fetchById(ID id) throws DaoException 
	{
		log.debug("com.epam.taskeleven.server.database.HibernateDaoImpl public T fetchById(ID id) throws DaoException: id={}", id);
		if (id == null) 
		{
			return null;
		}
		else
		{
			try
			{
				Session session = getSession();
				return (T) session.get(getPersistanceType(), id);
			}
			catch (HibernateException e)
			{
				log.debug("HibernateException catched: ", e.getMessage());
				throw new DaoException(DaoExceptionCode.READ_EXCEPTION, e, new Object[]{getPersistanceTypeName(), e.getClass()});
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T save(T entity) throws DaoException 
	{
		log.debug("com.epam.taskeleven.server.database.HibernateDaoImpl public boolean save(T entity) throws DaoException: entity={}", entity);
		try
		{
			if (null != entity)
			{
				log.debug("Attempt to save {}: entity={}", getPersistanceType(), entity);
				Session session = getSession();
				session.saveOrUpdate(entity);
				ID id = (ID) getSession().save(entity);
				session.flush();
				log.debug("Attempt to save {} was successfull: entity={}", getPersistanceType(), entity);
				return fetchById(id);
			}
		}
		catch (HibernateException e)
		{
			log.debug("HibernateException catched: ", e.getMessage());
			throw new DaoException(DaoExceptionCode.UPDATE_EXCEPTION, e, new Object[]{getPersistanceTypeName(), e.getClass()});
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void remove(ID[] ids) throws DaoException 
	{
		log.debug("com.epam.taskeleven.server.database.HibernateDaoImpl public void remove(Integer[] ids) throws DaoException: ids={}", ids);
		if (ids != null) 
		{
			for (ID id : ids)
			{
				try
				{
					log.debug("Attempt to delete {}: id={}", getPersistanceTypeName(), id);
					Session session = getSession();
					T t = (T) session.get(getPersistanceType(), id);
					if (null != t)
					{
						session.delete(t);
						log.debug("Attempt to delete {} was successfull: id={}", getPersistanceType(), id);
					}
					else
					{
						log.debug("Attempt to delete {} was not successfull. Can't find entity with such id: id={}", getPersistanceType(), id);
					}
					session.flush();					
				}
				catch (HibernateException e)
				{
					log.debug("HibernateException catched: ", e.getMessage());
					throw new DaoException(DaoExceptionCode.DELETE_EXCEPTION, e, new Object[]{getPersistanceTypeName(), getPersistanceTypeName().concat(".ID=") + id, e.getClass()});
				}
			}
		}
	}

	@Override
	public T update(T entity) throws DaoException 
	{
		return this.save(entity);
	}

	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(Criterion... criterion) throws DaoException
	{		
		log.debug("com.epam.taskeleven.server.database.HibernateDaoImpl protected List<T> findByCriteria(Criterion... criterion) throws DaoException: criterion={}", criterion);
		if (null != criterion)
		{
			try
			{
				Session session = getSession();
				Criteria crit = session.createCriteria(getPersistanceType());
				for (Criterion c : criterion) 
				{
					crit.add(c);
				}
				return crit.list();
			}
			catch (HibernateException e)
			{
				log.debug("HibernateException catched: ", e.getMessage());
				throw new DaoException(DaoExceptionCode.CRITERIA_FIND_EXCEPTION, e, new Object[]{getPersistanceTypeName(), e.getClass()});
			}
		}
		else
		{
			return new ArrayList<T>(0);
		}
	}

	protected Session getSession() throws DaoException
	{
		Session session = null;
		if (null == sessionFactory)
		{
			throw new DaoException(DaoExceptionCode.INITIALIZATION_EXCEPTION, new Object[]{getClass(), "sessionFactory", sessionFactory});
		}
		else
		{
			try 
			{
				session = sessionFactory.getCurrentSession();
			} 
			catch (HibernateException e) 
			{
				try
				{
					log.warn("Can't getCurrentSession from sessionFactory=" + sessionFactory, e);
					session = sessionFactory.openSession();
				}
				catch (HibernateException e1)
				{
					log.debug("HibernateException catched: ", e1.getMessage());
					throw new DaoException(DaoExceptionCode.SESSION_OPEN_EXCEPTION, e1, new Object[]{sessionFactory.toString().concat(".openSession()")});
				}
			}
		}		
		return session;
	}
}