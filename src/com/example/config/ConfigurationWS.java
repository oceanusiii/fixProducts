package com.example.config;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ConfigurationWS {

	int TIMEOUT_MILLISEC = 30000;		// 10000
	Context context;

	public ConfigurationWS(Context context) {
		this.context = context;
	}
	
	
	
	//
	// post request to html
	// get value JSON <String>
	// convert value to JsonArray
	//
	public JSONArray connectWSPut_Get_Data(String url, JSONObject json, String jsonName) {
		
		JSONArray jarr = null;
		try {
			
			
			
			// cach khac de thay vao entity __________________________________
			StringEntity se = new StringEntity(json.toString(), "UTF-8");
			InputStreamEntity ise = new InputStreamEntity(new ByteArrayInputStream(json.toString().getBytes("UTF-8")), -1);
			// ---------------------------------------------------------------------
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
			// -----------------------
			HttpClient client = new DefaultHttpClient(httpParams);

			HttpPost request = new HttpPost(url);
			
			// set POST request
			request.setEntity(new ByteArrayEntity(json.toString().getBytes("UTF-8")));
							
			Log.d("fuck", "fuck");	// oh, this so fun :>
			
			///??????????????????????????????
			// it use for debug?
			request.setHeader("json", json.toString());
			Log.e("fuck", json.toString());
			
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();

			InputStream instream = entity.getContent();
			
			// test
			// no connect to server
			// set hardstring to result :>
			//String result = "{\"voucher\":[{\"_id\":38,\"code_id\":\"BSM840-12\",\"product_name\":\"BSTONE 12\",\"group_id\":\"12.00R20\",\"type_id\":\"PR18\",\"note\":\"abc\",\"accessory\":0,\"status\":1,\"type\":1,\"quantity\":1,\"create_date\":\"Apr 4 2014 12:00AM\",\"update_date\":\"Apr 4 2014 12:00AM\",\"flag\":1,\"barcode\":\"1340590100\"},{\"_id\":41,\"code_id\":\"BSM840-11\",\"product_name\":\"BSTONE 11\",\"group_id\":\"11.00R20\",\"type_id\":\"PR18\",\"note\":\"abc\",\"accessory\":0,\"status\":1,\"type\":1,\"quantity\":1,\"create_date\":\"Apr 4 2014 12:00AM\",\"update_date\":\"Apr 4 2014 12:00AM\",\"flag\":1,\"barcode\":\"1340590101\"},{\"_id\":43,\"code_id\":\"5555\",\"product_name\":\"55555\",\"group_id\":\"12.00R20\",\"type_id\":\"PR16\",\"note\":\"555555\",\"accessory\":0,\"status\":1,\"type\":1,\"quantity\":1,\"create_date\":\"Apr 22 2014 12:00AM\",\"update_date\":\"Apr 22 2014 12:00AM\",\"flag\":1,\"barcode\":\"1340590102\"},{\"_id\":44,\"code_id\":\"MVT101\",\"product_name\":\"BSBMW\",\"group_id\":\"12.00R20\",\"type_id\":\"PR20\",\"note\":null,\"accessory\":0,\"status\":1,\"type\":1,\"quantity\":1,\"create_date\":\"Apr 21 2014 4:52PM\",\"update_date\":\"Apr 21 2014 4:52PM\",\"flag\":1,\"barcode\":\"1340590103\"},{\"_id\":45,\"code_id\":\"LOPHONDA\",\"product_name\":\"CRB\",\"group_id\":\"11.00R18\",\"type_id\":\"PR18\",\"note\":null,\"accessory\":0,\"status\":1,\"type\":1,\"quantity\":1,\"create_date\":\"Apr 21 2014 4:55PM\",\"update_date\":\"Apr 21 2014 4:55PM\",\"flag\":1,\"barcode\":\"1340590104\"},{\"_id\":46,\"code_id\":\"MH01\",\"product_name\":\"CSTONE 22\",\"group_id\":\"11.00R18\",\"type_id\":\"PR18\",\"note\":\"fvssdsc\",\"accessory\":0,\"status\":1,\"type\":1,\"quantity\":1,\"create_date\":\"Apr 22 2014 8:49AM\",\"update_date\":\"Apr 22 2014 8:49AM\",\"flag\":1,\"barcode\":\"1340590105\"},{\"_id\":47,\"code_id\":\"123\",\"product_name\":\"acb123\",\"group_id\":\"12.00R20\",\"type_id\":\"PR13\",\"note\":\"fdff\",\"accessory\":0,\"status\":1,\"type\":1,\"quantity\":1,\"create_date\":\"Apr 22 2014 10:04AM\",\"update_date\":\"Apr 22 2014 10:04AM\",\"flag\":1,\"barcode\":\"1340590106\"},{\"_id\":48,\"code_id\":\"T555\",\"product_name\":\"555\",\"group_id\":\"thuoc ngoai\",\"type_id\":\"nhom ngoai\",\"note\":\"thuoc la\",\"accessory\":0,\"status\":1,\"type\":1,\"quantity\":1,\"create_date\":\"May 13 2014 12:00AM\",\"update_date\":\"May 13 2014 12:00AM\",\"flag\":1,\"barcode\":\" \"}]}";
			
			
			String result = ConfigurationWSRestClient.convertStreamToString(instream);
			
			//test
			Log.e("json string result", result);
			//Log.e("jsonName", jsonName);
			
			
			// decode string to jsonobject
			JSONObject jobj = new JSONObject(result);
			jarr = jobj.getJSONArray(jsonName);

		} catch (Exception t) { }
		
		
		return jarr;
	}
	
	
	
	// Get data
	public JSONArray connectWS_Get_Data(String url, String jsonName) {
		JSONArray jarr = null;
		try {

			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpClient client = new DefaultHttpClient(httpParams);

			HttpPost request = new HttpPost(url);
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();

			InputStream instream = entity.getContent();

			String result = ConfigurationWSRestClient.convertStreamToString(instream);
			JSONObject jobj = new JSONObject(result);
			jarr = jobj.getJSONArray(jsonName);

		} catch (Exception t) {
		}
		return jarr;
	}
	
	
	// put data through POST
	public void connectWS_Put_Data(String url, JSONObject json) {
		try {
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpClient client = new DefaultHttpClient(httpParams);
			HttpPost request = new HttpPost(url);
			request.setEntity(new ByteArrayEntity(json.toString().getBytes("UTF8")));
			request.setHeader("json", json.toString());
			client.execute(request);

		} catch (Exception t) {
		}
	}

}
