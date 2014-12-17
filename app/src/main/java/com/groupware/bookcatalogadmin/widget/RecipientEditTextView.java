package com.groupware.bookcatalogadmin.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.groupware.bookcatalogadmin.R;

public class RecipientEditTextView extends EditText {
	
	private Context context;
	private LayoutInflater layoutInflater;
	public boolean isCharaterAdded = true;
	public boolean isContactAddedFromDb = false;
	public boolean isTextAdditionInProgress = false;
	public boolean checkValidation = true;
	public boolean isTextDeletedFromTouch = false;
	
	public SpannableStringBuilder ssb;

	public RecipientEditTextView(Context context) {
		super(context);
		init(context);
	}
	
	public RecipientEditTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public RecipientEditTextView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	/**
	 * Method called at each object
	 * initialization
	 * @param context
	 */
	public void init(Context context){
		this.context = context;
		layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		ssb = new SpannableStringBuilder();
	}
	
	public void addOrCheckSpannable(CharSequence s){
		SpannableString ss = new SpannableString(s);
		BitmapDrawable bmpDrawable = getBitmapFromText(s.toString());
		bmpDrawable.setBounds(0, 0,bmpDrawable.getIntrinsicWidth(), bmpDrawable.getIntrinsicHeight());
		ss.setSpan(new ImageSpan(bmpDrawable), 0, s.toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ssb.append(ss);	
		ssb.append(" ");
		setSpannableText(ssb);
	}
	
	private void setSpannableText(final Spannable ssb){
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				RecipientEditTextView.this.setText(ssb);
			}
		}, 20);
	}
	
	/**
	 * @param message
	 * @return BitmapDrawable
	 * method which takes string as input and 
	 * created a bitmapDrawable from that string using layoutInflater
	 */
	private BitmapDrawable getBitmapFromText(String message) {
		TextView textView = (TextView)layoutInflater.inflate(R.layout.textview, null);
		textView.setText(message);
		BitmapDrawable bitmapDrawable = (BitmapDrawable) extractBitmapFromTextView(textView);
		return bitmapDrawable;
	}
	
	public static Object extractBitmapFromTextView(View view) {
		int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		view.measure(spec, spec);
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		Bitmap b = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
				Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(b);
		c.translate(-view.getScrollX(), -view.getScrollY());
		view.draw(c);
		view.setDrawingCacheEnabled(true);
		Bitmap cacheBmp = view.getDrawingCache();
		Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);
		view.destroyDrawingCache();
		return new BitmapDrawable(viewBmp);

	}

}
