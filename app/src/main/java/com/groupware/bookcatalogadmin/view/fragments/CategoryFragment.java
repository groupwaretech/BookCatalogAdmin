package com.groupware.bookcatalogadmin.view.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.groupware.bookcatalogadmin.R;
import com.groupware.bookcatalogadmin.controller.FragmentCategoryController;
import com.groupware.bookcatalogadmin.controller.FragmentElementController;

public class CategoryFragment extends Fragment {
	
	private final static String TAG = CategoryFragment.class.getSimpleName();
	private FragmentCategoryController controller;
	//Componentes visuales
	private ListView listViewCategory = null;
	private AlertDialog.Builder newCategoryDialog;
	private EditText nameCategory = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setRetainInstance(Boolean.TRUE);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView");
		
		View rootView = inflater.inflate(R.layout.fragment_category, container, false);
		listViewCategory = (ListView) rootView.findViewById(R.id.listViewCategory);
		newCategoryDialog = new AlertDialog.Builder(getActivity());
		
		final View inflateView = inflater.inflate(R.layout.dialog_add_category, null);
		nameCategory = (EditText) inflateView.findViewById(R.id.editTextNewCategory);
	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    newCategoryDialog.setView(inflateView)
	    
	    // Add action buttons
	           .setPositiveButton(R.string.action_accept, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   controller.sendCategory(nameCategory.getText().toString());
	               }
	           })
	           .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {

	               }
	           }); 
	    newCategoryDialog.create();
	    
	    
	    
		return rootView;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Log.i(TAG, "onStart");
		if (controller == null) {
			controller = new FragmentCategoryController(this);
		} else {
			controller.setFragment(this);
		}
		
		controller.onStart();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.i(TAG, "onStart");
		controller.onResume();
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.i(TAG, "onActivityCreated");
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		Log.i(TAG, "onSaveInstanceState");
		super.onSaveInstanceState(controller.onSaveInstanceState(outState));
	}
	
	public ListView getListViewCategory() {
		return listViewCategory;
	}

	public void setListViewCategory(ListView listViewCategory) {
		this.listViewCategory = listViewCategory;
	}

	public FragmentCategoryController getController() {
		return controller;
	}

	public AlertDialog.Builder getNewCategoryDialog() {
		return newCategoryDialog;
	}

	public void setNewCategoryDialog(AlertDialog.Builder newCategoryDialog) {
		this.newCategoryDialog = newCategoryDialog;
	}

}
