package com.groupware.bookcatalogadmin;

import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.groupware.bookcatalogadmin.controller.ActivityCategoryController;
import com.groupware.bookcatalogadmin.controller.FragmentCategoryController;
import com.groupware.bookcatalogadmin.model.Category;
import com.groupware.bookcatalogadmin.view.fragments.CategoryFragment;

public class CategoryActivity extends ActionBarActivity {
	
	private final static String TAG = CategoryActivity.class.getSimpleName();
	private ActivityCategoryController controller;
	
	private CategoryFragment categoryFragment;
	private ActionMode mActionMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("Listado de categorias");
		actionBar.setIcon(R.drawable.icon_bar);

		if (savedInstanceState == null) {
			categoryFragment = new CategoryFragment();
			getSupportFragmentManager().beginTransaction().add(R.id.container, categoryFragment).commit();
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		if (controller==null) {
			controller = new ActivityCategoryController(this);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.category, menu);
		
		MenuItem searchViewItem = menu.findItem(R.id.action_search);
	    SearchView searchView = (SearchView) searchViewItem.getActionView();
	    
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		int id = item.getItemId();
		switch (id) {
		case R.id.action_new:
			categoryFragment.getNewCategoryDialog().show();
			break;
		case R.id.action_search:
			
			break;
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Log.i(TAG, "onBackPressed");
		setResult(0);
		finish();
	}
	
	//Boolean isBackPressed = Boolean.FALSE;
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
        	Log.i(TAG, "onBackPressed");
           // handle your back button code here
        	//isBackPressed = Boolean.TRUE;
        	setResult(0);
    		finish();
           return true; // consumes the back key event - ActionMode is not finished
        }
	    return super.dispatchKeyEvent(event);
	}	
	
	public void initActionMode() {
		if (mActionMode == null) {
			 // Start the CAB
	        mActionMode = startSupportActionMode(mActionModeCallback);  
        }
	}

	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			Log.i(TAG, "onActionItemClicked - onActionItemClicked");
			
			int id = item.getItemId();
			switch (id) {
			case R.id.action_new:
				
				break;
			case R.id.action_search:
				
				
				
				break;
			default:
				break;
			}
			
			//mode.finish(); // Action picked, so close the CAB
			return true;
		}
		

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			 // inflate a menu resource providing context menu items
		      MenuInflater inflater = mode.getMenuInflater();
		      // assumes that you have "contexual.xml" menu resources
		      inflater.inflate(R.menu.category, menu);
		      
		      MenuItem searchViewItem = menu.findItem(R.id.action_search);
			  SearchView searchView = (SearchView) searchViewItem.getActionView();

			  return true;
		}


		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			Log.i(TAG, "onActionItemClicked - onPrepareActionMode");
			return false;
		}
		
		@Override
		public void onDestroyActionMode(ActionMode mode) {
			Log.i(TAG, "onActionItemClicked - onDestroyActionMode");
			
			FragmentCategoryController contll =  categoryFragment != null ? categoryFragment.getController() : null;
			if (contll != null) {
				contll.finalize();
			}
			
			mActionMode = null;
			
		}
		
	};

}
