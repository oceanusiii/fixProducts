package com.exemple.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Product implements Parcelable {
	private String id;
	private String code_id;
	private String name;
	private String group_id;
	private String type_id;
	private String accessory;
	private String note;
	private String quantity;
	private String status;
	private String barcode;
	private String create_date;

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode_id() {
		return code_id;
	}

	public void setCode_id(String code_id) {
		this.code_id = code_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getAccessory() {
		return accessory;
	}

	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Product(String id, String code_id, String name, String group_id,
			String type_id, String accessory, String note) {
		super();
		this.id = id;
		this.code_id = code_id;
		this.name = name;
		this.group_id = group_id;
		this.type_id = type_id;
		this.accessory = accessory;
		this.note = note;
	}
	
	
	
	public Product() {
		super();
	}

	public Product(Parcel arg0) {
		super();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub

	}
	public static final Parcelable.Creator<Product> CREATOR = new Creator<Product>() {
		@Override
		public Product[] newArray(int arg0) {
			// TODO Auto-generated method stub
			return new Product[arg0];
		}

		@Override
		public Product createFromParcel(Parcel arg0) {
			// TODO Auto-generated method stub
			return new Product(arg0);
		}
	};

}
