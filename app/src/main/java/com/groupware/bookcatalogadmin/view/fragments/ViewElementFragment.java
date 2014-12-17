package com.groupware.bookcatalogadmin.view.fragments;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.groupware.bookcatalogadmin.R;
import com.groupware.bookcatalogadmin.controller.FragmentElementPhotoController;
import com.groupware.bookcatalogadmin.model.AbstractModel;
import com.groupware.bookcatalogadmin.model.Image;
import com.groupware.bookcatalogadmin.model.ObjectBook;
import com.groupware.bookcatalogadmin.model.ObjectBookModel;
import com.heinsohn.databinding.DataBinding;
import com.heinsohn.databinding.annotation.Property;

public class ViewElementFragment extends Fragment {
	
	private final static String TAG = ViewElementFragment.class.getSimpleName();
	private LinearLayout myGallery;
	@Property(dataClass = ObjectBook.class)
	private TextView tittle;
	@Property(dataClass = ObjectBook.class)
	private TextView author;
	@Property(dataClass = ObjectBook.class)
	private TextView code;
	private TextView objectBookType;
	private TextView category;
	@Property(dataClass = ObjectBook.class)
	private TextView publication;
	@Property(dataClass = ObjectBook.class)
	private TextView languageContent;
	@Property(dataClass = ObjectBook.class)
	private TextView isbn;
	@Property(dataClass = ObjectBook.class)
	private TextView physicalDescription;
	@Property(dataClass = ObjectBook.class)
	private TextView notes;
	
	private AbstractModel<ObjectBook> model;
	
	//private ObjectBook element = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_view_element, container, false);
		model = ObjectBookModel.getInstance();
		
		tittle = (TextView)rootView.findViewById(R.id.textViewTittleView);
		author = (TextView)rootView.findViewById(R.id.textViewAuthorView);
		code = (TextView)rootView.findViewById(R.id.textViewCodeView);
		objectBookType = (TextView)rootView.findViewById(R.id.textViewObjectBookTypeView);
		category = (TextView)rootView.findViewById(R.id.textViewCategoryView);
		publication = (TextView)rootView.findViewById(R.id.textViewPublicationView);
		languageContent = (TextView)rootView.findViewById(R.id.textViewLanguageView);
		isbn = (TextView)rootView.findViewById(R.id.textViewIsbnView);
		physicalDescription = (TextView)rootView.findViewById(R.id.textViewPhysicalDescriptionView);
		notes = (TextView)rootView.findViewById(R.id.textViewNotesView);
		
		myGallery = (LinearLayout)rootView.findViewById(R.id.mygalleryView);
		
		return rootView;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Log.i(TAG, "onStart");
		ObjectBook element = (ObjectBook) model.getObject();
		DataBinding.setDataValue(element, ViewElementFragment.this, getView().getContext());
		
		for (Image image : element.getImageList()) {
			new ShowImageTask(this).execute(image.getPath(), image.getName());
		}
		
		
		
	}
	
	
	private class ShowImageTask extends AsyncTask<String, Void, Bitmap> {
		
		private ViewElementFragment fragment;
		private ProgressDialog progressDialog;
		
		public ShowImageTask(ViewElementFragment fragment) {
			this.fragment = fragment;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = ProgressDialog.show(fragment.getActivity(), "",  "Cargando imagenes...", false);
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bitmap = null;
			try {
				BitmapFactory.Options bmOptions = new BitmapFactory.Options();
				bmOptions.inSampleSize = 2; // 1 = 100% if you write 4 means 1/4 = 25% 
				InputStream is = ((ObjectBookModel)model).getImageUrlInputStream(params[0], params[1]);
				Bitmap bitmapO = BitmapFactory.decodeStream(is);
				bitmap = Bitmap.createScaledBitmap(bitmapO, 240, 280, true);

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return bitmap;
		}
		
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			addImageGallery(bitmap);
		}
		
		
		private void addImageGallery(Bitmap bitmap) {
			if (bitmap != null) {
				LinearLayout layout = new LinearLayout(fragment.getActivity());
			    layout.setLayoutParams(new LayoutParams(bitmap.getWidth(), bitmap.getHeight()));
			    layout.setGravity(Gravity.CENTER);
			    layout.setClickable(true);
			    layout.setPadding(3, 0, 5, 0);
			    
			    ImageView imageView = new ImageView(fragment.getActivity());
			    imageView.setLayoutParams(new LayoutParams(bitmap.getWidth(), bitmap.getHeight()));
			    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			    imageView.setImageBitmap(bitmap);
			    
			    layout.addView(imageView);
			    
			    //LinearLayout myGallery = (LinearLayout)fragment.getView().findViewById(R.id.mygallery);
			    myGallery.addView(layout);
			}
			
		    progressDialog.dismiss();
		}
		
	}


	public TextView getTittle() {
		return tittle;
	}

	public void setTittle(TextView tittle) {
		this.tittle = tittle;
	}

	public TextView getAuthor() {
		return author;
	}

	public void setAuthor(TextView author) {
		this.author = author;
	}

	public TextView getCode() {
		return code;
	}

	public void setCode(TextView code) {
		this.code = code;
	}

	public TextView getObjectBookType() {
		return objectBookType;
	}

	public void setObjectBookType(TextView objectBookType) {
		this.objectBookType = objectBookType;
	}

	public TextView getCategory() {
		return category;
	}

	public void setCategory(TextView category) {
		this.category = category;
	}

	public TextView getPublication() {
		return publication;
	}

	public void setPublication(TextView publication) {
		this.publication = publication;
	}

	public TextView getLanguageContent() {
		return languageContent;
	}

	public void setLanguageContent(TextView languageContent) {
		this.languageContent = languageContent;
	}

	public TextView getIsbn() {
		return isbn;
	}

	public void setIsbn(TextView isbn) {
		this.isbn = isbn;
	}

	public TextView getPhysicalDescription() {
		return physicalDescription;
	}

	public void setPhysicalDescription(TextView physicalDescription) {
		this.physicalDescription = physicalDescription;
	}

	public TextView getNotes() {
		return notes;
	}

	public void setNotes(TextView notes) {
		this.notes = notes;
	}


	/*
	public void addImageToCache(final String currentPhotoPath) {
		Log.i(TAG, "listPathPhotos - "+listPathPhotos);
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
		
		LinearLayout layout = new LinearLayout(fragment.getActivity());
	    layout.setLayoutParams(new LayoutParams(bitmap.getWidth(), bitmap.getHeight()));
	    layout.setGravity(Gravity.CENTER);
	    layout.setClickable(true);
	    layout.setPadding(3, 0, 5, 0);
	    
	    ImageView imageView = new ImageView(fragment.getActivity());
	    imageView.setLayoutParams(new LayoutParams(bitmap.getWidth(), bitmap.getHeight()));
	    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	    imageView.setImageBitmap(bitmap);
	    
	    imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.i(TAG, "ImageView - onClick");
				Log.i(TAG, "listPathPhotos - "+listPathPhotos);
				ImageView photo = (ImageView) view;
				
				Dialog mSplashDialog = new Dialog(fragment.getActivity());
				mSplashDialog.requestWindowFeature((int) Window.FEATURE_NO_TITLE);
				mSplashDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
				mSplashDialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				mSplashDialog.setContentView(R.layout.dialog_view_photo);
				ImageView image = (ImageView)mSplashDialog.findViewById(R.id.ImageView_photo); 
				photo.buildDrawingCache();
				image.setImageBitmap(photo.getDrawingCache());
				
				mSplashDialog.show();
				
			}
		});
	    
	    layout.addView(imageView);
	    fragment.getMyGallery().addView(layout);
		
	}
	*/
	
	
	

}
