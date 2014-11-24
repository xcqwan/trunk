package com.gezbox.windmap.app.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ParcelCreator")
public class FileTraversal implements Parcelable {
	private String filename;
    private List<String> filecontent = new ArrayList<String>();
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(filename);
		dest.writeList(filecontent);
	}
	
	public static final Creator<FileTraversal> CREATOR = new Creator<FileTraversal>() {
		
		@Override
		public FileTraversal[] newArray(int size) {
			return null;
		}
		
		@Override
		public FileTraversal createFromParcel(Parcel source) {
			FileTraversal ft=new FileTraversal();
			ft.filename= source.readString();
			ft.filecontent= source.readArrayList(FileTraversal.class.getClassLoader());
			
			return ft;
		}
	};

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<String> getFilecontent() {
        return filecontent;
    }

    public void setFilecontent(List<String> filecontent) {
        this.filecontent = filecontent;
    }
}
