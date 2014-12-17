package com.groupware.bookcatalogadmin.controller;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.groupware.bookcatalogadmin.model.Category;
import com.groupware.bookcatalogadmin.model.helper.ConsumeODataService;
import com.groupware.bookcatalogadmin.model.helper.ConsumeServiceHelper;
import com.groupware.bookcatalogadmin.view.adapters.CategoryAdapater;
import com.groupware.bookcatalogadmin.view.fragments.CategoryFragment;

public class FragmentCategoryController {
	
	private final static String TAG = FragmentCategoryController.class.getSimpleName();
	private final static String STATE_LIST_VIEW_CATEGORY = "listViewCategory";
	private CategoryFragment fragment;
	private List<Category> listCategory;
	private CategoryAdapater categoryAdapater;
	
	
	
	public FragmentCategoryController(CategoryFragment fragment) {
		Log.i(TAG, "Constructor");
		this.fragment = fragment;
		ListView listView = fragment.getListViewCategory();
		listView.setOnItemClickListener(listViewItemClickListener);
		listCategory  = new ArrayList<Category>();
		new ListCategoryTask(fragment.getActivity()).execute((Void)null);
		fragment.getNewCategoryDialog();
		
	}
	
	private OnItemClickListener listViewItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Log.i(TAG, "FragmentCategoryController - onItemClick");
			CategoryAdapater adapter = (CategoryAdapater) parent.getAdapter();
			Category category = adapter.getItem(position);
			listCategory.add(category);
			
		}
	};
	
	Parcelable mListInstanceState = null;
	
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i(TAG, "onActivityCreated");
		if (savedInstanceState == null) {
			listCategory  = new ArrayList<Category>();
			new ListCategoryTask(fragment.getActivity()).execute((Void)null);
		} 
	}
	
	public Bundle onSaveInstanceState(Bundle savedInstanceState) {
		Log.i(TAG, "onSaveInstanceState");
		return savedInstanceState;
	}
	
	public void onStart() {
		Log.i(TAG, "onStart");
		
		if (categoryAdapater != null) {
			ListView listView = fragment.getListViewCategory();
			listView.setAdapter(categoryAdapater);
		}
	}
	
	public void onResume(){
		Log.i(TAG, "onResume");
		
	}
	
	private class ListCategoryTask extends AsyncTask<Void, Void, String> {

		private Activity activity;
		private ProgressDialog progressDialog;
		private List<Category> listCategory;
		
		public ListCategoryTask(Activity activity) {
			this.activity = activity;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = ProgressDialog.show(activity, "",  "Consultando categorias...", false);
		}
		
		@Override
		protected String doInBackground(Void... arg0) {
			try {
				Log.i(TAG, "doInBackground");
				listCategory = ConsumeServiceHelper.getCategories();
				
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			String selectedCategory = fragment.getActivity().getIntent().getExtras().getString(FragmentElementController.STATE_SELECTED_CATEGORY);
		    System.out.println(":::: "+selectedCategory);
			ListView listView = fragment.getListViewCategory();
			categoryAdapater = new CategoryAdapater(fragment, android.R.layout.simple_list_item_1, listCategory, selectedCategory);
			listView.setAdapter(categoryAdapater);
			progressDialog.dismiss();
		}
		
	}
	
	public void sendCategory(String name) {
		new CreateCategoryTask(fragment.getActivity()).execute(name);
	}
	
	private class CreateCategoryTask extends AsyncTask<String, Void, String> {

		private Activity activity;
		private ProgressDialog progressDialog;
		
		public CreateCategoryTask(Activity activity) {
			this.activity = activity;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = ProgressDialog.show(activity, "",  "Enviando categoria...", false);
		}
		
		@Override
		protected String doInBackground(String... arg) {
			String result = null;
			try {
				Log.i(TAG, "doInBackground");
				Category category = new Category();
				category.setName(arg[0]);
				result = ConsumeODataService.createCategory(category);
				
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			
			Log.i(TAG, "onPostExecute "+result);
			progressDialog.dismiss();
		}
		
	}
	
	public void finalize() {
		Intent intent = fragment.getActivity().getIntent();
		Bundle bundle = new Bundle();
		for (int i = 0; i < listCategory.size(); i++) {
			bundle.putSerializable("category"+i, listCategory.get(i));
			intent.putExtras(bundle);
			fragment.getActivity().setResult(FragmentElementController.LIST_CATEGORIES, intent);
		}
		fragment.getActivity().finish();
	}

	public List<Category> getListCategory() {
		return listCategory;
	}

	public void setListCategory(List<Category> listCategory) {
		this.listCategory = listCategory;
	}

	public void setFragment(CategoryFragment fragment) {
		this.fragment = fragment;
	}
	
	
	
}
