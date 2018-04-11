package com.vv.test.automation.api;

import static io.restassured.RestAssured.given;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.KeyStore;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.vv.test.automation.logs.ExtentLogs;

import io.restassured.http.Headers;
import io.restassured.response.Response;

/**
 * The Class RestAccelerators.
 */
public class RestAccelerators {
	
	private static final Logger LOG = Logger.getLogger(RestAccelerators.class);
	private ExtentLogs extentLogs = new ExtentLogs();
	/**
	 * Gets the json response string.
	 *
	 * @param response the response
	 * @return the json response string
	 */
	public String getJsonResponseString(HttpResponse response)
	{
		String ResponseString = null;
		try{
			StringBuilder JsonResponse = new StringBuilder();   
	        BufferedReader reader;
			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = null;
			while ((line = reader.readLine()) != null)
			{
				JsonResponse.append(line);
			}
			ResponseString = JsonResponse.toString();
		}
		catch (IOException e) {
			LOG.error("Exception while get JSON String from response ::"+e.getMessage());
		}
		
		return ResponseString;		
	}
	

	/**
	 * Gets the json string.
	 *
	 * @param filepath the filepath
	 * @param properties the properties
	 * @return the json string
	 */
	public String getJsonString(String filepath,Map properties)
	{
		
		String Filedata = null;
		try {
			Filedata = IOUtils.toString(new FileInputStream(new File(filepath)), "UTF-8");
			Iterator it = properties.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        String key = (String) pair.getKey();
		        String value = (String) pair.getValue();
		        String[] values = value.split("::");
		        for(int i=0;i<values.length;i++)
		        {
		        	Filedata = Filedata.replaceFirst(key, values[i]);
		        }	       
		        it.remove(); // avoids a ConcurrentModificationException
		    }
		}	    
	    catch (FileNotFoundException e) {
	    	LOG.error("Exception while creating JSON request ::"+e.getMessage());
		} catch (IOException e) {
			LOG.error("Exception while creating JSON request ::"+e.getMessage());
		}
		return Filedata;
	}
	
	/**
	 * Verify value.
	 *
	 * @param response the response
	 * @param element the element
	 * @param expectedvalue the expectedvalue
	 * @return true, if successful
	 */
	public boolean verifyValue(String response,String element,String expectedvalue)
	{
		boolean status = false;
	try {
			ObjectMapper a1 = new ObjectMapper();
	        Object  jsonObj = a1.readValue(response, Object.class);
			Object val =PropertyUtils.getProperty(jsonObj, element);
			String Value = (String) val;
			if(Value.equalsIgnoreCase(expectedvalue))
			{
				status = true;
			}
		} 
        catch (IllegalStateException | IOException e) {
        	LOG.error("Exception while verifying value in response ::"+e.getMessage());
 		} catch (IllegalAccessException e) {
 			LOG.error("Exception while verifying value in response ::"+e.getMessage());
		} catch (InvocationTargetException e) {
			LOG.error("Exception while verifying value in response ::"+e.getMessage());
		} catch (NoSuchMethodException e) {
			LOG.error("Exception while verifying value in response ::"+e.getMessage());
		}
	return status;
	}
	
	/**
	 * Htttp post json.
	 *
	 * @param EndpointURI the endpoint uri
	 * @param Request the request
	 * @return the http response
	 */
	public HttpResponse htttpPostJSON(String EndpointURI, String Request)
	{
		HttpResponse response = null;
		try {
		HttpClient httpClient = getNewHttpClient();
		HttpPost request = new HttpPost(EndpointURI);
		StringEntity RequestEntity;		
		RequestEntity = new StringEntity(Request);
		request.addHeader("content-type", "application/json");
        request.setEntity(RequestEntity);
        response = httpClient.execute(request);
        if(response.getStatusLine().getStatusCode()==200) {
			extentLogs.pass("Post HttpResponse", "Response status Code: "+response.getStatusLine().getStatusCode());	
			extentLogs.pass("Post HttpResponse", "Response : "+response.toString());
		}else {
			extentLogs.fail("Post HttpResponse", "Response status Code: "+response.getStatusLine().getStatusCode());	
			extentLogs.fail("Post HttpResponse", "Response : "+response.toString());
		}
		}
        catch (UnsupportedEncodingException e) {
        	LOG.error("Exception while posting the http Post ::"+e.getMessage());
        	extentLogs.fail("htttpPostJSON Exception", e.getMessage());
		} catch (ClientProtocolException e) {
			LOG.error("Exception while posting the http Post ::"+e.getMessage());
			extentLogs.fail("htttpPostJSON Exception", e.getMessage());
		} catch (IOException e) {
			LOG.error("Exception while posting the http Post ::"+e.getMessage());
			extentLogs.fail("htttpPostJSON Exception", e.getMessage());
		}
		return response;
	}
	
	/**
	 * Htttp delete json.
	 *
	 * @param EndpointURI the endpoint uri
	 * @param Request the request
	 * @return the http response
	 */
	public HttpResponse htttpDeleteJSON(String EndpointURI, String Request)
	{
		HttpResponse response = null;
		try {
		HttpClient httpClient = getNewHttpClient();
		HttpDelete request = new HttpDelete(EndpointURI);
		StringEntity RequestEntity;		
		RequestEntity = new StringEntity(Request);
		request.addHeader("content-type", "application/json");
        //request.setEntity(RequestEntity);
        response = httpClient.execute(request);
		}
        catch (UnsupportedEncodingException e) {
        	LOG.error("Exception while posting the http Delete ::"+e.getMessage());
		} catch (ClientProtocolException e) {
			LOG.error("Exception while posting the http Delete ::"+e.getMessage());
		} catch (IOException e) {
			LOG.error("Exception while posting the http Delete ::"+e.getMessage());
		}
		return response;
	}
	
	/**
	 * Htttp trace json.
	 *
	 * @param EndpointURI the endpoint uri
	 * @param Request the request
	 * @return the http response
	 */
	public HttpResponse htttpTraceJSON(String EndpointURI, String Request)
	{
		HttpResponse response = null;
		try {
		HttpClient httpClient = getNewHttpClient();
		HttpTrace request = new HttpTrace(EndpointURI);
		StringEntity RequestEntity = new StringEntity(Request);
		request.addHeader("content-type", "application/json");
        //request.setEntity(RequestEntity);
        response = httpClient.execute(request);
		}
        catch (UnsupportedEncodingException e) {
        	LOG.error("Exception while posting the http Trace ::"+e.getMessage());
		} catch (ClientProtocolException e) {
			LOG.error("Exception while posting the http Trace ::"+e.getMessage());
		} catch (IOException e) {
			LOG.error("Exception while posting the http Trace ::"+e.getMessage());
		}
		return response;
	}
	/**
	 * Htttp get.
	 *
	 * @param EndpointURI the endpoint uri
	 * @return the http response
	 */
	public HttpResponse htttpGet(String EndpointURI)
	{
		HttpResponse response = null;
		try {
		HttpClient httpClient = getNewHttpClient();
		HttpGet request = new HttpGet(EndpointURI);	
		request.addHeader("content-type", "application/json");
        response = httpClient.execute(request);
		}
        catch (UnsupportedEncodingException e) {
        	LOG.error("Exception while sending the http Get request ::"+e.getMessage());
		} catch (ClientProtocolException e) {
			LOG.error("Exception while sending the http Get request ::"+e.getMessage());
		} catch (IOException e) {
			LOG.error("Exception while sending the http Get request ::"+e.getMessage());
		}
		return response;
	}
	
	/**
	 * Htttp put json.
	 *
	 * @param EndpointURI the endpoint uri
	 * @param Request the request
	 * @return the http response
	 */
	public HttpResponse htttpPutJSON(String EndpointURI, String Request)
	{
		HttpResponse response = null;
		try {
		HttpClient httpClient = getNewHttpClient();
		HttpPut request = new HttpPut(EndpointURI);
		StringEntity RequestEntity;		
		RequestEntity = new StringEntity(Request);
		request.addHeader("content-type", "application/json");
        request.setEntity(RequestEntity);
        response = httpClient.execute(request);
		}
        catch (UnsupportedEncodingException e) {
        	LOG.error("Exception while sending the http Put request ::"+e.getMessage());
		} catch (ClientProtocolException e) {
			LOG.error("Exception while sending the http Put request ::"+e.getMessage());
		} catch (IOException e) {
			LOG.error("Exception while sending the http Put request ::"+e.getMessage());
		}
		return response;
	}
	
	/**
	 * Checks if is success status.
	 *
	 * @param response the response
	 * @return true, if is success status
	 */
	public boolean isSuccessStatus(HttpResponse response)
	{
		boolean status = false;
		if(response.getStatusLine().getStatusCode()==200)
		{
			status= true;
		}
		return status;
	}
	
	/**
	 * Validate json schema.
	 *
	 * @param JsonSchemaFile the json schema file
	 * @param JsonFile the json file
	 * @return true, if successful
	 * @throws ProcessingException the processing exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public boolean validateJSONSchema(File JsonSchemaFile,File JsonFile) throws ProcessingException, IOException
	{
		boolean status = false;	    	
	    if (ValidationUtils.isJsonValid(JsonSchemaFile, JsonFile)){
	    	status = true;
	    }
	    return status;
	}
	
	/**
	 * Validate json schema.
	 *
	 * @param JsonSchema the json schema
	 * @param JSon the j son
	 * @return true, if successful
	 * @throws ProcessingException the processing exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public boolean validateJSONSchema(String JsonSchema,String JSon) throws ProcessingException, IOException
	{  	
		boolean status = false;	 
	    if (ValidationUtils.isJsonValid(JsonSchema, JSon)){
	    	status = true;
	    }
	    return status;
	}

	/**
	 * Gets the new http client.
	 *
	 * @return the new http client
	 */
	@SuppressWarnings("deprecation")
	public static HttpClient getNewHttpClient() {
	    try {
	        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	        trustStore.load(null, null);

	        MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
	        sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

	        HttpParams params = new BasicHttpParams();
	        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

	        SchemeRegistry registry = new SchemeRegistry();
	        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	        registry.register(new Scheme("https", sf, 443));

	        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

	        return new DefaultHttpClient(ccm, params);
	    } catch (Exception e) {
	        return new DefaultHttpClient();
	    }
	}
	
	public Response getRestResponse(String url) {
		
		Response response = null;
		try {
			response = given().when().get(url);
			if(response.statusCode()==200) {
				extentLogs.pass("getRestResponse - "+url, "Response status Code: "+response.statusCode());				
			}else {
				extentLogs.fail("getRestResponse - "+url, "Response status Code: "+response.statusCode());					
			}
		}catch(Exception e){
			extentLogs.error("getResponse Exception : "+url, e.getMessage());
		}		
		return response;
		
	}
	
	public Response getRestResponseWithBody(Headers header,String requestData, String URL) {
		Response response=null;
		try {
			response = given().log().all().contentType("application/json").headers(header).body(requestData)
					.when().get(URL);;
			if(response.statusCode()==200) {
				extentLogs.pass("getRestResponseWithBody : "+URL, "Response status Code: "+response.statusCode());	
				extentLogs.pass("getRestResponseWithBody : "+URL, "Response : "+response.asString());
			}else {
				extentLogs.fail("getRestResponseWithBody : "+URL, "Response status Code: "+response.statusCode());	
				extentLogs.fail("getRestResponseWithBody : "+URL, "Response : "+response.asString());
			}
		}catch(Exception e){
			extentLogs.error("getRestResponseWithBody Exception : "+URL, e.getMessage());
		}		
		return response;
		
	}
	
	public Response getRestResponseWithHeader(Headers header, String URL) {
		Response response=null;
		try {
			response = given().log().all().contentType("application/json").headers(header)
					.when().get(URL);;
			if(response.statusCode()==200) {
				extentLogs.pass("getRestResponseWithHeader : "+URL, "Response status Code: "+response.statusCode());	
				//extentLogs.pass("getRestResponseWithHeader : "+URL, "Response : "+response.asString());
			}else {
				extentLogs.fail("getRestResponseWithHeader : "+URL, "Response status Code: "+response.statusCode());	
				//extentLogs.fail("getRestResponseWithHeader : "+URL, "Response : "+response.asString());
			}
		}catch(Exception e){
			extentLogs.error("getRestResponseWithHeader Exception : "+URL, e.getMessage());
		}		
		return response;
	}
	
	public Response postRestResponseWithBody(Headers header,String requestData, String URL) {
		Response response=null;
		try {
			response = given().log().all().contentType("application/json").headers(header).body(requestData)
					.when().post(URL).then().extract().response();;
			if(response.statusCode()==200) {
				extentLogs.pass("postRestResponseWithBody : "+URL, "Response status Code: "+response.statusCode());	
				//extentLogs.pass("postRestResponseWithBody : "+URL, "Response : "+response.asString());
			}else {
				extentLogs.fail("postRestResponseWithBody : "+URL, "Response status Code: "+response.statusCode());	
				//extentLogs.fail("postRestResponseWithBody : "+URL, "Response : "+response.asString());
			}
		}catch(Exception e){
			extentLogs.error("postRestResponseWithBody Exception : "+URL, e.getMessage());
		}		
		return response;
		
	}
	
	//for Basic authentication. 
	
	//ValidatableResponse restAssuredResponse = given().auth().basic("pcwebuser", "Royal2016").when().get("https://wmworldtst.cruises.princess.com/rest/api/v1/bookings?ProductCompanyCode=HA&BookingNumber=7LVDWJ&LastName=MURETICH").then().statusCode(200);
	//System.out.println(restAssuredResponse.extract().asString());
	
	public String getJsonObjectString(String jsonString, String jsonItem) {
		
		JSONObject jsonObj = new JSONObject(jsonString);
		String jsonObject = jsonObj.getJSONObject(jsonItem).toString();
		return jsonObject;
		
	}
	
	public String getJsonString(String jsonString, String key) {
		
		JSONObject jsonObj = new JSONObject(jsonString);
		String jsonStringData = jsonObj.getString(key);
		return jsonStringData;
		
	}

}
