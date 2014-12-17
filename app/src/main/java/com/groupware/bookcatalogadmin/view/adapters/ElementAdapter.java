package com.groupware.bookcatalogadmin.view.adapters;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.groupware.bookcatalogadmin.MainActivity;
import com.groupware.bookcatalogadmin.R;
import com.groupware.bookcatalogadmin.model.AbstractModel;
import com.groupware.bookcatalogadmin.model.ObjectBook;
import com.groupware.bookcatalogadmin.model.ObjectBookModel;

public class ElementAdapter extends ArrayAdapter<ObjectBook>{
	
	private MainActivity context;
	private List<ObjectBook> objects;
	
	
	public ElementAdapter(MainActivity context
            , int resource
            , List<ObjectBook> objects) {
		super(context, resource, objects);
		this.context = context;
		this.objects = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View view = inflater.inflate(R.layout.item_list_element, null);
		TextView tittleText = (TextView) view.findViewById(R.id.textViewTittleElement);
		TextView authorText = (TextView) view.findViewById(R.id.textViewAuthorElement);
		TextView codeText = (TextView) view.findViewById(R.id.textViewCodeElement);
		ObjectBook currentElement = objects.get(position);
		tittleText.setText(currentElement.getTittle());
		authorText.setText(currentElement.getAuthor());
		codeText.setText(currentElement.getCode());
		
		ImageView imageView = (ImageView) view.findViewById(R.id.imageViewElement);
		new ShowImageTask(currentElement, imageView).execute((Void)null);
		return view;
	}
	
	private class ShowImageTask extends AsyncTask<Void, Void, Bitmap> {
		
		private final WeakReference<ImageView> imageViewReference;
		
		private ObjectBook currentElement;
		//private ImageView imageView;
		
		public ShowImageTask(ObjectBook currentElement, ImageView imageView) {
			this.currentElement = currentElement;
			//this.imageView = imageView;
			imageViewReference = new WeakReference<ImageView>(imageView);
		}
		
		@Override
		protected Bitmap doInBackground(Void... params) {
			Bitmap bitmap = null;
			try {
				bitmap = context.getBitmapFromMemCache(currentElement.getCode()); 
				if (bitmap == null) {
					BitmapFactory.Options bmOptions = new BitmapFactory.Options();
					bmOptions.inSampleSize = 2; // 1 = 100% if you write 4 means 1/4 = 25% 
					//InputStream is = (InputStream) new URL("http://54.187.205.50/images/"+currentElement.getCode()+"-0.jpg").getContent();
					AbstractModel<ObjectBook> model = ObjectBookModel.getInstance();
					InputStream is = ((ObjectBookModel)model).getImageUrlInputStream(currentElement.getImageList().get(0).getPath(), currentElement.getImageList().get(0).getName());
					
					//InputStream is = fetch("http://54.187.205.50/images/"+currentElement.getCode()+"-0.jpg");
					ImageView thumbnail = imageViewReference.get();
					Bitmap bitmapO = BitmapFactory.decodeStream(is);
					bitmap = Bitmap.createScaledBitmap(bitmapO, 160, 200, true);
					context.addBitmapToMemoryCache(currentElement.getCode(), bitmap);
				} 
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return bitmap;
		}
		
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			/*
			if (result != null) {
				Bitmap resized = Bitmap.createScaledBitmap(result, 160, 200, true);
				imageView.setImageBitmap(resized);
			}
			*/
			
			if (isCancelled()) {
				bitmap = null;
			}
			
			if (imageViewReference != null && bitmap != null) {
	            final ImageView imageView = imageViewReference.get();
	            imageView.setImageBitmap(bitmap);
	            /*
	            final ShowImageTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
	            if (this == bitmapWorkerTask && imageView != null) {
	                imageView.setImageBitmap(bitmap);
	            }
	            */
	        }
			
		}
		
		private InputStream fetch(String urlString) throws MalformedURLException, IOException {
	        DefaultHttpClient httpClient = new DefaultHttpClient();
	        HttpGet request = new HttpGet(urlString);
	        HttpResponse response = httpClient.execute(request);
	        return response.getEntity().getContent();
	    }
		
		private  ShowImageTask getBitmapWorkerTask(ImageView imageView) {
			   if (imageView != null) {
			       final Drawable drawable = imageView.getDrawable();
			       if (drawable instanceof AsyncDrawable) {
			           final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
			           return asyncDrawable.getBitmapWorkerTask();
			       }
			    }
			    return null;
			}
		
		private class AsyncDrawable extends BitmapDrawable {
		    private final WeakReference<ShowImageTask> bitmapWorkerTaskReference;

		    public AsyncDrawable(Resources res, Bitmap bitmap,
		    		ShowImageTask bitmapWorkerTask) {
		        super(res, bitmap);
		        bitmapWorkerTaskReference =
		            new WeakReference<ShowImageTask>(bitmapWorkerTask);
		    }

		    public ShowImageTask getBitmapWorkerTask() {
		        return bitmapWorkerTaskReference.get();
		    }
		}
	}

}
