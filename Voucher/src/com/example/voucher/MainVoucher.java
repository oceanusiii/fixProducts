package com.example.voucher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainVoucher extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainvoucher);
		Button barcodeselect = (Button) findViewById(R.id.mainBarSelect);
		Button listselect = (Button) findViewById(R.id.mainListSelect);
		
		
		listselect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainVoucher.this, ListVoucher.class);
				startActivity(intent);
			}
		});
		
		
		barcodeselect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainVoucher.this, BareCodeCT.class);
				startActivity(intent);
			}
		});
	}

}
