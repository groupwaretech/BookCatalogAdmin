package com.groupware.bookcatalogadmin.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.groupware.bookcatalogadmin.ListBookCatalogActivity;
import com.groupware.bookcatalogadmin.MainActivity;
import com.groupware.bookcatalogadmin.R;
import com.groupware.bookcatalogadmin.ViewElementActivity;
import com.groupware.bookcatalogadmin.model.AbstractModel;
import com.groupware.bookcatalogadmin.model.ObjectBook;
import com.groupware.bookcatalogadmin.model.ObjectBookModel;
import com.groupware.bookcatalogadmin.model.helper.ConsumeODataService;
import com.groupware.bookcatalogadmin.view.adapters.BookCatalogAdapter;
import com.groupware.bookcatalogadmin.view.adapters.ElementAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListBookCatalogController {

	private final static String TAG = ListBookCatalogController.class.getSimpleName();

	private ListBookCatalogActivity activity;
	private AbstractModel<ObjectBook> model;

	public ListBookCatalogController(ListBookCatalogActivity activity) {
		this.activity = activity;
		//ListView listView = (ListView) activity.findViewById(R.id.listViewElements);

		new ListElementTask(activity).execute((Void)null);
	}
	
	private OnItemClickListener listViewItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Log.i(TAG, "onItemClick");
			ElementAdapter adapter = (ElementAdapter) parent.getAdapter();
			final ObjectBook element = adapter.getItem(position);
			
			model = ObjectBookModel.getInstance();
			final Handler mHandler = new Handler();
			
			final ProgressDialog progressDialog = ProgressDialog.show(activity, "",  "Cargando detalle...", false);
			progressDialog.show();
			new Thread(new Runnable() {
			    public void run() {
			    	ObjectBook object = model.find(element.getCode());
			    	System.out.println("------ "+object);
			    	mHandler.post(new Runnable() {
			            public void run() {
			            	progressDialog.dismiss();
			            	Intent intent = new Intent(activity, ViewElementActivity.class);
			            	activity.startActivity(intent);
			            }
			    	});
			    }
			}).start();
			
			//Intent intent = new Intent(activity, ViewElementActivity.class);
			//intent.putExtra("element", element);
			//activity.startActivity(intent);
		
		}
	};
	
	private class ListElementTask extends AsyncTask<Void, Void, String> {
		
		private ListBookCatalogActivity activity;
		private ProgressDialog progressDialog;
		private List<ObjectBook> listElement;
		
		public ListElementTask(ListBookCatalogActivity activity) {
			this.activity = activity;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = ProgressDialog.show(activity, "",  "Consultando elementos...", false);
		}

		@Override
		protected String doInBackground(Void... params) {
			try {
				Log.e(TAG, "doInBackground");
				listElement = ConsumeODataService.getElements();
				
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			List<String> list = new ArrayList<String>();
			for (ObjectBook element : listElement) {
				list.add(element.getTittle());
			}

            RecyclerView.Adapter mAdapter = new BookCatalogAdapter(listElement, activity);
            activity.mRecyclerView.setAdapter(mAdapter);

			progressDialog.dismiss();
		}
		
	}

}
