package com.example.voucher;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.config.ConfigurationWS;
import com.exemple.model.Voucher;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddVoucher extends Activity {
	ProgressDialog progress;
	// private static final String url =
	// "http://117.6.131.222:8090/POS/WSERP/create_product.php";
//	private static final String urladd = "http://117.6.131.222:8090/POS/WSERP/create_product.php";
//	private static final String urlgetall = "http://117.6.131.222:8090/POS/WSERP/get_all_products.php";
//	private static final String urlidmax = "http://117.6.131.222:8090/POS/WSERP/get_maxid.php";

	private static final String urladd = "http://117.6.131.222:6789/erpws/create_product.php";
	private static final String urlgetall = "http://117.6.131.222:6789/erpws/get_all_products.php";
	private static final String urlidmax = "http://117.6.131.222:6789/erpws/get_maxid.php";

	
	EditText product_name;
	EditText quantity;
	EditText status;
	
	EditText create_time;
	EditText code_voucher;
	String idmax="";
	boolean checkCodeVoucher = false;
	ArrayList<String> arrayCodeVoucher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addvoucher);
		ImageButton save = (ImageButton) findViewById(R.id.addvouchersave);
		product_name = (EditText) findViewById(R.id.addVcEdtPrdname);
		quantity = (EditText) findViewById(R.id.addVcEdtQuan);
		status = (EditText) findViewById(R.id.addVcEdtStat);
		 
		create_time = (EditText) findViewById(R.id.addVcEdtCretime);
		code_voucher = (EditText) findViewById(R.id.addVcEdtCodevoucher);
		arrayCodeVoucher = new ArrayList<String>();
		
		
		//new WSGetAllProduct(AddVoucher.this).execute();
		
		//new WSGetMaxid(AddVoucher.this).execute();
		
		
		// Edit text change event
		code_voucher.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
				checkCodeVoucher = true;
				
				String a = code_voucher.getText().toString().trim();
				
				Log.d("list", arrayCodeVoucher.toString() + arrayCodeVoucher.size());
				
				for (int i = 0; i < arrayCodeVoucher.size() - 1; i++) {
					if (arrayCodeVoucher.get(i).equals(a)) {
						code_voucher.setError(" Code da bi trung hoac ban chua nhap ");
						checkCodeVoucher = false;
						break;
					}
				}
			}
		});
		
		
		// button save click event
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				// test
				// set true for `checkCodeVoucher`
				//
				checkCodeVoucher = true;
				
				// check input value
				if (checkCodeVoucher) {
					new WSAddvoucher(AddVoucher.this).execute();
				} else
					Toast.makeText(AddVoucher.this, " Moi nhap lai code voucher ", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public String configidmax(String id) {

		String convertid = id;

		int a = Integer.parseInt(convertid);
		a = a + 1;
		convertid = a + "";
		if (convertid.length() == 1) {
			convertid = "000" + convertid;
		}
		if (convertid.length() == 2) {
			convertid = "00" + convertid;
		}
		if (convertid.length() == 3) {
			convertid = "0" + convertid;
		}
		return convertid;
	}

	// ----------------------------add voucher after check code voucher
	// --------------
	class WSAddvoucher extends AsyncTask<String, String, String> {
		
		private String TAG = "WSGetAllPhone";
		private ConfigurationWS mWS;
		private Context context;
		private ProgressDialog mProgress;

		public WSAddvoucher(Context mcontext) {
			super();
			this.context = mcontext;
			mWS = new ConfigurationWS(mcontext);
			mProgress = new ProgressDialog(mcontext);
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			
			try {
				
				JSONObject json = new JSONObject();
				
				
				String Strproduct_name = product_name.getText().toString();
				String Strquantity = quantity.getText().toString();
				String Strstatus = status.getText().toString();
				String Strcreate_time = create_time.getText().toString();
				String Strcode_voucher = code_voucher.getText().toString();

//				String str = Strcreate_time;
//
//				String[] str1 = str.trim().split("-");
//				int[] count = new int[str1.length];
//
//				StringBuilder convert = new StringBuilder();
//				for (int i = 1; i < str1.length; i++) {
//					count[i] = Integer.parseInt(str1[i].toString());
//					if (count[i] < 9) {
//						convert.append("0" + count[i]);
//					}
//				}
//
//				String date = str1[0] + convert + "";
//				String id = configidmax(idmax);
//				date = date + id;
				
				// lines code below is create jsonObject
				// with data
				// i will create fake data here :>
//				json.put("product_name", "truong");
//				json.put("quantity", "1");
//				json.put("status", "1");
//				json.put("barcode", "123456789099");
//				json.put("create_time", "2014-07-07 00:00");
				//json.put("code_voucher", "1111");
				
				
				
				
				json.put("product_name", Strproduct_name);
				json.put("quantity", Strquantity);
				json.put("status", Strstatus);
				json.put("barcode", Strcode_voucher);
				json.put("create_time", Strcreate_time);
				json.put("code_voucher", Strcode_voucher);
				
				
				
				// send request to server
				mWS.connectWSPut_Get_Data(urladd, json, "voucher");

				
			} catch (Exception e) { }
			
			return null;
		}

		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			progress.dismiss();
			Intent intent = new Intent(AddVoucher.this, ListVoucher.class);
			startActivity(intent);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progress = new ProgressDialog(AddVoucher.this);
			progress.setMessage("Loading products. Please wait...");
			progress.setIndeterminate(false);
			progress.setCancelable(false);
			progress.show();
		}
	}

	// --------------------------Get codevoucher from server
	// --------------------
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
			// oh! double `try` again
			//try {
				try {
					String wsgetphonefilter = context.getString(R.string.wsgetproduct);
					JSONObject json = new JSONObject();
					JSONArray arrItem = new JSONArray();
					arrItem = mWS.connectWSPut_Get_Data(urlgetall, json, "voucher");
					
					if (arrItem != null) {
						
						for (int i = 0; i < arrItem.length(); i++) {
							JSONObject results = arrItem.getJSONObject(i);
							String a = results.getString("code_voucher");
							arrayCodeVoucher.add(a);
						}
					}
				} catch (Exception e) {
					Log.i(TAG, "ERROR : " + e.getMessage());
				}
			//} catch (Exception e) { }
				
			return null;
		}

		
		
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			try {
				
				mProgress.dismiss();
				
			} catch (Exception e) { }
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

	// -------------------Getmax id ----------------
	class WSGetMaxid extends AsyncTask<String, String, String> {
		
		private String TAG = "WSGetAllPhone";
		private ConfigurationWS mWS;
		private Context context;
		private ProgressDialog mProgress;

		public WSGetMaxid(Context mcontext) {
			super();
			this.context = mcontext;
			mWS = new ConfigurationWS(mcontext);
			mProgress = new ProgressDialog(mcontext);
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
				try {
					JSONObject json = new JSONObject();
					JSONArray arrItem = new JSONArray();
					arrItem = mWS.connectWSPut_Get_Data(urlidmax, json, "voucher");
					if (arrItem != null) {
						
						JSONObject results = arrItem.getJSONObject(0);
						idmax = results.getString("id") + "";
						
					}
				} catch (Exception e) {
					Log.i(TAG, "ERROR : " + e.getMessage());
				}
			} catch (Exception e) {
			}
			return null;
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			try {
				mProgress.dismiss();
			} catch (Exception e) {
			}
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
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	        Log.i("MainActivity", "Back button pressed, exiting..");
	        Intent i = new Intent(AddVoucher.this,MainVoucher.value);
	        startActivity(i);
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
