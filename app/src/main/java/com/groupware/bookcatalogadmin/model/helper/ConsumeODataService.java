package com.groupware.bookcatalogadmin.model.helper;

import java.io.BufferedReader;
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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.json.JSONObject;

import android.util.Log;

import com.groupware.bookcatalogadmin.model.Author;
import com.groupware.bookcatalogadmin.model.Category;
import com.groupware.bookcatalogadmin.model.Image;
import com.groupware.bookcatalogadmin.model.ObjectBook;
import com.groupware.bookcatalogadmin.model.ObjectBookType;

public class ConsumeODataService {
		private final static String TAG = ConsumeODataService.class.getSimpleName();

		public static String createElement(ObjectBook element) {
			
			InputStream inputStream = null;
	        String result = "";
	        try {
	 
	            // 1. create HttpClient
	            HttpClient httpclient = new DefaultHttpClient();
	 
	            // 2. make POST request to the given URL
	            HttpPost httpPost = new HttpPost("http://54.187.205.50:8080/BookCatalogServices/ODataServlet.svc/ObjectBooks");
	            //HttpPost httpPost = new HttpPost("http://192.168.43.105:8080/BookCatalogServices/ODataServlet.svc/Elements");
	       	 
	            String json = "";
	 
	            // 3. build jsonObject
	            JSONObject jsonObject = new JSONObject();
	            jsonObject.accumulate("Code", element.getCode());
	            jsonObject.accumulate("Tittle", element.getTittle());
	            jsonObject.accumulate("Author", element.getAuthor());
	            jsonObject.accumulate("Notes", element.getNotes());
	            jsonObject.accumulate("Category", element.getCategory());
	            jsonObject.accumulate("ObjectBookType", element.getObjectBookType().getId());
	            jsonObject.accumulate("Publication", element.getPublication());
	            jsonObject.accumulate("LibraryStore", element.getLibraryStore() != null ? element.getLibraryStore().getId() : null);
	            jsonObject.accumulate("PhysicalDescription", element.getPhysicalDescription());
	            
	            List<JSONObject> listObjectCategory = new ArrayList<JSONObject>();
	            JSONObject jsonObjectCategory = new JSONObject();
	            jsonObjectCategory.accumulate("id", 1);
	            listObjectCategory.add(jsonObjectCategory);
	            jsonObject.accumulate("CategoryDetails", jsonObjectCategory);
	 
	            // 4. convert JSONObject to JSON to String
	            json = jsonObject.toString();
	            Log.i(TAG, "json - REQUEST: "+json);
	            // ** Alternative way to convert Person object to JSON string usin Jackson Lib 
	            // ObjectMapper mapper = new ObjectMapper();
	            // json = mapper.writeValueAsString(person); 
	 
	            // 5. set json to StringEntity
	            StringEntity se = new StringEntity(json);
	 
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
	            Log.d("InputStream", e.getLocalizedMessage());
	        }
	 
	        // 11. return result
	        return result;
			
		} 
		
		public static String createCategory(Category category) {
			
			InputStream inputStream = null;
	        String result = "";
	        try {
	 
	            // 1. create HttpClient
	            HttpClient httpclient = new DefaultHttpClient();
	 
	            // 2. make POST request to the given URL
	            HttpPost httpPost = new HttpPost("http://54.187.205.50:8080/BookCatalogServices/ODataServlet.svc/Categorys");
	            //HttpPost httpPost = new HttpPost("http://192.168.43.105:8080/BookCatalogServices/ODataServlet.svc/Elements");
	       	 
	            String json = "";
	 
	            // 3. build jsonObject
	            JSONObject jsonObject = new JSONObject();
	            jsonObject.accumulate("Name", category.getName());
	 
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
	            Log.d("InputStream", e.getLocalizedMessage());
	        }
	 
	        // 11. return result
	        return result;
			
		} 
		
		public static List<ObjectBook> getElements() {
			List<ObjectBook> listElement = new ArrayList<ObjectBook>();
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
			HttpConnectionParams.setSoTimeout(httpParams, 15000);
			
			HttpClient httpClient = new DefaultHttpClient(httpParams);
			
			StringBuilder filter = new StringBuilder();
			filter.append("?$select=Code,Tittle,Author,Category");
			
			Log.i(TAG, "filter: "+filter.toString());
			
			//HttpGet get = new HttpGet("http://54.187.205.50:8080/BookCatalogServices/ODataServlet.svc/ObjectBooks"+filter.toString()+"?$format=json");
			HttpGet get = new HttpGet("http://54.187.205.50:8080/BookCatalogServices/ODataServlet.svc/ObjectBooks?$format=json&"+filter.toString());
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
					String name = null;
					if (jp.nextToken() != JsonToken.START_ARRAY) {
						
					}
					
					
					ObjectBook element;
					//JsonToken token = jp.nextToken();
					while (jp.nextToken() != JsonToken.END_ARRAY) {
						
						String code = null;
						String tittle = null;
						String author = null;
						String notes = null;
						String category = null;
						String publication = null;
						String isbn = null;
						String languageContent = null;
						String physicalDescription = null;
						String objectBookType = null;
						String libraryStore = null;
						List<Image> imageList = null;
						while (jp.nextToken() != JsonToken.END_OBJECT) {
							current = jp.nextToken();
							name = jp.getCurrentName();
							
							if (name != null && current != JsonToken.END_ARRAY) {
								if ("Code".equals(name)) {
									code = jp.getText();
								} else if ("Tittle".equals(name)) {
									tittle = jp.getText();
								} else if ("Author".equals(name)) {
									author = jp.getText();
								} else if ("Notes".equals(name)) {
									notes = jp.getText();
								} else if ("Category".equals(name)) {
									category = jp.getText();
								} else if ("Publication".equals(name)) {
									publication = jp.getText();
								} else if ("LanguageContent".equals(name)) {
									languageContent = jp.getText();
								} else if ("Isbn".equals(name)) {
									isbn = jp.getText();
								} else if ("PhysicalDescription".equals(name)) {
									physicalDescription = jp.getText();
								} else if ("ObjectBookType".equals(name)) {
									objectBookType = jp.getText();
								} else if ("LibraryStore".equals(name)) {
									libraryStore = jp.getText();
								}
								
								
								if (current == JsonToken.START_OBJECT) {
									if ("__metadata".equals(name)) {
										while (jp.nextToken() != JsonToken.END_OBJECT) {
											current = jp.nextToken();
											name = jp.getCurrentName();
										}
									}
									
									else if ("ObjectBookTypeDetails".equals(name)) {
										while (jp.nextToken() != JsonToken.END_OBJECT) {
											current = jp.nextToken();
											name = jp.getCurrentName();
											if (current == JsonToken.START_OBJECT) {
												if ("__deferred".equals(name)) {
													while (jp.nextToken() != JsonToken.END_OBJECT) {
														current = jp.nextToken();
														name = jp.getCurrentName();
														
													}
												}
											}
											
										}
									} else if ("ImageDetails".equals(name)) {
										while (jp.nextToken() != JsonToken.END_OBJECT) {
											current = jp.nextToken();
											name = jp.getCurrentName();
											if (current == JsonToken.START_OBJECT) {
												if ("__deferred".equals(name)) {
													while (jp.nextToken() != JsonToken.END_OBJECT) {
														current = jp.nextToken();
														name = jp.getCurrentName();
														
														if ("uri".equals(name)) {
															String url = jp.getText();
															imageList = getImages(url, "$top=1&$orderby=Id&");
														}
														
													}
												}
											}
											
										}
									} else if ("LibraryStoreDetails".equals(name)) {
										while (jp.nextToken() != JsonToken.END_OBJECT) {
											current = jp.nextToken();
											name = jp.getCurrentName();
											if (current == JsonToken.START_OBJECT) {
												if ("__deferred".equals(name)) {
													while (jp.nextToken() != JsonToken.END_OBJECT) {
														current = jp.nextToken();
														name = jp.getCurrentName();
													}
												}
											}
											
										}
									} else if ("CategoryDetails".equals(name)) {
										while (jp.nextToken() != JsonToken.END_OBJECT) {
											current = jp.nextToken();
											name = jp.getCurrentName();
											if (current == JsonToken.START_OBJECT) {
												if ("__deferred".equals(name)) {
													while (jp.nextToken() != JsonToken.END_OBJECT) {
														current = jp.nextToken();
														name = jp.getCurrentName();
													}
												}
											}
											
										}
									} else if ("AuthorDetails".equals(name)) {
										while (jp.nextToken() != JsonToken.END_OBJECT) {
											current = jp.nextToken();
											name = jp.getCurrentName();
											if (current == JsonToken.START_OBJECT) {
												if ("__deferred".equals(name)) {
													while (jp.nextToken() != JsonToken.END_OBJECT) {
														current = jp.nextToken();
														name = jp.getCurrentName();
													}
												}
											}
											
										}
									}
									
								}
							}
							
							
						}
						
						if (name != null && current != JsonToken.END_ARRAY) {
							Log.i(TAG, "Tittle: "+tittle);
							element = new ObjectBook();
							element.setCode(code);
							element.setTittle(tittle);
							element.setAuthor(author);
							element.setCategory(category);
							element.setIsbn(isbn);
							element.setNotes(notes);
							element.setLanguageContent(languageContent);
							element.setPhysicalDescription(physicalDescription);
							element.setPublication(publication);
							
							element.setImageList(imageList);
							//element.setYearPublication(yearPublication != null ? Integer.parseInt(yearPublication):0);
							listElement.add(element);
						} else {
							break;
						}
						
					}

				}
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return listElement;
		}
		
		public static List<Image> getImages(String url, String addfilter) {
			List<Image> listImage = new ArrayList<Image>();
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
			HttpConnectionParams.setSoTimeout(httpParams, 15000);
			
			HttpClient httpClient = new DefaultHttpClient(httpParams);
			
			StringBuilder filter = new StringBuilder();
			filter.append(addfilter);
			
			Log.i(TAG, "filter: "+filter.toString());
			
			//HttpGet get = new HttpGet("http://54.187.205.50:8080/BookCatalogServices/ODataServlet.svc/ObjectBooks"+filter.toString()+"?$format=json");
			HttpGet get = new HttpGet(url+"/?"+filter+"$format=json");
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
					String name = null;
					if (jp.nextToken() != JsonToken.START_ARRAY) {
						
					}
					
					
					Image image;

					while (jp.nextToken() != JsonToken.END_ARRAY) {
						
						String id = null;
						String nameImage = null;
						String path = null;
						
						while (jp.nextToken() != JsonToken.END_OBJECT) {
							current = jp.nextToken();
							name = jp.getCurrentName();
							
							if (name != null && current != JsonToken.END_ARRAY) {
								if ("Id".equals(name)) {
									id = jp.getText();
								} else if ("Name".equals(name)) {
									nameImage = jp.getText();
								} else if ("Path".equals(name)) {
									path = jp.getText();
								} 
								
								if (current == JsonToken.START_OBJECT) {
									if ("__metadata".equals(name)) {
										while (jp.nextToken() != JsonToken.END_OBJECT) {
											current = jp.nextToken();
											name = jp.getCurrentName();
										}
									}
									
									else if ("ObjectBookDetails".equals(name)) {
										while (jp.nextToken() != JsonToken.END_OBJECT) {
											current = jp.nextToken();
											name = jp.getCurrentName();
											if (current == JsonToken.START_OBJECT) {
												if ("__deferred".equals(name)) {
													while (jp.nextToken() != JsonToken.END_OBJECT) {
														current = jp.nextToken();
														name = jp.getCurrentName();
													}
												}
											}
											
										}
									} 
									
								}
							}
							
							
						}
						
						if (name != null && current != JsonToken.END_ARRAY) {
							Log.i(TAG, "Nombre: "+nameImage);
							image = new Image();
							image.setId(id != null ? Integer.parseInt(id):0);
							image.setName(nameImage);
							image.setPath(path);
							listImage.add(image);
						} else {
							break;
						}
						
					}

				}
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return listImage;
		}
		
		public static List<Category> getCategories(String url, String addfilter) {
			List<Category> listCategory = new ArrayList<Category>();
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
			HttpConnectionParams.setSoTimeout(httpParams, 15000);
			
			HttpClient httpClient = new DefaultHttpClient(httpParams);
			
			StringBuilder filter = new StringBuilder();
			filter.append(addfilter);
			
			Log.i(TAG, "filter: "+filter.toString());
			
			//HttpGet get = new HttpGet("http://54.187.205.50:8080/BookCatalogServices/ODataServlet.svc/ObjectBooks"+filter.toString()+"?$format=json");
			HttpGet get = new HttpGet(url+"/?"+filter+"$format=json");
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
					String name = null;
					if (jp.nextToken() != JsonToken.START_ARRAY) {
						
					}
					
					
					Category category = null;

					while (jp.nextToken() != JsonToken.END_ARRAY) {
						
						String id = null;
						String nameCategory = null;
						
						while (jp.nextToken() != JsonToken.END_OBJECT) {
							current = jp.nextToken();
							name = jp.getCurrentName();
							
							if (name != null && current != JsonToken.END_ARRAY) {
								if ("Id".equals(name)) {
									id = jp.getText();
								} else if ("Name".equals(name)) {
									nameCategory = jp.getText();
								}  
								
								if (current == JsonToken.START_OBJECT) {
									if ("__metadata".equals(name)) {
										while (jp.nextToken() != JsonToken.END_OBJECT) {
											current = jp.nextToken();
											name = jp.getCurrentName();
										}
									}
									
									else if ("ObjectBookDetails".equals(name)) {
										while (jp.nextToken() != JsonToken.END_OBJECT) {
											current = jp.nextToken();
											name = jp.getCurrentName();
											if (current == JsonToken.START_OBJECT) {
												if ("__deferred".equals(name)) {
													while (jp.nextToken() != JsonToken.END_OBJECT) {
														current = jp.nextToken();
														name = jp.getCurrentName();
													}
												}
											}
											
										}
									} 
									
								}
							}
							
							
						}
						
						if (name != null && current != JsonToken.END_ARRAY) {
							Log.i(TAG, "Nombre: "+nameCategory);
							category = new Category();
							category.setId(id != null ? Integer.parseInt(id):0);
							category.setName(nameCategory);
							listCategory.add(category);
						} else {
							break;
						}
						
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
		
		public static List<Author> getAuthors(String url, String addfilter) {
			List<Author> listAuthor = new ArrayList<Author>();
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
			HttpConnectionParams.setSoTimeout(httpParams, 15000);
			
			HttpClient httpClient = new DefaultHttpClient(httpParams);
			
			StringBuilder filter = new StringBuilder();
			filter.append(addfilter);
			
			Log.i(TAG, "filter: "+filter.toString());
			
			//HttpGet get = new HttpGet("http://54.187.205.50:8080/BookCatalogServices/ODataServlet.svc/ObjectBooks"+filter.toString()+"?$format=json");
			HttpGet get = new HttpGet(url+"/?"+filter+"$format=json");
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
					String name = null;
					if (jp.nextToken() != JsonToken.START_ARRAY) {
						
					}
					
					
					Author author = null;

					while (jp.nextToken() != JsonToken.END_ARRAY) {
						
						String id = null;
						String nameAuthor = null;
						
						while (jp.nextToken() != JsonToken.END_OBJECT) {
							current = jp.nextToken();
							name = jp.getCurrentName();
							
							if (name != null && current != JsonToken.END_ARRAY) {
								if ("Id".equals(name)) {
									id = jp.getText();
								} else if ("Name".equals(name)) {
									nameAuthor = jp.getText();
								}  
								
								if (current == JsonToken.START_OBJECT) {
									if ("__metadata".equals(name)) {
										while (jp.nextToken() != JsonToken.END_OBJECT) {
											current = jp.nextToken();
											name = jp.getCurrentName();
										}
									}
									
									else if ("ObjectBookDetails".equals(name)) {
										while (jp.nextToken() != JsonToken.END_OBJECT) {
											current = jp.nextToken();
											name = jp.getCurrentName();
											if (current == JsonToken.START_OBJECT) {
												if ("__deferred".equals(name)) {
													while (jp.nextToken() != JsonToken.END_OBJECT) {
														current = jp.nextToken();
														name = jp.getCurrentName();
													}
												}
											}
											
										}
									} 
									
								}
							}
							
							
						}
						
						if (name != null && current != JsonToken.END_ARRAY) {
							Log.i(TAG, "Nombre: "+nameAuthor);
							author = new Author();
							author.setId(id != null ? Integer.parseInt(id):0);
							author.setName(nameAuthor);
							listAuthor.add(author);
						} else {
							break;
						}
						
					}

				}
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return listAuthor;
		}
		
		public static ObjectBook getElement(String code) {
			ObjectBook objectBook = null;
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet get = new HttpGet("http://54.187.205.50:8080/BookCatalogServices/ODataServlet.svc/ObjectBooks('"+code+"')/?$format=json");
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
					String tittle = null;
					String author = null;
					String notes = null;
					String category = null;
					String publication = null;
					String isbn = null;
					String languageContent = null;
					String physicalDescription = null;
					objectBook = new ObjectBook();
					List<Image> imageList = null;
					ObjectBookType objectBookType = null;
					List<Category> categoryList = null;
					List<Author> authorList = null;
					while (jp.nextToken() != JsonToken.END_OBJECT) {
						current = jp.nextToken();
						name = jp.getCurrentName();
						
						if ("Code".equals(name)) {
							code = jp.getText();
						} else if ("Tittle".equals(name)) {
							tittle = jp.getText();
						} else if ("Author".equals(name)) {
							author = jp.getText();
						} else if ("Notes".equals(name)) {
							notes = jp.getText();
						} else if ("Category".equals(name)) {
							category = jp.getText();
						} else if ("Publication".equals(name)) {
							publication = jp.getText();
						} else if ("LanguageContent".equals(name)) {
							languageContent = jp.getText();
						} else if ("Isbn".equals(name)) {
							isbn = jp.getText();
						} else if ("PhysicalDescription".equals(name)) {
							physicalDescription = jp.getText();
						} 
						
						
						if (current == JsonToken.START_OBJECT) {
							if ("__metadata".equals(name)) {
								while (jp.nextToken() != JsonToken.END_OBJECT) {
									current = jp.nextToken();
									name = jp.getCurrentName();
								}
							}
							
							else if ("ObjectBookTypeDetails".equals(name)) {
								while (jp.nextToken() != JsonToken.END_OBJECT) {
									current = jp.nextToken();
									name = jp.getCurrentName();
									if (current == JsonToken.START_OBJECT) {
										if ("__deferred".equals(name)) {
											while (jp.nextToken() != JsonToken.END_OBJECT) {
												current = jp.nextToken();
												name = jp.getCurrentName();
												
												if ("uri".equals(name)) {
													String url = jp.getText();
													objectBookType = getObjectBookType(url);
												}
											}
										}
									}
									
								}
							} else if ("ImageDetails".equals(name)) {
								while (jp.nextToken() != JsonToken.END_OBJECT) {
									current = jp.nextToken();
									name = jp.getCurrentName();
									if (current == JsonToken.START_OBJECT) {
										if ("__deferred".equals(name)) {
											while (jp.nextToken() != JsonToken.END_OBJECT) {
												current = jp.nextToken();
												name = jp.getCurrentName();
												
												if ("uri".equals(name)) {
													String url = jp.getText();
													imageList = getImages(url, "");
												}
												
											}
										}
									}
									
								}
							} else if ("LibraryStoreDetails".equals(name)) {
								while (jp.nextToken() != JsonToken.END_OBJECT) {
									current = jp.nextToken();
									name = jp.getCurrentName();
									if (current == JsonToken.START_OBJECT) {
										if ("__deferred".equals(name)) {
											while (jp.nextToken() != JsonToken.END_OBJECT) {
												current = jp.nextToken();
												name = jp.getCurrentName();
											}
										}
									}
									
								}
							} else if ("CategoryDetails".equals(name)) {
								while (jp.nextToken() != JsonToken.END_OBJECT) {
									current = jp.nextToken();
									name = jp.getCurrentName();
									if (current == JsonToken.START_OBJECT) {
										if ("__deferred".equals(name)) {
											while (jp.nextToken() != JsonToken.END_OBJECT) {
												current = jp.nextToken();
												name = jp.getCurrentName();
												
												if ("uri".equals(name)) {
													String url = jp.getText();
													categoryList = getCategories(url, "");
												}
											}
										}
									}
									
								}
							} else if ("AuthorDetails".equals(name)) {
								while (jp.nextToken() != JsonToken.END_OBJECT) {
									current = jp.nextToken();
									name = jp.getCurrentName();
									if (current == JsonToken.START_OBJECT) {
										if ("__deferred".equals(name)) {
											while (jp.nextToken() != JsonToken.END_OBJECT) {
												current = jp.nextToken();
												name = jp.getCurrentName();
												if ("uri".equals(name)) {
													String url = jp.getText();
													authorList = getAuthors(url, "");
												}
												
											}
										}
									}
									
								}
							}
								
						}
					}
					Log.i(TAG, "Tittle: "+tittle);
					objectBook.setCode(code);
					objectBook.setTittle(tittle);
					objectBook.setAuthor(author);
					objectBook.setCategory(category);
					objectBook.setIsbn(isbn);
					objectBook.setNotes(notes);
					objectBook.setLanguageContent(languageContent);
					objectBook.setPhysicalDescription(physicalDescription);
					objectBook.setPublication(publication);	
					
					objectBook.setImageList(imageList);
					objectBook.setObjectBookType(objectBookType);
					objectBook.setCategoryList(categoryList);
					objectBook.setAuthorList(authorList);

				}
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return objectBook;
			
		}
		
		public static ObjectBookType getObjectBookType(String url) {
			ObjectBookType objectBookType = null;
			HttpClient httpClient = new DefaultHttpClient();
			StringBuilder filter = new StringBuilder();
			//HttpGet get = new HttpGet("http://54.187.205.50:8080/BookCatalogServices/ODataServlet.svc/ObjectBooks('"+code+"')/?$format=json");
			HttpGet get = new HttpGet(url+"/?"+filter+"$format=json");
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
					String id = null;
					String nameType = null;
					objectBookType = new ObjectBookType();
					while (jp.nextToken() != JsonToken.END_OBJECT) {
						current = jp.nextToken();
						name = jp.getCurrentName();
						
						if ("Name".equals(name)) {
							nameType = jp.getText();
						} else if ("Id".equals(name)) {
							id = jp.getText();
						} 
						
						
						if (current == JsonToken.START_OBJECT) {
							if ("__metadata".equals(name)) {
								while (jp.nextToken() != JsonToken.END_OBJECT) {
									current = jp.nextToken();
									name = jp.getCurrentName();
								}
							}
							
							else if ("ObjectBookDetails".equals(name)) {
								while (jp.nextToken() != JsonToken.END_OBJECT) {
									current = jp.nextToken();
									name = jp.getCurrentName();
									if (current == JsonToken.START_OBJECT) {
										if ("__deferred".equals(name)) {
											while (jp.nextToken() != JsonToken.END_OBJECT) {
												current = jp.nextToken();
												name = jp.getCurrentName();
											}
										}
									}
									
								}
							} 
								
						}
					}
					Log.i(TAG, "Name: "+nameType);
					objectBookType.setId(Integer.valueOf(id));
					objectBookType.setName(nameType);



				}
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return objectBookType;
			
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
