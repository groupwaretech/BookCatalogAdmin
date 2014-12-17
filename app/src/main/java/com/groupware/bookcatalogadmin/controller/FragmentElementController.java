package com.groupware.bookcatalogadmin.controller;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.groupware.bookcatalogadmin.CategoryActivity;
import com.groupware.bookcatalogadmin.R;
import com.groupware.bookcatalogadmin.model.AbstractModel;
import com.groupware.bookcatalogadmin.model.Category;
import com.groupware.bookcatalogadmin.model.ObjectBook;
import com.groupware.bookcatalogadmin.model.ObjectBookModel;
import com.groupware.bookcatalogadmin.model.ObjectBookType;
import com.groupware.bookcatalogadmin.model.data.ElementTypeDAO;
import com.groupware.bookcatalogadmin.view.adapters.ElementTypeAdapter;
import com.groupware.bookcatalogadmin.view.fragments.ElementFragment;
import com.groupware.bookcatalogadmin.widget.RecipientEditTextView;
import com.heinsohn.databinding.DataBinding;

public class FragmentElementController {
	
	private final static String TAG = FragmentElementController.class.getSimpleName();
	
	
	
	static final int LIST_CATEGORIES = 1;
	//Recuperar estado del objeto elementType al girar la pantalla
	static final String STATE_ELEMENT_TYPE = "elementType";
	public static final String STATE_SELECTED_CATEGORY = "selectedCategory";
	private ElementFragment fragment;
	private ObjectBook element;
	private ObjectBookType elementType;
	
	private AbstractModel<ObjectBook> model;
	
	public FragmentElementController() {
		
	}
	
	public FragmentElementController(final ElementFragment fragment) {
		this.fragment = fragment;
		model = ObjectBookModel.getInstance();
		Spinner  spinnerElementType  = fragment.getSpinnerElementType();
		ElementTypeDAO dao = new ElementTypeDAO(fragment.getActivity());
		List<ObjectBookType> listElementType = dao.listElementType();
		
		ElementTypeAdapter spinnerArrayAdapter = new ElementTypeAdapter(fragment.getActivity(), android.R.layout.simple_spinner_dropdown_item, listElementType);
		spinnerElementType.setAdapter(spinnerArrayAdapter);
		spinnerElementType.setOnItemSelectedListener(spinnerElementTypeOnItemSelectedListener);
		
		RecipientEditTextView editTextCategories  = (RecipientEditTextView) fragment.getView().findViewById(R.id.EditTextCategories);
		editTextCategories.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(fragment.getActivity(), CategoryActivity.class);
				String selectedCategory = fragment.getEditTextCategories().getText().toString();
				Bundle bundle = new Bundle();
				bundle.putString(STATE_SELECTED_CATEGORY, selectedCategory != null ? selectedCategory : "");
				intent.putExtras(bundle);
				fragment.startActivityForResult(intent, LIST_CATEGORIES);
			}
		});
		
		//editTextCategories.addOrCheckSpannable("HOLA");
		
	}
	
	public void captureElement() {
		ObjectBook element = DataBinding.getDataValue(ObjectBook.class, fragment, fragment.getView().getContext());
		this.element = element;
		model.setObject(element);
		System.out.println(element);
		/*
		ObjectBook element = new ObjectBook();
		
		Activity parent                           = fragment.getActivity();
		EditText editTextCode                     = fragment.getEditTextCode();
		EditText editTextTittle                   = fragment.getEditTextTittle();
		EditText editTextAuthor                   = fragment.getEditTextAuthor();
		Spinner  spinnerElementType               = fragment.getSpinnerElementType();
	    EditText editTextYear        		      = fragment.getEditTextYear();
		RecipientEditTextView editTextCategories  = fragment.getEditTextCategories();
		EditText editTextDescription              = fragment.getEditTextDescription();
		EditText editTextLanguaje                 = fragment.getEditTextLanguaje();
		EditText editTextIsbn                     = fragment.getEditTextIsbn();
		EditText editTextPhysicalDescription      = fragment.getEditTextPhysicalDescription();
		
		boolean cancel = false;
		View focusView = null;
		Animation animation = AnimationUtils.loadAnimation(fragment.getView().getContext(), R.anim.validate_text);
		
		Log.i(TAG, "Code: " + editTextCode.getText().toString());
		if (TextUtils.isEmpty(editTextCode.getText().toString())) {
			editTextCode.setError("Campo requerido");
			focusView = editTextCode;
			cancel = true;
			editTextCode.startAnimation(animation);
		} else {
			editTextCode.setError(null);
			element.setCode(editTextCode.getText().toString());
		}
		
		Log.i(TAG, "Tittle: " + editTextTittle.getText().toString());
		if (TextUtils.isEmpty(editTextTittle.getText().toString())) {
			editTextTittle.setError("Campo requerido");
			focusView = editTextTittle;
			cancel = true;
			editTextTittle.startAnimation(animation);
		} else {
			editTextTittle.setError(null);
			element.setTittle(editTextTittle.getText().toString());
		}
		
		Log.i(TAG, "Author: " + editTextAuthor.getText().toString());
		if (TextUtils.isEmpty(editTextAuthor.getText().toString())) {
			editTextAuthor.setError("Campo requerido");
			focusView = editTextAuthor;
			cancel = true;
			editTextAuthor.startAnimation(animation);
		} else {
			editTextAuthor.setError(null);
			element.setAuthor(editTextAuthor.getText().toString());
		}
		
		Log.i(TAG, "Element type: " + elementType);
		if (elementType == null) {
			focusView = spinnerElementType;
			cancel = true;
			spinnerElementType.startAnimation(animation);
		} else {
			element.setObjectBookType(elementType);
		}
		
		Log.i(TAG, "Year publication: " + editTextYear.getText().toString());
		element.setPublication(editTextYear.getText().toString());
		Log.i(TAG, "Category: " + editTextCategories.getText().toString());
		element.setCategory(editTextCategories.getText().toString()); 
		Log.i(TAG, "Description: " + editTextDescription.getText().toString());
		element.setNotes(editTextDescription.getText().toString()); 
		element.setLanguageContent(editTextLanguaje.getText().toString());
		element.setIsbn(editTextIsbn.getText().toString());
		element.setPhysicalDescription(editTextPhysicalDescription.getText().toString());
		
		if (cancel) {
			// Get instance of Vibrator from current Context
			Vibrator vibrator = (Vibrator) parent.getSystemService(Context.VIBRATOR_SERVICE);
			 
			// Vibrate for 300 milliseconds
			vibrator.vibrate(300);
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
			
			element = null;
		} 
		
		
		 this.element = element;
		 model.setObject(element);
		 */
	}
	
	OnItemSelectedListener spinnerElementTypeOnItemSelectedListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			elementType = (ObjectBookType) parent.getItemAtPosition(position);
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			
		}
	};
	
	public ObjectBook getElement() {
		return element;
	}

	public void setElement(ObjectBook element) {
		this.element = element;
		
		EditText editTextCode                     = fragment.getCode();
		EditText editTextTittle                   = fragment.getTittle();
		EditText editTextAuthor                   = fragment.getAuthor();
		Spinner  spinnerElementType               = fragment.getSpinnerElementType();
	    EditText editTextYear        			  = fragment.getEditTextYear();
		RecipientEditTextView editTextCategories  = fragment.getEditTextCategories();
		EditText editTextDescription              = fragment.getEditTextDescription();
		EditText editTextLanguaje                 = fragment.getEditTextLanguaje();
		EditText editTextIsbn                     = fragment.getEditTextIsbn();
		EditText editTextPhysicalDescription      = fragment.getEditTextPhysicalDescription();
		
		editTextCode.setText(element.getCode());
		editTextTittle.setText(element.getTittle());
		editTextAuthor.setText(element.getAuthor());
		editTextYear.setText(element.getPublication());
		editTextCategories.setText(element.getCategory());
		editTextDescription.setText(element.getNotes());
		
		editTextLanguaje.setText(element.getLanguageContent());
		editTextIsbn.setText(element.getIsbn());
		editTextPhysicalDescription.setText(element.getPhysicalDescription());
		
		
	}
	
	public Bundle onSaveInstanceState(Bundle savedInstanceState) {
		Log.i(TAG, "onSaveInstanceState");
		savedInstanceState.putSerializable(STATE_ELEMENT_TYPE, elementType);
		return savedInstanceState;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i(TAG, "onActivityCreated");
		if (savedInstanceState != null) {
			elementType = (ObjectBookType) savedInstanceState.getSerializable(STATE_ELEMENT_TYPE);
			
			ElementTypeDAO dao = new ElementTypeDAO(fragment.getActivity());
			List<ObjectBookType> listElementType = dao.listElementType();
			
			Spinner  spinnerElementType  = fragment.getSpinnerElementType();
			
			if (elementType != null) {
				int pos = 0;
				for (ObjectBookType elementType: listElementType) {
					if (elementType.equals(elementType)) {
						spinnerElementType.setSelection(pos);
						break;
					}
					pos++;
				}
			}
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		switch (resultCode) {
		case LIST_CATEGORIES:
			boolean isNull = false;
			int i = 0;
			int startIndex = 0;
			String categoryName = "";
			fragment.getEditTextCategories().ssb.clearSpans();
			fragment.getEditTextCategories().ssb = new SpannableStringBuilder();
			while (!isNull && data != null && data.getExtras() != null) {
				Category category = (Category) data.getExtras().getSerializable("category"+i);
				if (category == null) {
					isNull = true;
				} else {
					categoryName = category.getName();
					startIndex = categoryName.length();
					startIndex = startIndex < 1 ? 1 : startIndex;
					fragment.getEditTextCategories().addOrCheckSpannable(categoryName);
					
					System.out.println(category);
					i++;
				}
			}
			
			break;
		default:
			break;
		}
		
		
	}

	public void cleanForm() {
		EditText editTextCode                     = fragment.getCode();
		EditText editTextTittle                   = fragment.getTittle();
		EditText editTextAuthor                   = fragment.getAuthor();
		Spinner  spinnerElementType               = fragment.getSpinnerElementType();
	    EditText editTextYear        			  = fragment.getEditTextYear();
		RecipientEditTextView editTextCategories  = fragment.getEditTextCategories();
		EditText editTextDescription              = fragment.getEditTextDescription();
		EditText editTextLanguaje                 = fragment.getEditTextLanguaje();
		EditText editTextIsbn                     = fragment.getEditTextIsbn();
		EditText editTextPhysicalDescription      = fragment.getEditTextPhysicalDescription();
		
		editTextCode.setText(null);
		editTextTittle.setText(null);
		editTextAuthor.setText(null);
		editTextYear.setText(null);
		editTextCategories.setText(null);
		editTextDescription.setText(null);
		
		editTextLanguaje.setText(null);
		editTextIsbn.setText(null);
		editTextPhysicalDescription.setText(null);
	}

}
