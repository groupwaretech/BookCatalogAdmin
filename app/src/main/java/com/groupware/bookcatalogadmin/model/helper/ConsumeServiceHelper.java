package com.groupware.bookcatalogadmin.model.helper;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.json.JSONObject;

import com.groupware.bookcatalogadmin.model.Author;
import com.groupware.bookcatalogadmin.model.Category;
import com.groupware.bookcatalogadmin.model.ObjectBook;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;


public class ConsumeServiceHelper {
	
	private final static String TAG = ConsumeServiceHelper.class.getSimpleName();
	
	
	public static String uploadPhoto(Bitmap bitmap, String codeElement) {
		Log.i(TAG, "uploadPhoto");
		String sResponse = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();  
	        HttpContext localContext = new BasicHttpContext();
	        
	        //HttpPost httpPost = new HttpPost("http://192.168.43.105:8080/BookCatalogServices/UploadDataServlet");  
	        HttpPost httpPost = new HttpPost("http://54.187.205.50:8080/BookCatalogServices/UploadDataServlet");  
	        
	        String BOUNDARY= "--bookboundry--";
	        //httpPost.setHeader("Content-Type", "multipart/form-data; boundary="+BOUNDARY);
	        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);  
	        Bitmap bmpCompressed = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);  
	        ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
	        
	        bmpCompressed.compress(CompressFormat.JPEG, 100, bos);  
	        byte[] data = bos.toByteArray();  
	        Log.i(TAG, "codeElement: "+codeElement);
	        entity.addPart("code", new StringBody(codeElement)); 
	        entity.addPart("name", new StringBody(codeElement+".jpg"));
	        entity.addPart("file", new ByteArrayBody(data, codeElement));  
	        httpPost.setEntity(entity);
       	 	HttpResponse response = httpClient.execute(httpPost, localContext);  
	        BufferedReader reader = new BufferedReader(new InputStreamReader( response.getEntity().getContent(), "UTF-8"));  
	        sResponse = reader.readLine(); 
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		Log.i(TAG, "sResponse: "+sResponse);
		return sResponse;
	} 
	
	public static List<Category> getCategories() {
		List<Category> listCategory = new ArrayList<Category>();
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
		HttpConnectionParams.setSoTimeout(httpParams, 15000);
		
		HttpClient httpClient = new DefaultHttpClient(httpParams);
		
		StringBuilder filter = new StringBuilder();
		
		
		Log.i(TAG, "filter: "+filter.toString());
		
		HttpGet get = new HttpGet("http://54.187.205.50:8080/BookCatalogServices/bookcatalog/BookCatalogService/getCategories");
		get.addHeader("Accept", "Application/json");
		try {
			HttpResponse response = httpClient.execute(get);
			
			JsonFactory jsonFactory = new JsonFactory();
			JsonToken current = null;
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				final JsonParser jp = jsonFactory.createJsonParser(response.getEntity().getContent());

				jp.nextToken(); // skip ("START_OBJECT(d) expected");
				if (jp.nextToken() != JsonToken.START_OBJECT) {
					
				}
				String name;
				if (jp.nextToken() != JsonToken.START_ARRAY) {
					
				}
				
				String id           = null;
				String nameCategory = null;

				
				Category category;
				
				while (jp.nextToken() != JsonToken.END_ARRAY) {
					while (jp.nextToken() != JsonToken.END_OBJECT) {
						current = jp.nextToken();
						name = jp.getCurrentName();
						
						if ("id".equals(name)) {
							id = jp.getText();
						} else if ("name".equals(name)) {
							nameCategory = jp.getText();
						} 
					}
						
						
					Log.i(TAG, "name: "+nameCategory);
					category = new Category();
					category.setId(id != null ? Integer.parseInt(id): null);
					category.setName(nameCategory);
					listCategory.add(category);
				}

			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listCategory;
	}
	
	public static String createElement(ObjectBook element) {
		
		InputStream inputStream = null;
        String result = "";
        try {
 
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost("http://54.187.205.50:8080/BookCatalogServices/bookcatalog/BookCatalogService/setObjectBook");
       	 
            String json = "";
 
            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("code", element.getCode());
            jsonObject.accumulate("tittle", element.getTittle());
            jsonObject.accumulate("author", element.getAuthor());
            jsonObject.accumulate("notes", element.getNotes());
            jsonObject.accumulate("category", element.getCategory());
            jsonObject.accumulate("publication", element.getPublication());
            jsonObject.accumulate("physicalDescription", element.getPhysicalDescription());
            jsonObject.accumulate("languageContent", element.getLanguageContent());
            jsonObject.accumulate("isbn", element.getIsbn());
            
            if (element.getLibraryStore() != null) {
            	JSONObject jsonObjectLibraryStore = new JSONObject();
                jsonObjectLibraryStore.accumulate("id", element.getLibraryStore().getId());
                jsonObject.accumulate("libraryStore",  jsonObjectLibraryStore);
            }
            
            if (element.getObjectBookType() != null) {
            	JSONObject jsonObjectObjectBookType = new JSONObject();
                jsonObjectObjectBookType.accumulate("id", element.getObjectBookType().getId());
                jsonObject.accumulate("objectBookType",  jsonObjectObjectBookType);
            }
            
            if ( element.getCategoryList() != null && element.getCategoryList().size() > 0) {
            	List<JSONObject> listObjectCategory = new ArrayList<JSONObject>();
                JSONObject jsonObjectCategory = new JSONObject();
                for (Category category : element.getCategoryList()) {
                	jsonObjectCategory.accumulate("id", category.getId());
                }
                
                listObjectCategory.add(jsonObjectCategory);
                jsonObject.accumulate("categories", listObjectCategory);
            }
            
            if ( element.getAuthorList() != null && element.getAuthorList().size() > 0) {
            	List<JSONObject> listObjectAuthor = new ArrayList<JSONObject>();
                JSONObject jsonObjectAuthor = new JSONObject();
                for (Author author : element.getAuthorList()) {
                	jsonObjectAuthor.accumulate("id", author.getId());
                }
                listObjectAuthor.add(jsonObjectAuthor);
                jsonObject.accumulate("authors", listObjectAuthor);
            }
            
            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
            Log.i(TAG, "json - REQUEST: "+json);
            // ** Alternative way to convert Person object to JSON string usin Jackson Lib 
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person); 
 
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json, HTTP.UTF_8);
 
            // 6. set httpPost Entity
            httpPost.setEntity(se);
 
            // 7. Set some headers to inform server about the type of the content   
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json; charset=utf-8");
 
            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);
            Log.i(TAG, "httpResponse - StatusCode: "+httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            	// 9. receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();
     
                // 10. convert inputstream to string
                if(inputStream != null)
                    result = convertInputStreamToString(inputStream);
                else
                    result = "Did not work!";
            	
            } else if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
            	result = "OK";
            } else if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
            	// 9. receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();
     
                // 10. convert inputstream to string
                if(inputStream != null)
                    result = convertInputStreamToString(inputStream);
                else
                    result = "Did not work!";
            } else {
            	result = "ERROR";
            }
 
            
 
        } catch (Exception e) {
        	result = e.getMessage();
            //Log.d("InputStream", e.getLocalizedMessage());
        }
 
        // 11. return result
        return result;
		
	} 
	
	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
 
    }

}
