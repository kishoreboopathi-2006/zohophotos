package com.zs.zohophotos;

import java.sql.Connection;

public class RetriveDiaryAndPhotoDetails {
	Connection conn;
	RetriveDiaryAndPhotoDetails(){
		conn=DBConnector.getConnection();
	}
	
}
