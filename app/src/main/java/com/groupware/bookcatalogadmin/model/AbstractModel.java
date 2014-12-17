package com.groupware.bookcatalogadmin.model;

import java.util.List;

public abstract class AbstractModel<T> {
	
	private Class<T> objectClass;
	private T object;

    public AbstractModel(Class<T> entityClass) {
        this.objectClass = objectClass;
    }
    
    public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public abstract String create();

    public abstract String edit();

    public abstract String remove();

    public abstract T find(Object id); 

    public abstract List<T> findAll();

    public abstract List<T> findRange(int[] range);

}
