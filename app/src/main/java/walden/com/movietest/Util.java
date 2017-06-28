package walden.com.movietest;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    private static DosomeThing doit;

    public static void showDialog(Context context, final File file, final ListAdapter adapter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("确认删除吗？");
        builder.setTitle(null);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (file.exists()) {
                    File[] files = file.listFiles();
                    for (File f : files) {
                        f.delete();
                    }
                    file.delete();
                }
                doit.doit();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    interface DosomeThing {
        void doit();
    }

    public static void setDoit(DosomeThing it) {
        doit = it;
    }

    public static String formatDate(long time) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date(time);
        String timeStr = format.format(date);
        return timeStr;
    }

    public static String formatSize(long time) {
        String ret = "";
        int kb = (int) time / 1000;
        if (kb < 1000) {
            ret = kb + "k";
        } else {
            ret = kb / 1000 + "M";
        }
        return ret;
    }
}
