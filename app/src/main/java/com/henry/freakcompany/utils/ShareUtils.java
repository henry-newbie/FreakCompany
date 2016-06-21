package com.henry.freakcompany.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by henry on 2016/6/18.
 */
public class ShareUtils {

    /**
     * 复制到剪切板
     * @param context
     * @param text
     * @param view
     */
    public static void copyToClipboard(Context context, String text, View view) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(ClipData.newPlainText("url", text));
        Snackbar.make(view, "链接已复制！", Snackbar.LENGTH_LONG).show();
    }

    /**
     * 打开系统默认浏览器
     * @param context
     * @param url
     */
    public static void openBrowser(Context context, String url) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }
}
