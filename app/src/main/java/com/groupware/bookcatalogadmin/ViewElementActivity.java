package com.groupware.bookcatalogadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.groupware.bookcatalogadmin.model.AbstractModel;
import com.groupware.bookcatalogadmin.model.ObjectBook;
import com.groupware.bookcatalogadmin.view.fragments.ViewElementFragment;

public class ViewElementActivity extends ActionBarActivity {
	
	private final static String TAG = ViewElementActivity.class.getSimpleName();
	private ViewElementFragment viewElementFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_element);
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("Detalle");
		actionBar.setIcon(R.drawable.icon_bar);
		viewElementFragment = new ViewElementFragment();
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, viewElementFragment).commit();
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_element, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_edit) {

			startActivity(new Intent(this, RegisterElementActivity.class));
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
