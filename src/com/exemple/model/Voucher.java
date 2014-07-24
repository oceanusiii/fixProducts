package com.exemple.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Voucher implements Parcelable {
	String id;
	String product_name;
	String quantity;
	String status;
	String barcode;
	String code_voucher;		// code_voucher = product_id ???

	public String getCode_voucher() {
		return code_voucher;
	}

	public void setCode_voucher(String code_voucher) {
		this.code_voucher = code_voucher;
	}

	String create_time;

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public Voucher() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Voucher(String id, String product_name, String quantity,
			String status, String barcode, String create_time,
			String code_voucher) {
		super();
		this.id = id;
		this.product_name = product_name;
		this.quantity = quantity;
		this.status = status;
		this.barcode = barcode;
		this.create_time = create_time;
		this.code_voucher=code_voucher;
	}

	public Voucher(Parcel a) {
		id = a.readString();
		product_name = a.readString();
		quantity = a.readString();
		status = a.readString();
		barcode = a.readString();
		create_time = a.readString();
		code_voucher=a.readString();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel a, int arg1) {
		// TODO Auto-generated method stub
		a.writeString(id);
		a.writeString(product_name);
		a.writeString(quantity);
		a.writeString(status);
		a.writeString(barcode);
		a.writeString(create_time);
		a.writeString(code_voucher);
	}

	public static final Parcelable.Creator<Voucher> CREATOR = new Creator<Voucher>() {
		@Override
		public Voucher[] newArray(int arg0) {
			// TODO Auto-generated method stub
			return new Voucher[arg0];
		}

		@Override
		public Voucher createFromParcel(Parcel arg0) {
			// TODO Auto-generated method stub
			return new Voucher(arg0);
		}
	};
}
