package com.groupware.bookcatalogadmin.controller;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.groupware.bookcatalogadmin.R;
import com.groupware.bookcatalogadmin.RegisterElementActivity;
import com.groupware.bookcatalogadmin.model.AbstractModel;
import com.groupware.bookcatalogadmin.model.ObjectBook;
import com.groupware.bookcatalogadmin.model.ObjectBookModel;
import com.groupware.bookcatalogadmin.model.helper.ConsumeServiceHelper;
import com.groupware.bookcatalogadmin.view.fragments.ElementFragment;
import com.groupware.bookcatalogadmin.view.fragments.ElementPhotoFragment;

public class ActivityRegisterElementController {
	
	private final static String TAG = ActivityRegisterElementController.class.getSimpleName();
	
	private RegisterElementActivity activity;
	//private ObjectBook element;
	//private ElementPhotoFragment elementPhotoFragment;
	private List<String> listPathPhotos;
	
	private AbstractModel<ObjectBook> model;
	
	public ActivityRegisterElementController() {
		
	}
	
	public ActivityRegisterElementController(RegisterElementActivity activity) {
		this.activity = activity;
		model = ObjectBookModel.getInstance();
		//activity.addSaveElementOnClickListener(saveElementOnClickListener);
	}
	
	/**
	 * Control para crear nuevo elemento
	 */
	private OnClickListener saveElementOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			
			FragmentManager fManager = activity.getSupportFragmentManager();
			//ElementFragment elementFragment = (ElementFragment) fManager.findFragmentById(R.id.container);
			ElementFragment elementFragment = null;
			ElementPhotoFragment elementPhotoFragment = null;
			for (Fragment fragment : fManager.getFragments()) {
				if (fragment instanceof ElementFragment) {
					elementFragment = (ElementFragment) fragment;
				} else if (fragment instanceof ElementPhotoFragment) {
					elementPhotoFragment = (ElementPhotoFragment) fragment;
				}
			}
			//Recuperar informacion de la vista
			elementFragment.getController().captureElement();
			//element = elementFragment.getController().getElement();
			//element = model.getObject();
			
			listPathPhotos = elementPhotoFragment.getController().getListPathPhotos();
			if (model.getObject() != null) {
				RegisterDialogFragment dialog = new RegisterDialogFragment();
				dialog.show(fManager, null);
			}
			
			
		}
	};
	
	public void saveElement() {
		FragmentManager fManager = activity.getSupportFragmentManager();
		//ElementFragment elementFragment = (ElementFragment) fManager.findFragmentById(R.id.container);
		ElementFragment elementFragment = null;
		ElementPhotoFragment elementPhotoFragment = null;
		for (Fragment fragment : fManager.getFragments()) {
			if (fragment instanceof ElementFragment) {
				elementFragment = (ElementFragment) fragment;
			} else if (fragment instanceof ElementPhotoFragment) {
				elementPhotoFragment = (ElementPhotoFragment) fragment;
			}
		}
		//Recuperar informacion de la vista
		elementFragment.getController().captureElement();
		//element = elementFragment.getController().getElement();
		listPathPhotos = elementPhotoFragment.getController().getListPathPhotos();
		if (model.getObject() != null) {
			RegisterDialogFragment dialog = new RegisterDialogFragment();
			dialog.show(fManager, null);
		}
	}
	
	public class RegisterDialogFragment extends DialogFragment {
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the Builder class for convenient dialog construction
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        builder.setMessage(R.string.dialog_message_register)
	               .setPositiveButton(R.string.button_accept, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   RegisterElementTask task = new RegisterElementTask();
	                	   task.execute();
	                   }
	               })
	               .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // User cancelled the dialog
	                   }
	               });
	        // Create the AlertDialog object and return it
	        return builder.create();
	    }
	}
	
	public class ConfirmDialogFragment extends DialogFragment {
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the Builder class for convenient dialog construction
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        builder.setMessage(R.string.dialog_confirm_register)
	               .setPositiveButton(R.string.button_accept, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   	FragmentManager fManager = activity.getSupportFragmentManager();
	           				ElementFragment elementFragment = null;
	           				for (Fragment fragment : fManager.getFragments()) {
	           					if (fragment instanceof ElementFragment) {
	           						elementFragment = (ElementFragment) fragment;
	           						break;
	           					} 
	           				}
	           				
	           				elementFragment.getController().cleanForm();
	                   }
	               });
	        // Create the AlertDialog object and return it
	        return builder.create();
	    }
	}
	
	public class ErrorDialogFragment extends DialogFragment {
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the Builder class for convenient dialog construction
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        builder.setMessage(R.string.dialog_error_register)
	               .setPositiveButton(R.string.button_accept, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   
	                   }
	               });
	        // Create the AlertDialog object and return it
	        return builder.create();
	    }
	}
	
	private class RegisterElementTask extends AsyncTask<Void, Void, String> {
		
		private ProgressDialog progressDialog;
		
		@Override
		protected void onPreExecute() {
			Log.i(TAG, "onPreExecute");
			progressDialog = ProgressDialog.show(activity, "",  "Enviando registro...", false);
		}
		
		@Override
		protected String doInBackground(Void... elements) {
			Log.i(TAG, "doInBackground");
			//String result = "OK";
			//String result = ConsumeODataService.createElement(elements[0]);
			//String result = ConsumeServiceHelper.createElement(elements[0]);
			String result = model.create();
			
			
			return result != null ? result : "ERROR";
		}
		
		@Override
		protected void onPostExecute(String result) {
			Log.i(TAG, "onPostExecute");
			Log.i(TAG, "RegisterElementTask - onPostExecute: "+result != null ? result : "ERROR");
			progressDialog.dismiss();
			
			if ("OK".equals(result)) {
				int prefix = 0;
				Log.i(TAG, "listPathPhotos: "+listPathPhotos);
				for (String pathPhoto : listPathPhotos) {
					new UploadPhotoTask(prefix).execute(pathPhoto);
					prefix++;
				}
				new ConfirmDialogFragment().show(activity.getSupportFragmentManager(), null);
			} else {
				new ErrorDialogFragment().show(activity.getSupportFragmentManager(), null);
			}
		}
		
	}
	
	private class UploadPhotoTask extends AsyncTask<String, Void, String> {
		private int prefix;
		
		public UploadPhotoTask(int prefix) {
			this.prefix = prefix;
		}

		@Override
		protected String doInBackground(String... bitmapPaths) {
			String result = "ERROR";
			Log.i(TAG, "codeElement: "+model.getObject());
			if (model.getObject() != null && model.getObject().getCode() != null) {
				Log.i(TAG, "codeElement: "+model.getObject().getCode());
				
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				Bitmap bitmap = BitmapFactory.decodeFile(bitmapPaths[0], options);
				
				result = ConsumeServiceHelper.uploadPhoto(bitmap, model.getObject().getCode()+"NoN"+prefix);
			}
			
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			Log.i(TAG, "UploadPhotoTask - onPostExecute "+result != null ? result : "ERROR");
		}
		
	}

}
