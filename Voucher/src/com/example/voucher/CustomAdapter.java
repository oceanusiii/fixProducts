package com.example.voucher;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.exemple.model.Product;

public class CustomAdapter extends ArrayAdapter<Product> {
	
	
	private LayoutInflater mLayoutInflate = null;
	private ArrayList<Product> mArrayList;

	public CustomAdapter(Context context, int resource, ArrayList<Product> arrayList) {
		
		super(context, resource, arrayList);
		mLayoutInflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mArrayList = arrayList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;

		
		final Product p= mArrayList.get(position);
		
		if (convertView == null) {
			view = mLayoutInflate.inflate(R.layout.custom_voucher, null);}

			TextView tv1 = (TextView) view.findViewById(R.id.customvoucher);
			tv1.setText(mArrayList.get(position).getProduct_Name());
			TextView id = (TextView) view.findViewById(R.id.id);
			id.setText(mArrayList.get(position).getId());
			CheckBox cb =(CheckBox) view.findViewById(R.id.cb);
			cb.setOnClickListener(new OnClickListener() { // Vu: 15:46 23/07/2014: neu khong duoc them ca thang nay vao
				@Override
				public void onClick(View v) {
					CheckBox cb = (CheckBox) v;
					p.setCheckbox(cb.isChecked());
					if (cb.isChecked()) {
						p.setCheckbox(true);
					} else {
						p.setCheckbox(false);
					}
				}
			});
		return view;

	}

}
