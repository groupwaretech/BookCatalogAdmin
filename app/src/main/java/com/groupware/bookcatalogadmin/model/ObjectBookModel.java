package com.groupware.bookcatalogadmin.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.groupware.bookcatalogadmin.model.helper.ConsumeODataService;
import com.groupware.bookcatalogadmin.model.helper.ConsumeServiceHelper;

public class ObjectBookModel extends AbstractModel<ObjectBook>{
	
	private static ObjectBookModel instance;
	
	public static ObjectBookModel getInstance(){
        if(instance == null){
            instance = new ObjectBookModel();
        }
        return instance;
    }
	
	private ObjectBookModel() {
		super(ObjectBook.class);
	}
	
	public ObjectBook getObjectBook() {
		return getObject();
	}
	
	public InputStream getImageUrlInputStream(String path, String imageName) {
		InputStream is = null;
		try {
			is = (InputStream) new URL("http://54.187.205.50"+path+imageName).getContent();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}

	@Override
	public String create() {
		return ConsumeServiceHelper.createElement(getObject());
	}

	@Override
	public String edit() {
		return null;
	}

	@Override
	public String remove() {
		return null;
	}

	@Override
	public ObjectBook find(Object id) {
		ObjectBook objectBook = ConsumeODataService.getElement((String)id);
		setObject(objectBook);
		return objectBook;
	}

	@Override
	public List<ObjectBook> findAll() {
		return null;
	}

	@Override
	public List<ObjectBook> findRange(int[] range) {
		return null;
	}
	

}
