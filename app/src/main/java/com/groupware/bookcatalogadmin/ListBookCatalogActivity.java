package com.groupware.bookcatalogadmin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.groupware.bookcatalogadmin.controller.ActivityMainController;
import com.groupware.bookcatalogadmin.controller.ListBookCatalogController;
import com.groupware.bookcatalogadmin.model.AbstractModel;
import com.groupware.bookcatalogadmin.model.ObjectBook;
import com.groupware.bookcatalogadmin.model.ObjectBookModel;
import com.groupware.bookcatalogadmin.view.adapters.BookCatalogAdapter;


public class ListBookCatalogActivity extends ActionBarActivity {

    private static final String TAG = ListBookCatalogActivity.class.getSimpleName();

    public RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ListBookCatalogController controller;
    private LruCache mMemoryCache;
    private AbstractModel<ObjectBook> model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book_catalog);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);



        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Listado de materiales");
        actionBar.setIcon(R.drawable.icon_bar);

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
            controller = new ListBookCatalogController(this);
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


}
