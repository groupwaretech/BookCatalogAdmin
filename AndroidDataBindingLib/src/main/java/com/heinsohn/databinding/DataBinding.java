package com.heinsohn.databinding;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.internal.widget.TintEditText;
import android.text.TextUtils;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.heinsohn.databinding.annotation.Property;
import com.heinsohn.databinding.annotation.Required;

public class DataBinding {
	
	/**
	 * Método que mapea el estado de la vista y sus compoenetes con el modelo
	 * 
	 * @param Class<T> classObject
	 * @param Object view
	 * @param Context context
	 * @return T
	 */
	public static <T> T getDataValue(Class<T> classObject, Object view, Context context) {
		Field[] fields = view.getClass().getDeclaredFields();
		Object data = null;
		boolean isValidated = true;
		try {
			data = classObject.newInstance();
			for (Field field : fields) {
				if (field.isAnnotationPresent(Property.class) || field.isAnnotationPresent(Required.class)) {
					//Obtener el estado de la propiedad de la vista (field)
					StringBuilder getNameMethod = new StringBuilder();
					StringBuilder fieldName = new StringBuilder(field.getName());
					getNameMethod.append("get");
					getNameMethod.append(fieldName.substring(0, 1).toUpperCase());
					getNameMethod.append(fieldName.substring(1, fieldName.length()));
					Method getMethod = view.getClass().getMethod(getNameMethod.toString(), null);
					Object property =  getMethod.invoke(view, null);
					
					if (property != null) {
						Class<?> propertyClass = property.getClass();
						Object value = null;
						if (propertyClass == EditText.class) {
							EditText editText = (EditText) property;
							value = editText.getText().toString();
							
							if (TextUtils.isEmpty(editText.getText().toString()) && field.isAnnotationPresent(Required.class)) {
								isValidated = false;
								editText.setError(field.getAnnotation(Required.class).errorMessage());
								editText.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_text_validate));
								
							} else {
								editText.setError(null);
							}
							
						} if (propertyClass == TintEditText.class) {
							TintEditText tintEditText = (TintEditText) property;
							value = tintEditText.getText().toString();
							
							if (TextUtils.isEmpty(tintEditText.getText().toString()) && field.isAnnotationPresent(Required.class)) {
								isValidated = false;
								tintEditText.setError(field.getAnnotation(Required.class).errorMessage());
								tintEditText.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_text_validate));
								
							} else {
								tintEditText.setError(null);
							}
							
						} else if (propertyClass == Spinner.class) {
							Spinner spinner = (Spinner) property;
							value = spinner.getAdapter().getItem(spinner.getSelectedItemPosition());
						}
						
						if (field.isAnnotationPresent(Property.class) && isValidated) {
							Property propertyAnnotation = field.getAnnotation(Property.class);
							if (propertyAnnotation.dataClass() == classObject) {
								StringBuilder dataPropertyName = new StringBuilder(propertyAnnotation.dataPropertyName().length() > 0 ? propertyAnnotation.dataPropertyName() : fieldName);
								StringBuilder setNameMethod = new StringBuilder();
								setNameMethod.append("set");
								setNameMethod.append(dataPropertyName.substring(0, 1).toUpperCase());
								setNameMethod.append(dataPropertyName.substring(1, dataPropertyName.length()));
								Method setMethod = data.getClass().getMethod(setNameMethod.toString(), propertyAnnotation.dataPropertyType());
								setMethod.invoke(data, value);
							}
						}
					}
				}
			}
		
			if (!isValidated) {
				// Get instance of Vibrator from current Context
				Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
				// Vibrate for 300 milliseconds
				vibrator.vibrate(300);
				data = null;
			} 
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return data != null ? classObject.cast(data) : null;
	}
	
	public static <T> void setDataValue(T data, Object view, Context context) {
		Field[] fields = view.getClass().getDeclaredFields();
		try {
			for (Field field : fields) {
				if (field.isAnnotationPresent(Property.class)) {
					//Obtener el estado de la propiedad del modelo
					Property propertyAnnotation = field.getAnnotation(Property.class);
					StringBuilder getNameMethodData = new StringBuilder();
					StringBuilder fieldNameData = new StringBuilder(propertyAnnotation.dataPropertyName().length() > 0 ? propertyAnnotation.dataPropertyName() : field.getName());
					getNameMethodData.append("get");
					getNameMethodData.append(fieldNameData.substring(0, 1).toUpperCase());
					getNameMethodData.append(fieldNameData.substring(1, fieldNameData.length()));
					Method getMethodData = data.getClass().getMethod(getNameMethodData.toString(), null);
					Object dataProperty =  getMethodData.invoke(data, null);
					
					//Obtener el estado de la propiedad de la vista
					StringBuilder getNameMethodView = new StringBuilder();
					StringBuilder fieldNameView = new StringBuilder(field.getName());
					getNameMethodView.append("get");
					getNameMethodView.append(fieldNameView.substring(0, 1).toUpperCase());
					getNameMethodView.append(fieldNameView.substring(1, fieldNameView.length()));
					Method getMethodView = view.getClass().getMethod(getNameMethodView.toString(), null);
					Object viewProperty =  getMethodView.invoke(view, null);
					
					if (viewProperty != null) {
						Class<?> propertyClass = viewProperty.getClass();

						if (propertyClass == EditText.class) {
							EditText editText = (EditText) viewProperty;
							editText.setText(dataProperty.toString());
							
						} else if (propertyClass == TextView.class) {
							TextView textView = (TextView) viewProperty;
							textView.setText(dataProperty.toString());
							
						} else if (propertyClass == Spinner.class) {
							Spinner spinner = (Spinner) viewProperty;
							for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
								if (spinner.getAdapter().getItem(i).equals(dataProperty)) {
									spinner.setSelection(i);
								}
							}
							
						}
						
					}
				}
			}
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}

}
