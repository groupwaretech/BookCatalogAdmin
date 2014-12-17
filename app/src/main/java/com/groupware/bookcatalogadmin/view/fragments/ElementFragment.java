package com.groupware.bookcatalogadmin.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;

import com.groupware.bookcatalogadmin.R;
import com.groupware.bookcatalogadmin.controller.FragmentElementController;
import com.groupware.bookcatalogadmin.model.AbstractModel;
import com.groupware.bookcatalogadmin.model.ObjectBook;
import com.groupware.bookcatalogadmin.model.ObjectBookModel;
import com.groupware.bookcatalogadmin.model.ObjectBookType;
import com.groupware.bookcatalogadmin.widget.RecipientEditTextView;
import com.heinsohn.databinding.DataBinding;
import com.heinsohn.databinding.annotation.Property;
import com.heinsohn.databinding.annotation.Required;

public class ElementFragment extends Fragment {
	
	private final static String TAG = ElementFragment.class.getSimpleName();
	
	//Controlador
	private FragmentElementController controller;
	//Componentes visuales, formulario
	@Required
	@Property(dataClass = ObjectBook.class)
	private EditText code   = null;
	@Required
	@Property(dataClass = ObjectBook.class)
	private EditText tittle = null;
	@Required
	@Property(dataClass = ObjectBook.class)
    private EditText author = null;
	@Property(dataClass = ObjectBook.class, dataPropertyName="objectBookType", dataPropertyType=ObjectBookType.class)
	private Spinner  spinnerElementType  			  = null;
	@Property(dataClass = ObjectBook.class, dataPropertyName="publication")
	private EditText editTextYear        			  = null;
	private RecipientEditTextView editTextCategories  = null;
	@Property(dataClass = ObjectBook.class, dataPropertyName="notes")
	private EditText editTextDescription 			  = null;
	@Property(dataClass = ObjectBook.class, dataPropertyName="languageContent")
	private EditText editTextLanguaje   			  = null;
	@Property(dataClass = ObjectBook.class, dataPropertyName="isbn")
	private EditText editTextIsbn       			  = null;
	@Property(dataClass = ObjectBook.class, dataPropertyName="physicalDescription")
	private EditText editTextPhysicalDescription 	  = null;
	
	private AbstractModel<ObjectBook> model;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView");
		View rootView = inflater.inflate(R.layout.fragment_element, container, false);
		
		//Evitar que se despliegue el teclado viertual al iniciar el fragment
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		spinnerElementType          = (Spinner) rootView.findViewById(R.id.SpinnerObjectBookType);
		code                = (EditText) rootView.findViewById(R.id.EditTextCode);
		tittle              = (EditText) rootView.findViewById(R.id.EditTextTittle);
		author              = (EditText) rootView.findViewById(R.id.EditTextAuthor);
		editTextYear                = (EditText) rootView.findViewById(R.id.EditTextPublication);
		editTextCategories          = (RecipientEditTextView) rootView.findViewById(R.id.EditTextCategories);
		editTextDescription         = (EditText) rootView.findViewById(R.id.EditTextDescription);
		editTextLanguaje            = (EditText) rootView.findViewById(R.id.EditTextLanguageContent);
		editTextIsbn                = (EditText) rootView.findViewById(R.id.EditTextIsbn);
		editTextPhysicalDescription = (EditText) rootView.findViewById(R.id.EditTextPhysicalDescription);
		
		return rootView;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Log.i(TAG, "onStart");
		updateView();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.i(TAG, "onResume");
	}

	public FragmentElementController getController() {
		return controller;
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		Log.i(TAG, "onSaveInstanceState");
		super.onSaveInstanceState(controller.onSaveInstanceState(savedInstanceState));
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.i(TAG, "onActivityCreated");
		controller = new FragmentElementController(this);	
		controller.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.i(TAG, "onActivityResult");
		controller.onActivityResult(requestCode, resultCode, data);
	}

	public EditText getCode() {
		return code;
	}

	public void setCode(EditText code) {
		this.code = code;
	}

	public EditText getTittle() {
		return tittle;
	}

	public void setTittle(EditText tittle) {
		this.tittle = tittle;
	}

	public EditText getAuthor() {
		return author;
	}

	public void setAuthor(EditText author) {
		this.author = author;
	}

	public Spinner getSpinnerElementType() {
		return spinnerElementType;
	}

	public void setSpinnerElementType(Spinner spinnerElementType) {
		this.spinnerElementType = spinnerElementType;
	}

	public EditText getEditTextYear() {
		return editTextYear;
	}

	public void setEditTextYear(EditText editTextYear) {
		this.editTextYear = editTextYear;
	}

	public RecipientEditTextView getEditTextCategories() {
		return editTextCategories;
	}

	public void setEditTextCategories(RecipientEditTextView editTextCategories) {
		this.editTextCategories = editTextCategories;
	}

	public EditText getEditTextDescription() {
		return editTextDescription;
	}

	public void setEditTextDescription(EditText editTextDescription) {
		this.editTextDescription = editTextDescription;
	}

	public EditText getEditTextLanguaje() {
		return editTextLanguaje;
	}

	public void setEditTextLanguaje(EditText editTextLanguaje) {
		this.editTextLanguaje = editTextLanguaje;
	}

	public EditText getEditTextIsbn() {
		return editTextIsbn;
	}

	public void setEditTextIsbn(EditText editTextIsbn) {
		this.editTextIsbn = editTextIsbn;
	}

	public EditText getEditTextPhysicalDescription() {
		return editTextPhysicalDescription;
	}

	public void setEditTextPhysicalDescription(EditText editTextPhysicalDescription) {
		this.editTextPhysicalDescription = editTextPhysicalDescription;
	}
	
	public void updateView() {
		model = ObjectBookModel.getInstance();
		ObjectBook element = model.getObject();
		if (element != null) {
			DataBinding.setDataValue(element, ElementFragment.this, getView().getContext());	
		}
		
	}
	
}
