package com.groupware.bookcatalogadmin.view.adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.groupware.bookcatalogadmin.model.ObjectBookType;

public class ElementTypeAdapter extends ArrayAdapter<ObjectBookType> {

	

	private Activity context;
	private List<ObjectBookType> objects;
	
	public ElementTypeAdapter(Activity context
            , int resource
            , List<ObjectBookType> objects) {
		super(context, resource, objects);
		this.context = context;
		this.objects = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
		TextView text = (TextView) view.findViewById(android.R.id.text1);
		ObjectBookType elementType = objects.get(position);
		text.setText(elementType.getName());
		return view;
	}

}
