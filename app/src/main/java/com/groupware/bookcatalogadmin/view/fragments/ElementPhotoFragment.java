package com.groupware.bookcatalogadmin.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.groupware.bookcatalogadmin.R;
import com.groupware.bookcatalogadmin.controller.FragmentElementPhotoController;

public class ElementPhotoFragment extends Fragment {
	
	private final static String TAG = ElementPhotoFragment.class.getSimpleName();
	private LinearLayout myGallery;
	private FragmentElementPhotoController controller;
    private ImageButton cameraButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView");
		View rootView = inflater.inflate(R.layout.fragment_element_photo, container, false);
		cameraButton = (ImageButton) rootView.findViewById(R.id.Button_capture_photo);
		myGallery = (LinearLayout)rootView.findViewById(R.id.mygallery);
		
		return rootView;
	}
	
	public void addCameraButtonListener(OnClickListener onClickListener) {
		cameraButton.setOnClickListener(onClickListener);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Log.i(TAG, "onStart");
		if (controller == null) {
			controller = new FragmentElementPhotoController(this);
		}
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(TAG, "onActivityResult");
		controller.addImage(requestCode, resultCode, data);
	}
	
	public LinearLayout getMyGallery() {
		return myGallery;
	}

	public FragmentElementPhotoController getController() {
		return controller;
	}
	
	
	
}
