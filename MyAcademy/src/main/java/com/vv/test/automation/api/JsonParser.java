package com.vv.test.automation.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The Class JsonParser.
 */
public class JsonParser {
	
	/**
	 * Gets the json field.
	 *
	 * @param response the response
	 * @param path the path
	 * @param arrayPath the array path
	 * @return the json field
	 * @throws JSONException the JSON exception
	 * @throws Exception the exception
	 */
	public String getJsonField(String response,String path,String arrayPath) throws JSONException, Exception{
        try{
            String pathValues[] =path.split("//");
            String arrayPathValues[]=arrayPathValues=arrayPath.split("//");
            int arrayPathValueCount=0;
            if(true!=arrayPath.isEmpty()){
                arrayPathValueCount=arrayPathValues.length;
            }for(int i=0,j=0;pathValues.length>i||arrayPathValueCount>j;){
                if(response.startsWith("{")){
                    response=getJsonObject(response,pathValues[i]);
                    i++;
                }
                else{
                    response=getJsonArray(response,arrayPathValues[j]);
                    j++;
                }
            }
        }catch (Exception e) {
                    throw new Exception(
                            "Exception occured while getting the JSON Field value. Exception message is:"
                                    + e.getMessage());
        }
        return response;
    }

    /**
     * Gets the json object.
     *
     * @param response the response
     * @param pathValues the path values
     * @return the json object
     * @throws JSONException the JSON exception
     * @throws Exception the exception
     */
    public String getJsonObject(String response,String pathValues) throws JSONException, Exception{
        try{
            JSONObject json=new JSONObject(response);
            response=json.get(pathValues).toString();
        }catch (Exception e) {
                    throw new Exception(
                            "Exception occured while getting the JSON Field value. Exception message is:"
                                    + e.getMessage());
        }
        return response;
    }

    /**
     * Gets the json array.
     *
     * @param response the response
     * @param arrayPathValues the array path values
     * @return the json array
     * @throws JSONException the JSON exception
     * @throws Exception the exception
     */
    public String getJsonArray(String response, String arrayPathValues) throws JSONException, Exception {
        try {
            JSONArray json = new JSONArray(response);
            for (int i = 0; json.length() > i; i++) {
                response = json.get(i).toString();
                if (response.contains(arrayPathValues)) {
                    return response;
                } else {
                }

            }
        } catch (Exception e) {
            throw new Exception("Exception occured while getting the JSON Field value. Exception message is:" + e.getMessage());
        }
        return "";
    }

    /**
     * Verify string.
     *
     * @param response the response
     * @param path the path
     * @param arrayPath the array path
     * @throws JSONException the JSON exception
     * @throws Exception the exception
     */
    public void VerifyString(String response, String path, String arrayPath) throws JSONException, Exception {
        try {
            response = getJsonField(response, path, arrayPath);
        } catch (Exception e) {
            throw new Exception("Exception occured while getting the JSON Field value. Exception message is:" + e.getMessage());
        }
    }


}
