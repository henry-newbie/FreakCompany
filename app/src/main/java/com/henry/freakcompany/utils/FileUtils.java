package com.henry.freakcompany.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * file操作类
 *
 * @Description:TODO
 * @author:henry
 */
public class FileUtils {

    /**
     * 读取asset文件
     *
     * @param context
     * @param fileName
     * @return
     * @Description:TODO
     * @exception:
     * @time:2016-3-17 下午7:34:52
     */
    public static String getFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader
                    (context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line, result = "";
            while ((line = bufReader.readLine()) != null)
                result += line;
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
