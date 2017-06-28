package walden.com.movietest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import mabeijianxi.camera.MediaRecorderActivity;
import mabeijianxi.camera.model.AutoVBRMode;
import mabeijianxi.camera.model.MediaRecorderConfig;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView showpath;
    private ArrayList<DataBean> mData = new ArrayList<>();
    private String pathCacha;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();
        initView();
    }

    private void getData() {
        if (TextUtils.isEmpty(pathCacha)) {
            pathCacha = ((MyApplication) getApplication()).getCachePath();
        }
        File file = new File(pathCacha);
        File[] files = file.listFiles();

        for (File f : files) {   //各个文件夹
            if (f.isDirectory() && !f.getName().contains("temp")) {  //去除缓存文件
                File[] detailFiles = f.listFiles();
                if (detailFiles.length > 0) {
                    DataBean data = new DataBean();
                    for (File detailFile : detailFiles) {  //遍历文件夹
                        if (detailFile.getName().endsWith("jpg")) {
                            data.setImgPath(detailFile.getAbsolutePath());
                        } else if (detailFile.getName().endsWith("mp4")) {
                            data.setCreateTime(detailFile.lastModified());
                            data.setMp4Path(detailFile.getAbsolutePath());
                            data.setMp4Size(detailFile.length());
                        }
                    }
                    mData.add(data);
                }

            }
        }
    }


    private void initView() {
        Button bt_1 = (Button) findViewById(R.id.bt_1);
        Button getpath = (Button) findViewById(R.id.getpath);
        Button refresh = (Button) findViewById(R.id.refresh);
        bt_1.setOnClickListener(this);
        getpath.setOnClickListener(this);
        refresh.setOnClickListener(this);

        showpath = (TextView) findViewById(R.id.showpath);
        showpath.setText("文件路径: " + pathCacha);

        ListView list = (ListView) findViewById(R.id.list);
        adapter = new ListAdapter(this, mData);
        list.setAdapter(adapter);
    }

    public void reflashData() {
        mData.clear();
        getData();
        adapter.notifyDataSetChanged();
    }

    private void goRecordMovie() {
        MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
                .doH264Compress(new AutoVBRMode())
                .setMediaBitrateConfig(new AutoVBRMode())
                .smallVideoWidth(1920)       //视频宽
                .smallVideoHeight(1080)      //视频高
                .recordTimeMax(6 * 1000)  //录制时间
                .maxFrameRate(20)           //帧率
                .captureThumbnailsTime(1)
                .recordTimeMin((int) (1.5 * 1000)) //最少时间
                .build();
        MediaRecorderActivity.goSmallVideoRecorder(this, MediaRecorderActivity.class.getName(), config);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //reflashData();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_1:
                goRecordMovie();
                break;
            case R.id.getpath:
                showpath.setText(null);
                showpath.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showpath.setText("文件路径: " + pathCacha);
                    }
                }, 300);
                break;
            case R.id.refresh:
                reflashData();
                break;

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        reflashData();
    }
}
