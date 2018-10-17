package com.kzj.mall.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
    public static final String DIR = Environment.getExternalStorageDirectory() + "/kzj";

    /**
     * 保存安装包到本地
     *
     * @param is
     * @param apkFile
     * @return
     * @throws IOException
     */
    public static File saveApkFile(InputStream is, File apkFile) throws IOException {
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            if (apkFile.exists()) {
                apkFile.delete();
            }
            fos = new FileOutputStream(apkFile);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            return apkFile;

        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static File getApkFile(String apkName) {
        boolean mounted = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (!mounted) {
            return null;
        }
        File dir = new File(DIR + "/apk");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, apkName);
        return file;
    }

}
