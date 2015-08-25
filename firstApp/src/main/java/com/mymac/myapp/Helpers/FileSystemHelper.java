package com.mymac.myapp.Helpers;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by eduardo on 6/11/2014.
 */
public class FileSystemHelper { //ExternalStorage

    public FileSystemHelper() { }


    public static File[] getFolder(String path) {

        path = path+"/music";

        //Map<String, File> externalLocations = FileSystemHelper.getAllStorageLocations();
        //File sdCard = externalLocations.get(FileSystemHelper.SD_CARD);
        //File f = new File(sdCard.toString());
        File f = new File(path);
        File file[] = f.listFiles();
        Log.d("Files", " FileSystemHelper.SD_CARD Size: " + path);

        if (file.length > 0) {
            for (int i = 0; i < file.length; i++) {
                Log.d("Files", "FileName:" + file[i].getName());
            }
        }

        return file;
    }
    public static File[] getAlbum(String path) {

        //path = path;

        //Map<String, File> externalLocations = FileSystemHelper.getAllStorageLocations();
        //File sdCard = externalLocations.get(FileSystemHelper.SD_CARD);
        //File f = new File(sdCard.toString());
        File f = new File(path);
        File file[] = f.listFiles();
        Log.d("Files", " FileSystemHelper.SD_CARD Size: " + path);
        for (int i=0; i < file.length; i++)
        {
            Log.d("Files", "FileName:" + file[i].getName());
        }

        return file;
    }


    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
    public static final String SD_CARD = "sdCard";
    public static final String EXTERNAL_SD_CARD = "externalSdCard";

    /**
     * @return True if the external storage is available. False otherwise.
     */
    public static boolean isAvailable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static String getSdCardPath() {
        return Environment.getExternalStorageDirectory().getPath() + "/";
    }

    /**
     * @return True if the external storage is writable. False otherwise.
     */
    public static boolean isWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;

    }

    /**
     * @return A map of all storage locations available
     */
    public static Map<String, File> getAllStorageLocations() {
        Map<String, File> map = new HashMap<String, File>(10);

        List<String> mMounts = new ArrayList<String>(10);
        List<String> mVold = new ArrayList<String>(10);
        mMounts.add("/mnt/sdcard");
        mVold.add("/mnt/sdcard");

        try {
            File mountFile = new File("/proc/mounts");
            if(mountFile.exists()){
                Scanner scanner = new Scanner(mountFile);
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if (line.startsWith("/dev/block/vold/")) {
                        String[] lineElements = line.split(" ");
                        String element = lineElements[1];

                        // don't add the default mount path
                        // it's already in the list.
                        if (!element.equals("/mnt/sdcard"))
                            mMounts.add(element);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File voldFile = new File("/system/etc/vold.fstab");
            if(voldFile.exists()){
                Scanner scanner = new Scanner(voldFile);
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if (line.startsWith("dev_mount")) {
                        String[] lineElements = line.split(" ");
                        String element = lineElements[2];

                        if (element.contains(":"))
                            element = element.substring(0, element.indexOf(":"));
                        if (!element.equals("/mnt/sdcard"))
                            mVold.add(element);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        for (int i = 0; i < mMounts.size(); i++) {
            String mount = mMounts.get(i);
            if (!mVold.contains(mount))
                mMounts.remove(i--);
        }
        mVold.clear();

        List<String> mountHash = new ArrayList<String>(10);

        for(String mount : mMounts){
            File root = new File(mount);
            if (root.exists() && root.isDirectory() && root.canWrite()) {
                File[] list = root.listFiles();
                String hash = "[";
                if(list!=null){
                    for(File f : list){
                        hash += f.getName().hashCode()+":"+f.length()+", ";
                    }
                }
                hash += "]";
                if(!mountHash.contains(hash)){
                    String key = SD_CARD + "_" + map.size();
                    if (map.size() == 0) {
                        key = SD_CARD;
                    } else if (map.size() == 1) {
                        key = EXTERNAL_SD_CARD;
                    }
                    mountHash.add(hash);
                    map.put(key, root);
                }
            }
        }

        mMounts.clear();

        if(map.isEmpty()) {
            map.put(SD_CARD, Environment.getExternalStorageDirectory());
        }
        return map;
    }


    public static String getParentFolder(String pathName) {

        String folder[] = pathName.split("/");

        return folder[folder.length - 1];
    }

}
