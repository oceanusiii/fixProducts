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
import com.exemple.model.Voucher;

public class ListVoucher extends Activity {
	ArrayList<Voucher> myArrayList = new ArrayList<Voucher>();
	ListView myListView;
	CustomAdapter myCustomAdapter;
	Bundle bundlerefrest;
	// private static final String url =
	// "http://192.168.1.101:81/WSERP/get_all_products.php";
	// private static final String url =
	// "http://117.6.131.222:8090/POS/WSERP/get_all_products.php";
	private static final String url = "http://117.6.131.222:8090/POS/WSERP/get_all_products.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bundlerefrest = savedInstanceState;
		setContentView(R.layout.listvoucher);
		/*--------------------policy for connect to ws-------------*/
		final ListView myListView = (ListView) findViewById(R.id.MainListvoucher);
		myCustomAdapter = new CustomAdapter(getApplicationContext(), 1,
				myArrayList);
		myListView.setAdapter(myCustomAdapter);
		ImageButton myimageButton = (ImageButton) findViewById(R.id.MainIbAdd);
		ImageButton refreshButton = (ImageButton) findViewById(R.id.MainIbRefresh);
		// new Getall().execute();
		refreshButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				myArrayList.clear();
				new WSGetAllProduct(ListVoucher.this).execute();
			}
		});
		myimageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ListVoucher.this, AddVoucher.class);
				startActivity(intent);
			}
		});
		myListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Voucher voucher = (Voucher) myListView.getItemAtPosition(arg2);
				Intent intent = new Intent(ListVoucher.this,
						DetailVoucher.class);
				Bundle bundle = new Bundle();
				Log.d("date", voucher.getCreate_time());
				bundle.putParcelable("voucher", voucher);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		myArrayList.clear();
		new WSGetAllProduct(ListVoucher.this).execute();
		super.onResume();
	}

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
			try {
				try {
					String wsgetphonefilter = context
							.getString(R.string.wsgetproduct);
					JSONObject json = new JSONObject();
					JSONArray arrItem = new JSONArray();
					arrItem = mWS.connectWSPut_Get_Data(url, json, "voucher");
					if (arrItem != null) {
						for (int i = 0; i < arrItem.length(); i++) {
							JSONObject results = arrItem.getJSONObject(i);

							Voucher voucher = new Voucher();
							voucher.setId(results.getString("id"));
							voucher.setProduct_name(results
									.getString("product_name"));
							voucher.setQuantity(results.getString("quantity"));
							voucher.setStatus(results.getString("status"));
							voucher.setBarcode(results.getString("barcode"));
							voucher.setCode_voucher(results
									.getString("code_voucher"));
							voucher.setCreate_time(results
									.getString("create_time"));
							myArrayList.add(voucher);
						}
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
			myCustomAdapter.notifyDataSetChanged();
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
}
