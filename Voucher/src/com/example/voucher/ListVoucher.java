package com.example.voucher;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import com.example.config.ConfigurationWS;
import com.exemple.model.Product;
import com.exemple.model.Voucher;

public class ListVoucher extends Activity {
	
	
	ArrayList<Product> myArrayList = new ArrayList<Product>();
	ListView myListView;
	CustomAdapter myCustomAdapter;
	Bundle bundlerefrest;
	// private static final String url =
	// "http://192.168.1.101:81/WSERP/get_all_products.php";
	// private static final String url =
	// "http://117.6.131.222:8090/POS/WSERP/get_all_products.php";
	private static final String url = "http://117.6.131.222:6789/erpws/get_all_products.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		bundlerefrest = savedInstanceState;
		setContentView(R.layout.listvoucher);
		/*--------------------policy for connect to ws-------------*/
		final ListView myListView = (ListView) findViewById(R.id.MainListvoucher);
		myCustomAdapter = new CustomAdapter(getApplicationContext(), 1, myArrayList);
		myListView.setAdapter(myCustomAdapter);
		
		ImageButton myimageButton = (ImageButton) findViewById(R.id.MainIbAdd);
		ImageButton refreshButton = (ImageButton) findViewById(R.id.MainIbRefresh);
		// new Getall().execute();
		
		// button REFRESH onClick
		refreshButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				myArrayList.clear();
				new WSGetAllProduct(ListVoucher.this).execute();
			}
		});
		
		
		
		// button ADD onClick
		myimageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ListVoucher.this, AddVoucher.class);
				startActivity(intent);
			}
		});
		
		
		// select item in list view
		myListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				// get product selected
				Product product = (Product) myListView.getItemAtPosition(arg2);
				//Log.e("List all product", product.getProduct_Name());
				
				//Intent intent = new Intent(ListVoucher.this, DetailVoucher.class);
				Intent intent = new Intent(ListVoucher.this, DetailProduct.class);
				// send data to new activity
				//
				// set data: bundle.put..(key, data)
				// set bundle in to intent: myintent.putExtra("name_bundle", bundle);
				// open new activity: startActivity(myintent);
				//
				// in new activity
				// get intent: intent = getIntent();
				// get bundle data: bundle = intent.getBundleExtra("name_bundle")
				// get data: datatype data = bundle.getDatatype(key)
				//
				Bundle bundle = new Bundle();
				//Log.d("date", product.getCreate_date());
				bundle.putParcelable("product", product);
				intent.putExtras(bundle);
				// open new activity
				// show detail product
				startActivity(intent);
			}
		});
	}

	protected void onResume() {
		// TODO Auto-generated method stub
		
		// when back ifselt from another activity
		// reset list of products
		myArrayList.clear();
		new WSGetAllProduct(ListVoucher.this).execute();
		super.onResume();
	}
	
	
	///
	// this AsyncTask do convert
	// jsonObject to array
	class WSGetAllProduct extends AsyncTask<String, String, String> {
		private String TAG = "WSGetAllPhone";
		private ConfigurationWS mWS;
		private Context context;
		private ProgressDialog mProgress;

		public WSGetAllProduct(Context mcontext) {
			super();
			this.context = mcontext;
			mWS = new ConfigurationWS(mcontext);
			mProgress = new ProgressDialog(mcontext);
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			
			// there are double `try` in here ?
			// why?
			//try { 
			try {
				String wsgetphonefilter = context.getString(R.string.wsgetproduct);
				JSONObject json = new JSONObject();
				JSONArray arrItem = new JSONArray();
				arrItem = mWS.connectWSPut_Get_Data(url, json, "voucher");
				
				
				if (arrItem != null) {
					
					for (int i = 0; i < arrItem.length(); i++) 
					{
						JSONObject results = arrItem.getJSONObject(i);

						Product p = new Product();
						// no value for `id`, did it mean `_id`?
						//voucher.setId(results.getString("id"));
						p.setId(results.getString("_id"));
						p.setProduct_Name(results.getString("product_name"));
						p.setQuantity(results.getString("quantity"));
						p.setStatus(results.getString("status"));
						p.setBarcode(results.getString("barcode"));
						// don't have `code_voucher` in result
						//voucher.setCode_voucher(results.getString("code_voucher"));
						p.setCode_id(results.getString("code_id"));
						// and don't have `create_time` in result too
						//voucher.setCreate_time(results.getString("create_time"));	// it mean is `create_date` ?
						p.setCreate_date(results.getString("create_date"));
						
						myArrayList.add(p);
					}
				}
			} catch (Exception e) {
				
				Log.i(TAG, "in Get All Products : " + e.getMessage());
			}
			//} catch (Exception e) { }
			return null;
		}

		protected void onPostExecute(String result) {
			
			super.onPostExecute(result);
			
			myCustomAdapter.notifyDataSetChanged();
			
			try 
			{	
				mProgress.dismiss();
			} 
			catch (Exception e) { }
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mProgress.setMessage("Loading data...");
			mProgress.setCancelable(false);
			mProgress.show();
		}
	}
}
