package com.zs.zohodiary.records;

import java.io.InputStream;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

public record DiaryDetails(int userId, int diaryId, String date, LocalTime time, String title, String content,
		ArrayList<PhotoDetails> photos) {

}
