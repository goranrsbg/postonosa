package com.goranrsbg.app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.mysql.cj.exceptions.ConnectionIsClosedException;

public class EmfUtil {
	
	private final EntityManagerFactory entityManagerFactory;

	private static EmfUtil instance;
	
	private EmfUtil() {
		entityManagerFactory = Persistence.createEntityManagerFactory("CRM");
	}
	
	public static void connect() {
		instance = new EmfUtil();
	}
	
	public static EntityManager getEntityManager() throws ConnectionIsClosedException {
		if(instance == null) throw new ConnectionIsClosedException();
		return instance.entityManagerFactory.createEntityManager();
	}
	
	public static void close() throws ConnectionIsClosedException {
		if(instance == null) throw new ConnectionIsClosedException();
		instance.entityManagerFactory.close();
	}
	
}
