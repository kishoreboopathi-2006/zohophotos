package com.zs.zohophotos.records;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class UploadResponse {
	List<DataItem> data;
}

class DataItem {
	Attributes attributes;
}

class Attributes {
	String resource_id;
	String parent_id;
	@SerializedName("FileName")
	String photoName;
}
