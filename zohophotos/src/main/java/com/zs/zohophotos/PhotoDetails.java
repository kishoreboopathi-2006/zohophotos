package com.zs.zohophotos;

import java.io.InputStream;

public record PhotoDetails(InputStream inputStream,long size,String name,String contentType) {

}
