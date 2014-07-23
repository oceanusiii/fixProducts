package com.example.voucher;

import org.json.JSONObject;

import com.example.config.ConfigurationWS;
import com.exemple.model.Voucher;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class EditVoucher extends Activity {
	EditText product_name;
	EditText quantity;
	EditText status;
	EditText barcode;
	EditText code_voucher;
	ProgressDialog progress;
	// private static final String url =
	// "http://117.6.131.222:8090/POS/WSERP/update.php";
	private static final String url = "http://117.6.131.222:8090/POS/WSERP/update.php";
	Voucher voucher;
	ConfigurationWS WSedit = new ConfigurationWS(EditVoucher.this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);
		Bundle bundle = getIntent().getExtras();
		voucher = bundle.getParcelable("detailvoucher");

		product_name = (EditText) findViewById(R.id.editname);
		quantity = (EditText) findViewById(R.id.editquantity);
		status = (EditText) findViewById(R.id.editstatus);
		barcode = (EditText) findViewById(R.id.editbarcode);
		code_voucher = (EditText) findViewById(R.id.editcode_voucher);

		product_name.setText(voucher.getProduct_name());
		quantity.setText(voucher.getQuantity());
		status.setText(voucher.getStatus());
		code_voucher.setText(voucher.getCode_voucher());
		barcode.setText(voucher.getBarcode());
		Log.d("edit", voucher.getProduct_name().toString());
		ImageButton save = (ImageButton) findViewById(R.id.saveedit);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new update().execute();

			}
		});

	}

	class update extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
				String id = voucher.getId();
				String product_namej = product_name.getText().toString();
				String quantityj = quantity.getText().toString();
				String statusj = status.getText().toString();
				String barcodej = barcode.getText().toString();
				String code_voucherj = code_voucher.getText().toString();
				JSONObject json = new JSONObject();
				json.put("id", id);
				json.put("product_name", product_namej);
				json.put("quantity", quantityj);
				json.put("status", statusj);
				json.put("barcode", barcodej);
				json.put("code_voucher", code_voucherj);
				Log.d("edit", json.toString());
				WSedit.connectWSPut_Get_Data(url, json, "voucher");
			} catch (Exception e) {

			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progress.dismiss();
			Intent intent = new Intent(EditVoucher.this, ListVoucher.class);
			startActivity(intent);

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			super.onPreExecute();
			progress = new ProgressDialog(EditVoucher.this);
			progress.setMessage("Loading products. Please wait...");
			progress.setIndeterminate(false);
			progress.setCancelable(false);
			progress.show();
		}

	}

}
