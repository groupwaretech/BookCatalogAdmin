package com.groupware.bookcatalogadmin.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.groupware.bookcatalogadmin.R;
import com.groupware.bookcatalogadmin.utils.AlbumStorageDirFactory;
import com.groupware.bookcatalogadmin.utils.BaseAlbumDirFactory;
import com.groupware.bookcatalogadmin.utils.FroyoAlbumDirFactory;
import com.groupware.bookcatalogadmin.view.fragments.ElementPhotoFragment;

import eu.janmuller.android.simplecropimage.CropImage;

public class FragmentElementPhotoController {
	
	private final static String TAG = FragmentElementPhotoController.class.getSimpleName();
	private ElementPhotoFragment fragment;
	//private BitmapLruCache imageCache;
	private List<String> listPathPhotos;
	private File      mFileTemp;
	private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
	
	private static final String JPEG_FILE_PREFIX = "IMG_";
	private static final String JPEG_FILE_SUFFIX = ".jpg";
	private static final Uri CONTENT_URI = Uri.parse("content://com.bookcatalogadmin/");
	
	public static final int REQUEST_CODE_TAKE_PICTURE = 0x1;
	public static final int REQUEST_CODE_GALLERY      = 0x2;
    public static final int REQUEST_CODE_CROP_IMAGE   = 0x3;
	
    /**
     * Constructor controller
     */
	public FragmentElementPhotoController() {
		
	}
	
	/**
	 * Constructor controller
	 * 
	 * @param fragment
	 */
	public FragmentElementPhotoController(ElementPhotoFragment fragment) {
		Log.i(TAG, "FragmentElementPhotoController - INIT");
		this.fragment = fragment;
		this.fragment.addCameraButtonListener(cameraButtonListener);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
		} else {
			mAlbumStorageDirFactory = new BaseAlbumDirFactory();
		}
		listPathPhotos = new ArrayList<String>();
		//imageCache = new BitmapLruCache();
	}
	
	/**
	 * Controlador boton capturar foto
	 */
	private OnClickListener  cameraButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	        try {
	        	mFileTemp = createImageFile();
	        	Uri mImageCaptureUri = null;
	        	String state = Environment.getExternalStorageState();
	        	if (Environment.MEDIA_MOUNTED.equals(state)) {
	        		mImageCaptureUri = Uri.fromFile(mFileTemp);
	        	}
	        	else {
		        	/*
		        	 * The solution is taken from here: http://stackoverflow.com/questions/10042695/how-to-get-camera-result-as-a-uri-in-data-folder
		        	 */
		        	mImageCaptureUri = CONTENT_URI;
	        	}	
	            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
	            intent.putExtra("return-data", true);
	            fragment.startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
	        } catch (ActivityNotFoundException e) {
	            Log.d(TAG, "cannot take picture", e);
	        } catch (IOException e) {
				e.printStackTrace();
			}

		}
	};
	
	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
		File albumF = getAlbumDir();
		File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
		return imageF;
	}
	
	private File getAlbumDir() {
		File storageDir = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(fragment.getString(R.string.album_name));
			if (storageDir != null) {
				if (! storageDir.mkdirs()) {
					if (! storageDir.exists()){
						Log.d(fragment.getString(R.string.album_name), "failed to create directory");
						return null;
					}
				}
			}
			
		} else {
			Log.v(fragment.getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
		}
		
		return storageDir;
	}
	
	private void galleryAddPic(String currentPhotoPath) {
	    Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
		File f = new File(currentPhotoPath);
	    Uri contentUri = Uri.fromFile(f);
	    mediaScanIntent.setData(contentUri);
	    fragment.getActivity().sendBroadcast(mediaScanIntent);
	    
	}
	
	private void startCropImage() {
        Intent intent = new Intent(fragment.getActivity(), CropImage.class);
        intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
        intent.putExtra(CropImage.SCALE, false);
        intent.putExtra(CropImage.SCALE_UP_IF_NEEDED, false);
        intent.putExtra(CropImage.ASPECT_X, 2);
        intent.putExtra(CropImage.ASPECT_Y, 3);

        fragment.startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }
	
	public void addImage(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
	        case REQUEST_CODE_GALLERY:
	
	            break;
	        case REQUEST_CODE_TAKE_PICTURE:
	            startCropImage();
	            break;
	        case REQUEST_CODE_CROP_IMAGE:
	            String path = data.getStringExtra(CropImage.IMAGE_PATH);
	            if (path == null) {
	                return;
	            }
	            //bitmap = BitmapFactory.decodeFile(mFileTemp.getPath());
	            galleryAddPic(path);
	            
	            listPathPhotos.add(path);
	            addImageToCache(path);
	            break;
		}
	}
	
	
	
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
	
	public List<String> getListPathPhotos() {
		return listPathPhotos;
	}

	//ImageCache implementation
	public class BitmapLruCache extends LruCache<String, Bitmap> {
	    //private static final int DEFAULT_CACHE_SIZE = (int) (Runtime.getRuntime().maxMemory() / 1024) / 8;

	    public BitmapLruCache() {
	        this((int) (Runtime.getRuntime().maxMemory() / 1024) / 8);
	    }

	    public BitmapLruCache(int maxSize) {
	        super(maxSize);
	    }

	    public Bitmap getBitmap(String url) {
	        return get(url);
	    }

	    public void putBitmap(String url, Bitmap bitmap) {
	        put(url, bitmap);
	    }

	    @Override
	    protected int sizeOf(String key, Bitmap value) {
	        return value == null ? 0 : value.getRowBytes() * value.getHeight() / 1024;
	    }
	    
	    @Override
	    protected void entryRemoved(boolean evicted, String key,
	    		Bitmap oldValue, Bitmap newValue) {
	    	super.entryRemoved(evicted, key, oldValue, newValue);
	    }
	    
	    
	}
	

}
