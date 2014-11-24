package com.gezbox.windmap.app.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.gezbox.windmap.app.model.FileTraversal;

import java.io.File;
import java.util.*;

public class Util {
	Context context;
	
	public Util(Context context) {
		this.context=context;
	}
	
	/**
	 * 获取图片库目录
	 * @return
	 */
	public ArrayList<String>  listAlldir(){
    	Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    	Uri uri = intent.getData();
    	ArrayList<String> list = new ArrayList<String>();
    	String[] proj = {MediaStore.Images.Media.DATA};
    	Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
    	while(cursor.moveToNext()){
    		String path = cursor.getString(0);
    		list.add(new File(path).getAbsolutePath());
    	}
		return list;
    }

    /**
     * 获取本机图片目录
     * @return
     */
	public List<FileTraversal> LocalImgFileList(){
		List<FileTraversal> data = new ArrayList<FileTraversal>();
		List<String> allimglist = listAlldir();

		if (allimglist != null) {
            Map<String, FileTraversal> fileTraversalMap = new HashMap<String, FileTraversal>();
			Set<String> dirNameSet = new HashSet<String>();
			for (int i = 0; i < allimglist.size(); i++) {
                String filePath = allimglist.get(i);
                String dirName = getDirName(filePath);

                if (dirNameSet.contains(dirName)) {
                    fileTraversalMap.get(dirName).getFilecontent().add(filePath);
                } else {
                    dirNameSet.add(dirName);

                    FileTraversal fileTraversal = new FileTraversal();
                    fileTraversal.setFilename(dirName);
                    fileTraversal.getFilecontent().add(filePath);

                    fileTraversalMap.put(dirName, fileTraversal);
                }
			}

            for (String dirName : fileTraversalMap.keySet()) {
                data.add(fileTraversalMap.get(dirName));
            }
		}
		return data;
	}
	
	public String getDirName(String data){
		String filename[] = data.split("/");
		if (filename != null) {
			return filename[filename.length - 2];
		}
		return null;
	}
}
