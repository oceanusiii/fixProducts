package com.example.voucher;

import java.util.ArrayList;

import com.exemple.model.Product;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;



public class CustomAdapter extends ArrayAdapter<Product> 
{
	
	private LayoutInflater mLayoutInflate = null;
	private ArrayList<Product> mArrayList;
	private ViewHolder holder = null;

	
	
	public CustomAdapter(Context context, int resource, ArrayList<Product> arrayList) 
	{
		super(context, resource, arrayList);
		mLayoutInflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mArrayList = arrayList;
	}

	
	
	@SuppressLint("InflateParams") @Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		// TODO Auto-generated method stub
		View view = convertView;

		
		
		if (convertView == null)
		{
			holder = new ViewHolder();
			view = mLayoutInflate.inflate(R.layout.custom_voucher, null);
			
			holder.tvID = (TextView) view.findViewById(R.id.tv_customvoucher1);
			holder.tvName = (TextView) view.findViewById(R.id.tv_customvoucher2);
			holder.cbSelected = (CheckBox) view.findViewById(R.id.cb_customvoucher1);
			
			view.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) view.getTag();
		}

		
//		TextView tv1 = (TextView) view.findViewById(R.id.tv_customvoucher1);
//		tv1.setText(mArrayList.get(position).getId());
//		TextView tv2 = (TextView) view.findViewById(R.id.tv_customvoucher2);
//		tv2.setText(mArrayList.get(position).getProduct_Name());

		
		final Product p = mArrayList.get(position);
		
		holder.tvID.setText(p.getId());
		holder.tvName.setText(p.getProduct_Name());
		holder.cbSelected.setChecked(p.isChecked());
		
		
		holder.cbSelected.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				CheckBox cb = (CheckBox) v;
				
				if(cb.isChecked())
				{
					p.setChecked(true);
				}
				else
				{
					p.setChecked(false);
				}
			}
		});
		
			 
		return view;

	}
	
	
	class ViewHolder
	{
		TextView tvID;
		TextView tvName;
		CheckBox cbSelected;
	}
}
