package com.example.voucher;

import org.json.JSONObject;

import com.example.config.ConfigurationWS;
import com.exemple.model.Product;
import com.exemple.model.Voucher;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;




public class DetailVoucher extends Activity {
	
	
	
	ProgressDialog progress;
	//Voucher voucher;
	Product product;
	TextView product_name;
	TextView quantity;
	TextView status;
	TextView barcode;
	TextView date;
	TextView code_voucher;
	ConfigurationWS WSdetail = new ConfigurationWS(DetailVoucher.this);
	// private static final String url =
	// "http://117.6.131.222:8090/POS/WSERP/delete_product.php";
	//private static final String url = "http://117.6.131.222:8090/POS/WSERP/delete_product.php";
	private static final String url = "http://117.6.131.222:6789/erpws/delete_product.php";
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		
		// get data from father activity
		Bundle bundle = getIntent().getExtras();
		//voucher = bundle.getParcelable("voucher");
		product = bundle.getParcelable("voucher");
		
		// get views from layout
		product_name = (TextView) findViewById(R.id.productnamedetail);
		quantity = (TextView) findViewById(R.id.quantitydetail);
		status = (TextView) findViewById(R.id.statusdetail);
		barcode = (TextView) findViewById(R.id.barcodedetail);
		date = (TextView) findViewById(R.id.date);
		code_voucher=(TextView) findViewById(R.id.codevoucherdetail);
		
		// LOG
		Log.d("dohai", product.getCode_id());
		
//		product_name.setText(voucher.getProduct_name());
//		quantity.setText(voucher.getQuantity());
//		status.setText(voucher.getStatus());
//		barcode.setText(voucher.getBarcode());
//		code_voucher.setText(voucher.getCode_voucher());
		
		product_name.setText(product.getProduct_Name());
		quantity.setText(product.getQuantity());
		status.setText(product.getStatus());
		barcode.setText(product.getBarcode());
		code_voucher.setText(product.getCode_id());		
		//Log.d("date", voucher.getCreate_time());
		Log.d("date", product.getCreate_date());
		date.setText(product.getCreate_date());
		
		
		ImageButton update = (ImageButton) findViewById(R.id.sua);
		ImageButton xoa = (ImageButton) findViewById(R.id.xoa);
		
		// button DELETE onClick
		xoa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Asynctask class
				new delete().execute();
			}
		});
		
		update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(DetailVoucher.this, EditVoucher.class);
				
				// send data to new activity
				Bundle bun = new Bundle();
				//bun.putParcelable("detailvoucher", voucher);
				bun.putParcelable("detailvoucher", product);
				intent.putExtras(bun);
				// open new activity - <edit voucher activity>
				startActivity(intent);
			}
		});
	}

	
	
	class delete extends AsyncTask<String, String, String> {

		
		// 2
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
				JSONObject json = new JSONObject();
				//json.put("id", voucher.getId());
				json.put("id", product.getId());
				WSdetail.connectWSPut_Get_Data(url, json, "voucher");
				Log.d("delet", "delte");
			} 
			catch (Exception e) { }
			
			return null;
		}

		
		// 3
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			progress.dismiss();
			super.onPostExecute(result);
			Intent intent = new Intent(DetailVoucher.this, ListVoucher.class);
			startActivity(intent);
			finish();
		}
		
		
		// 1
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progress = new ProgressDialog(DetailVoucher.this);
			progress.setMessage("Loading products. Please wait...");
			progress.setIndeterminate(false);
			progress.setCancelable(false);
			progress.show();
		}

	}
}
