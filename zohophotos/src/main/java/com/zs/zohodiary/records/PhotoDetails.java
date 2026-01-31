package com.zs.zohodiary.records;

import java.io.InputStream;

public record PhotoDetails(InputStream inputStream,long size,String name,String contentType) {

}
