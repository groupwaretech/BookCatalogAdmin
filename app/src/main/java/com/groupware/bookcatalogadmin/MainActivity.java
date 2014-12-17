package com.groupware.bookcatalogadmin;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.groupware.bookcatalogadmin.controller.ActivityMainController;
import com.groupware.bookcatalogadmin.model.AbstractModel;
import com.groupware.bookcatalogadmin.model.ObjectBook;
import com.groupware.bookcatalogadmin.model.ObjectBookModel;

public class MainActivity extends ActionBarActivity {

	private static final String TAG = MainActivity.class.getSimpleName();
	
	private ActivityMainController controller;
	private LruCache mMemoryCache;
	private AbstractModel<ObjectBook> model;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("Listado de materiales");
		actionBar.setIcon(R.drawable.icon_bar);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		final int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);
		final int cacheSize = maxMemory / 8;
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
					return bitmap.getByteCount() / 1024;
				} else {
					return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
				}
			}
		};
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.i(TAG, "onStart");
		if (controller == null) {
			controller = new ActivityMainController(this);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_new) {
			model = ObjectBookModel.getInstance();
			model.setObject(new ObjectBook());
			Intent intent = new Intent(this, RegisterElementActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
	    if (getBitmapFromMemCache(key) == null) {
	        mMemoryCache.put(key, bitmap);
	    }
	}

	public Bitmap getBitmapFromMemCache(String key) {
	    return (Bitmap) mMemoryCache.get(key);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
