package com.groupware.bookcatalogadmin.view.adapters;

import java.io.Serializable;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.groupware.bookcatalogadmin.CategoryActivity;
import com.groupware.bookcatalogadmin.R;
import com.groupware.bookcatalogadmin.model.Category;
import com.groupware.bookcatalogadmin.view.amin.FlipView;
import com.groupware.bookcatalogadmin.view.fragments.CategoryFragment;

public class CategoryAdapater extends ArrayAdapter<Category> implements Serializable{

	private CategoryFragment context;
	private List<Category> objects;
	
	private String selectedCategory = null;
	
	private Boolean isCategoryChange = Boolean.FALSE;
	
	public CategoryAdapater(CategoryFragment context
            				, int resource
        					, List<Category> objects
        					, String selectedCategory) {
		super(context.getActivity(), resource, objects);

		this.context = context;
		this.objects = objects;
		this.selectedCategory = selectedCategory;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.item_list_category, parent, false);
		TextView text = (TextView) view.findViewById(R.id.textViewListCategory);
		final Category category = objects.get(position);
		text.setText(category.getName());
		
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.linearLayoutListCategory);
		final LinearLayout layoutSelect = (LinearLayout) view.findViewById(R.id.linearLayoutListCategorySelect);
		
		//front face
		final RelativeLayout ff = (RelativeLayout) View.inflate(context.getActivity(), R.layout.face_check_front, null);
	    //back face
		final RelativeLayout bf = (RelativeLayout) View.inflate(context.getActivity(), R.layout.face_check_back, null);
	    
		FlipView fvtemp = null;
		
		if (selectedCategory != null && selectedCategory.indexOf(category.getName()) >= 0) {
			fvtemp = new FlipView(view.getContext(), ff, bf);
			CheckBox checkBox = (CheckBox) fvtemp.findViewById(R.id.checkBoxListCategory);
			checkBox.setChecked(Boolean.TRUE);
			context.getController().getListCategory().add(category);
	    } else {
	    	fvtemp = new FlipView(view.getContext(), bf, ff);
	    }
		
		final FlipView fv = fvtemp;
		final CheckBox checkBox = (CheckBox) fvtemp.findViewById(R.id.checkBoxListCategory);
		
		
	    fv.setAnimationDuration(300);
	    fv.setDirection(FlipView.DIRECTION_HORIZONTAL);
	    fv.setPivot (FlipView.PIVOT_CENTER);
	    layoutSelect.addView(fv);
	    
		
	    layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fv.flip();
				if (checkBox.isChecked()){
					checkBox.setChecked(Boolean.FALSE);
					context.getController().getListCategory().remove(category);
				} else {
					checkBox.setChecked(Boolean.TRUE);
					context.getController().getListCategory().add(category);
				}
				
				if (!isCategoryChange) {
					isCategoryChange = Boolean.TRUE;
					CategoryActivity categoryActivity = (CategoryActivity)context.getActivity();
					categoryActivity.initActionMode(); 
			            
				}
				
			}
		});
		
		
		return view;
	}

}
