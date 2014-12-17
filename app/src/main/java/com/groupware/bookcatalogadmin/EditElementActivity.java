package com.groupware.bookcatalogadmin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.groupware.bookcatalogadmin.controller.ActivityEditElementController;
import com.groupware.bookcatalogadmin.view.fragments.ElementFragment;
import com.groupware.bookcatalogadmin.view.fragments.ElementPhotoFragment;

public class EditElementActivity extends ActionBarActivity {

	private final static String TAG = EditElementActivity.class.getSimpleName();
	
	private ActivityEditElementController controller;
	private LinearLayout saveLayout;
	
	private AppSectionsPagerAdapter mAppSectionsPagerAdapter;
	private ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.activity_register_element);
 
	    final ActionBar actionBar = getSupportActionBar();
	    actionBar.setTitle("Editar material");
	    actionBar.setIcon(R.drawable.icon_bar);
	    //actionBar.setDisplayShowHomeEnabled(false);
	    //actionBar.setDisplayShowTitleEnabled(false);     
	    //final LayoutInflater inflater = (LayoutInflater) getSystemService("layout_inflater");
	    //View view = inflater.inflate(R.layout.action_bar_edit_mode, null); 
	    //actionBar.setCustomView(view);
	    //actionBar.setDisplayShowCustomEnabled(true);
	    
	    mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());
	    
	    // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        //mViewPager.setBackgroundColor(Color.GRAY);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });
        
       // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
        	Tab tab = actionBar.newTab();
        	tab.setText(mAppSectionsPagerAdapter.getPageTitle(i));
        	tab.setTabListener(tabListener);
        	
            actionBar.addTab(tab);
        }
	    
        /*
	    actionBar.addTab(actionBar.newTab().setText("Titulo 1"));
	    
		if (savedInstanceState == null) {
			fManager.beginTransaction().add(R.id.container, new ElementFragment()).commit();
			
		}
		*/
		saveLayout = (LinearLayout) findViewById(R.id.action_done);
	}
	
	public TabListener tabListener = new TabListener() {
		
		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {
			Log.i(TAG, "onTabUnselected");
		}
		
		@Override
		public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
			Log.i(TAG, "onTabSelected");
			mViewPager.setCurrentItem(tab.getPosition());
		}
		
		@Override
		public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {
			Log.i(TAG, "onTabReselected");
		}
	};
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.i(TAG, "onStart");
		controller = new ActivityEditElementController(this);
	}
	
	/*
	public void addSaveElementOnClickListener(OnClickListener onClickListener) {
		saveLayout.setOnClickListener(onClickListener);
	}
	*/
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
	}

	
	public class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                	//elementFragment = new ElementFragment();
                   return new ElementFragment(); 
                default:
                	//elementPhotoFragment = new ElementPhotoFragment();
                   return new ElementPhotoFragment();
               
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
        	switch (position) {
            case 0:
               return "Principal"; 
            default:
                return "Fotos";
           
        }
        }
    }


	public ActivityEditElementController getController() {
		return controller;
	}


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_element, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_save) {

			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	 

}
