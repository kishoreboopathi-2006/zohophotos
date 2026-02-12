package com.zs.zohophotos.service;

import java.util.ArrayList;

import com.zs.zohophotos.DAO.WorkDrivePhotosAndFoldersDetailsManagement;
import com.zs.zohophotos.records.DeletedPhotoDetails;

public class DeletedPhotoDetailsOperations {
	WorkDrivePhotosAndFoldersDetailsManagement dbManager;

	public DeletedPhotoDetailsOperations() {
		dbManager = new WorkDrivePhotosAndFoldersDetailsManagement();

	}

	public boolean deletePhotoDetails(String url) {
		Boolean flag = dbManager.deletePhoto(url);
		return flag;
	}

	public boolean insertDeletedPhotoDetails(DeletedPhotoDetails details) {
		boolean flag = dbManager.insertDeletedPhoto(details);
		return flag;
	}

	public ArrayList<DeletedPhotoDetails> getDeletedPhotoDetails(int userId) {
		ArrayList<DeletedPhotoDetails> deletedPhotos=dbManager.getDeletedPhotos(userId);
		return deletedPhotos;
	}

}
