package com.example.voucher;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.config.ConfigurationWS;
import com.exemple.model.Product;
import com.exemple.model.Voucher;

public class BareCodeCT extends Activity {
	
	
	private BluetoothAdapter mBluetoothAdapter = null;
	public EditText id;
	public String message;
	//private static final String url = 
	//"http://117.6.131.222:8090/POS/WSERP/get_all_products1.php";
	//private static final String url = 
	//"http://117.6.131.222:6789/pos/SERPws/wserp/get_all_products1.php";
	//private static final String url = 
	//"http://117.6.131.222:6789/pos/SERPws/wserp/get_all_products_by_barcode.php";
	private static final String url = "http://117.6.131.222:6789/erpws/get_all_products_by_barcode.php";
	
	
	private TextView mainEdtPro;
	private TextView mainEdtQuan;
	private TextView mainEdtSta;
	private TextView mainEdtBar;
	private TextView mainEdtCre;
	//public Voucher vc;
	public Product p;
	
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			finish();
		}
		
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.barcode);
		
		
		id = (EditText) findViewById(R.id.idbarecode);
		id.setOnEditorActionListener(mWriteListener);
		
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		mainEdtBar = (TextView) findViewById(R.id.mainEdtbar);
		mainEdtSta = (TextView) findViewById(R.id.mainEdtSta);
		mainEdtQuan = (TextView) findViewById(R.id.mainEdtQuan);
		mainEdtPro = (TextView) findViewById(R.id.mainEdtProductname);
		mainEdtCre = (TextView) findViewById(R.id.mainEdtcre);
		//vc = new Voucher();
		p = new Product();

		
		// test on VirtualDevice
		// disable checking bluetooth
		
		
//		if (mBluetoothAdapter == null) {
//			
//			Toast.makeText(this, "khong ho tro barecode", Toast.LENGTH_LONG).show();
//			finish();
//			return;
//		}

	}

	private TextView.OnEditorActionListener mWriteListener = new TextView.OnEditorActionListener() {
		
		// event enter key
		public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
			
			
			// If the action is a key-up event on the return key, send the
			// message
			if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) 
			{
				
				message = view.getText().toString();
				
				
				new WSGetProductServer(BareCodeCT.this, message).execute();
				
				view.setText("");
				
				if (!message.equals("")) 
				{
					
					Toast.makeText(BareCodeCT.this, message, Toast.LENGTH_LONG).show();
					// ------- get data ----------------------
					new WSGetProductServer(BareCodeCT.this, message).execute();
				}
				
			}
				
			return true;
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class WSGetProductServer extends AsyncTask<Void, Void, Void> {
		
		
		private String TAG = "WSGetAllPhone";
		private ConfigurationWS mWS;
		private Context context;
		private ProgressDialog mProgress;
		private String barcode;

		// Server-------------------------------
		public WSGetProductServer(Context mcontext, String barcode) {
			super();
			this.context = mcontext;
			this.barcode = barcode;
			
			mWS = new ConfigurationWS(mcontext);
			mProgress = new ProgressDialog(mcontext);
		}

		// --------processing------------------------------//
		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method
			super.onPreExecute();
			mProgress.setMessage("Loading data...");
			mProgress.setCancelable(false);
			mProgress.show();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			
			try {
				
				getServerProducts();
				
			} catch (Exception e) {
				Log.i("Log : ", "Insert INV Detail : " + e.getMessage());
			}
			return null;
		}

		private void getServerProducts() {
			
			try 
			{	
				//String a = "12345";		// ???
				JSONObject json = new JSONObject();
				json.put("barcode", this.barcode);
				
				// test log
				Log.e("test barcode value", this.barcode);
				
				JSONArray arrItem = new JSONArray();
				
				// get from server
				arrItem = mWS.connectWSPut_Get_Data(url, json, "voucher");
				
				
				if (arrItem != null) 
				{
					for (int i = 0; i < arrItem.length(); i++) 
					{
						
						JSONObject results = arrItem.getJSONObject(i);
						
//						vc.setProduct_name(results.getString("product_name"));
//						vc.setQuantity(results.getString("quantity"));
//						vc.setStatus(results.getString("status") + "");
//						vc.setBarcode(results.getString("barcode"));
//						vc.setCreate_time(results.getString("create_date"));
						
						p.setProduct_Name(results.getString("product_name"));
						p.setQuantity(results.getString("quantity"));
						p.setStatus(results.getString("status") + "");
						p.setBarcode(results.getString("barcode"));
						p.setCreate_date(results.getString("create_date"));
						
					}
				}
			} 
			catch (Exception e) 
			{
				Log.i(TAG, "ERROR : " + e.getMessage());
			}
		}

		@Override
		protected void onPostExecute(Void result) 
		{
			super.onPostExecute(result);
			try 
			{
				// ------------set Text here---------------------------
//				mainEdtBar.setText(vc.getBarcode());
//				mainEdtSta.setText(vc.getStatus());
//				mainEdtQuan.setText(vc.getQuantity());
//				mainEdtPro.setText(vc.getProduct_name());
//				mainEdtCre.setText(vc.getCreate_time());
				
				mainEdtBar.setText(p.getBarcode());
				mainEdtSta.setText(p.getStatus());
				mainEdtQuan.setText(p.getQuantity());
				mainEdtPro.setText(p.getProduct_Name());
				mainEdtCre.setText(p.getCreate_date());
				
				mProgress.dismiss();
				
			}
			catch (Exception e) { }
		}
	}

}
