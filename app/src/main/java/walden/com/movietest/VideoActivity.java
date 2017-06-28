package walden.com.movietest;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener {
    private String videoPath;
    private String imagePath;
    private VideoView videoView;
    private ImageView videoimage;
    private Button play;
    private Button stop;
    private boolean isVideo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        init();
        initView();
    }

    private void init() {
        Intent intent = getIntent();
        if (intent != null) {
            videoPath = intent.getStringExtra("video");
            imagePath = intent.getStringExtra("image");
        }
    }

    private void initView() {

        play = (Button) findViewById(R.id.videoplay);
        stop = (Button) findViewById(R.id.videostop);
        play.setOnClickListener(this);
        stop.setOnClickListener(this);

        videoimage = (ImageView) findViewById(R.id.videoimage);
        videoView = (VideoView) findViewById(R.id.videoview);

        if (Build.VERSION.SDK_INT >= 21) {
            //videoView.setMediaController(new MediaController(this));
        }
        videoView.setOnCompletionListener(new MyPlayerOnCompletionListener());



        /* void start()：开始播放
        void stopPlayback()：停止播放
        void resume()：重新播放
        void seekTo(int msec)：从第几毫秒开始播放
        int getCurrentPosition()：获取当前播放的位置
        int getDuration()：获取当前播放视频的总长度
        boolean isPlaying()：当前VideoView是否在播放视频
        void setVideoPath(String path)：以文件路径的方式设置VideoView播放的视频源。
        void setVideoURI(Uri uri)：以Uri的方式设置视频源，可以是网络Uri或本地Uri。
        setMediaController(MediaController controller)：设置MediaController控制器。
        setOnCompletionListener(MediaPlayer.onCompletionListener l)：监听播放完成的事件。
        setOnErrorListener(MediaPlayer.OnErrorListener l)：监听播放发生错误时候的事件。
        setOnPreparedListener(MediaPlayer.OnPreparedListener l)：：监听视频装载完成的事件。


        int getCurrentPosition()：获取当前播放的位置。
        int getDuration()：获取当前播放视频的总长度。
        isPlaying()：当前VideoView是否在播放视频。
        void pause()：暂停
        void seekTo(int msec)：从第几毫秒开始播放。
        void resume()：重新播放。
        void setVideoPath(String path)：以文件路径的方式设置VideoView播放的视频源。
        void setVideoURI(Uri uri)：以Uri的方式设置VideoView播放的视频源，可以是网络Uri或本地Uri。
        void start()：开始播放。
        void stopPlayback()：停止播放。
        setMediaController(MediaController controller)：设置MediaController控制器。
        setOnCompletionListener(MediaPlayer.onCompletionListener l)：监听播放完成的事件。
        setOnErrorListener(MediaPlayer.OnErrorListener l)：监听播放发生错误时候的事件。
        setOnPreparedListener(MediaPlayer.OnPreparedListener l)：：监听视频装载完成的事件。
　　              上面的一些方法通过方法名就可以了解用途。和MediaPlayer配合SurfaceView播放视频不同，
                VideoView播放之前无需编码装载视频，它会在start()开始播放的时候自动装载视频。
                并且VideoView在使用完之后，无需编码回收资源。
        */


        //videoView.setVideoURI(Uri.parse(videoPath));
        //videoView.setVideoURI(Uri.fromFile(new File(videoPath)));
        videoView.setVisibility(View.GONE);
        videoimage.setVisibility(View.VISIBLE);
        videoimage.setImageBitmap(BitmapFactory.decodeFile(imagePath));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.videoplay:

                if (isVideo) {
                    videoView.setVisibility(View.GONE);
                    videoimage.setVisibility(View.VISIBLE);
                    isVideo = false;
                    videoView.stopPlayback();  //停止
                } else {
                    videoView.setVisibility(View.VISIBLE);
                    videoimage.setVisibility(View.GONE);
                    isVideo = true;
                    videoView.resume();
                    videoView.setVideoPath(videoPath);
                    videoView.start();
                    Intent intent =new Intent();
                    intent.putExtra("asd" ,new ArrayList());
                }
                break;
            case R.id.videostop:
                if (videoView.isPlaying()) {
                    cutrrentProcess = videoView.getCurrentPosition();
                    videoView.pause();
                    stop.setText("继续");
                } else {
                    if (isVideo) {
                        videoView.start();
                        videoView.seekTo(cutrrentProcess);
                        stop.setText("暂停");
                    } else {
                        Toast.makeText(this, "请先开启播放", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    int cutrrentProcess = 0;

    private class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            videoView.setVisibility(View.GONE);
            videoimage.setVisibility(View.VISIBLE);
            isVideo = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.stopPlayback();
    }
}
