package com.groupware.bookcatalogadmin.controller;

import com.groupware.bookcatalogadmin.EditElementActivity;
import com.groupware.bookcatalogadmin.model.ObjectBook;

public class ActivityEditElementController {
	
private final static String TAG = ActivityEditElementController.class.getSimpleName();
	
	private EditElementActivity activity;
	private ObjectBook element;
	
	public ActivityEditElementController() {
		
	}
	
	public ActivityEditElementController(EditElementActivity activity) {
		this.activity = activity;
		//activity.addSaveElementOnClickListener(saveElementOnClickListener);
	}

}
