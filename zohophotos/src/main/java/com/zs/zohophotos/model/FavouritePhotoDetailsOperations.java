package com.zs.zohophotos.model;

import java.util.ArrayList;

import com.zs.zohophotos.DAO.WorkDrivePhotosAndFoldersDetailsManagement;
import com.zs.zohophotos.records.FavouritePhotoDetails;

public class FavouritePhotoDetailsOperations {
	FavouritePhotoDetails photoDetails;

	public FavouritePhotoDetailsOperations(FavouritePhotoDetails photoDetails) {
		this.photoDetails = photoDetails;
	}

	public FavouritePhotoDetailsOperations() {
	}

	public boolean insertFavouritePhoto() {
		boolean flag = WorkDrivePhotosAndFoldersDetailsManagement.insertFavouritePhoto(photoDetails);
		return flag;
	}

	public boolean deleteFavouritePhoto() {
		boolean flag=WorkDrivePhotosAndFoldersDetailsManagement.deleteFavouritePhoto(photoDetails);
		return flag;
	}

	public ArrayList<FavouritePhotoDetails> getFavouritePhotoDetails(int userId) {
		ArrayList<FavouritePhotoDetails> photoDetails=WorkDrivePhotosAndFoldersDetailsManagement.getFavouritephotoDetails(userId);
		return photoDetails;
	}
}
