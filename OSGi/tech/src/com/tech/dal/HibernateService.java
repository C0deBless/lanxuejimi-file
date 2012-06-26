package com.tech.dal;

import org.hibernate.*;
import org.hibernate.cfg.*;

public class HibernateService {
	private SessionFactory _factory;
	private Session _session;
	private Transaction _trans;
	public HibernateService(){
		this._factory=new Configuration().configure().buildSessionFactory();
		this._session=this._factory.openSession();
	}
	public HibernateService(SessionFactory factory){
		this._factory=factory;
		this._session=this._factory.openSession();
	}
	public Session getSession(){
		if(this._factory!=null){
			return this._session;
		}
		else{
			return null;
		}
	}
	public void beginTransaction(){
		if(this._session!=null){
			this._trans=this._session.beginTransaction();
			this._trans.commit();
		}
	}
	public void rollback(){
		if(this._trans!=null){
			this._trans.rollback();
		}
	}
	public void dispose(){
		this._session.close();
		this._factory.close();
	}
}
