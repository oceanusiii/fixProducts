package com.example.voucher;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.StaticLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.config.ConfigurationWS;
import com.example.voucher.DetailVoucher.delete;
import com.exemple.model.Product;



public class DetailProduct extends Activity {
	
	
	ProgressDialog progress;
	Product product;
	TextView product_name;
	TextView quantity;
	TextView status;
	TextView barcode;
	TextView date;
	TextView code_id;
	ConfigurationWS WSdetail = new ConfigurationWS(DetailProduct.this);
	// private static final String url =
	// "http://117.6.131.222:8090/POS/WSERP/delete_product.php";
	//private static final String url = "http://117.6.131.222:8090/POS/WSERP/delete_product.php";
	private static final String url = "http://117.6.131.222:6789/erpws/delete_product.php";
	
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();


		//Intent i = new Intent(DetailProduct.this, ListVoucher.class);
		//startActivity(i);
		
		//finishActivity(0);
	}


	
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			Intent i = new Intent(DetailProduct.this, ListVoucher.class);
			startActivity(i);
		}
		
		return super.onKeyDown(keyCode, event);
	}





	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		
		// get data from father activity
		Bundle bundle = getIntent().getExtras();
		product = bundle.getParcelable("product");
		
		// get views from layout
		product_name = (TextView) findViewById(R.id.productnamedetail);
		quantity = (TextView) findViewById(R.id.quantitydetail);
		status = (TextView) findViewById(R.id.statusdetail);
		barcode = (TextView) findViewById(R.id.barcodedetail);
		date = (TextView) findViewById(R.id.date);
		code_id=(TextView) findViewById(R.id.codevoucherdetail);
		
		//Log.e("ProductDetail_test", product.getProduct_Name());
		
		product_name.setText(product.getProduct_Name());
		quantity.setText(product.getQuantity());
		status.setText(product.getStatus());
		barcode.setText(product.getBarcode());
		code_id.setText(product.getCode_id());
		date.setText(product.getCreate_date());
		
		ImageButton update = (ImageButton) findViewById(R.id.sua);
		ImageButton xoa = (ImageButton) findViewById(R.id.xoa);
		ImageButton btnBack = (ImageButton) findViewById(R.id.home);
		
		
		
		// button DELETE onClick
		xoa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Asynctask class
				new delete().execute();
			}
		});
		
		
		
		// button UPDATE onClick
		update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(DetailProduct.this, EditVoucher.class);
				
				// send data to new activity
				Bundle bun = new Bundle();
				bun.putParcelable("detailvoucher", product);
				intent.putExtras(bun);
				// open new activity - <edit voucher activity>
				startActivity(intent);
			}
		});
		
		
		
		// Button BACK onClick
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();
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
				json.put("_id", product.getId());
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
			Intent intent = new Intent(DetailProduct.this, ListVoucher.class);
			startActivity(intent);
			finish();
		}
		
		
		// 1
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progress = new ProgressDialog(DetailProduct.this);
			progress.setMessage("Loading products. Please wait...");
			progress.setIndeterminate(false);
			progress.setCancelable(false);
			progress.show();
		}

	}
}
