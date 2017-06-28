package walden.com.movietest;

import android.app.Application;
import android.os.Environment;

import java.io.File;

import mabeijianxi.camera.VCamera;
import mabeijianxi.camera.util.DeviceUtils;


public class MyApplication extends Application {

    String pathCache = "";

    @Override
    public void onCreate() {
        super.onCreate();
        initMovie();
    }

    //初始化视频录制
    private void initMovie() {
        //外部存储 DCIM 目录
        File dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (DeviceUtils.isZte()) {
            if (dcim.exists()) {
                VCamera.setVideoCachePath(dcim + "/qwer/");
            } else {
                VCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/", "sdcard-ext/") + "/qwer/");
            }
        } else {
            VCamera.setVideoCachePath(dcim + "/qwer/");
        }

        pathCache = VCamera.getVideoCachePath();
        VCamera.setDebugMode(true);
        VCamera.initialize(this);
    }

    public String getCachePath() {
        return pathCache;
    }


}
