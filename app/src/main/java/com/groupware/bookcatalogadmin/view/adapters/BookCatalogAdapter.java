package com.groupware.bookcatalogadmin.view.adapters;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.groupware.bookcatalogadmin.ListBookCatalogActivity;
import com.groupware.bookcatalogadmin.MainActivity;
import com.groupware.bookcatalogadmin.R;
import com.groupware.bookcatalogadmin.ViewElementActivity;
import com.groupware.bookcatalogadmin.model.AbstractModel;
import com.groupware.bookcatalogadmin.model.ObjectBook;
import com.groupware.bookcatalogadmin.model.ObjectBookModel;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.util.List;

/**
 * Created by heinsohnbusiness on 13/12/14.
 */
public class BookCatalogAdapter extends RecyclerView.Adapter<BookCatalogAdapter.ViewHolder> {

    private final static String TAG = BookCatalogAdapter.class.getSimpleName();

    private List<ObjectBook> objects;
    private ListBookCatalogActivity context;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tittleText;
        public TextView authorText;
        public TextView codeText;
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            tittleText = (TextView) view.findViewById(R.id.textViewTittleElement);
            authorText = (TextView) view.findViewById(R.id.textViewAuthorElement);
            codeText = (TextView) view.findViewById(R.id.textViewCodeElement);
            imageView = (ImageView) view.findViewById(R.id.imageViewElement);
            view.setOnClickListener(onClickListener);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onItemClick");


                int itemPosition = getPosition();

                final ObjectBook element = objects.get(itemPosition);

                final ObjectBookModel model = ObjectBookModel.getInstance();
                final Handler mHandler = new Handler();

                final ProgressDialog progressDialog = ProgressDialog.show(context, "",  "Cargando detalle...", false);
                progressDialog.show();
                new Thread(new Runnable() {
                    public void run() {
                        ObjectBook object = model.find(element.getCode());
                        System.out.println("------ "+object);
                        mHandler.post(new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                                Intent intent = new Intent(context, ViewElementActivity.class);
                                context.startActivity(intent);
                            }
                        });
                    }
                }).start();
            }
        };

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BookCatalogAdapter(List<ObjectBook> objects, ListBookCatalogActivity context) {
        this.objects = objects;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tittleText.setText(objects.get(position).getTittle());
        holder.authorText.setText(objects.get(position).getAuthor());
        holder.codeText.setText(objects.get(position).getCode());
        ObjectBook currentElement = objects.get(position);
        new ShowImageTask(currentElement, holder.imageView).execute((Void)null);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return objects.size();
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
