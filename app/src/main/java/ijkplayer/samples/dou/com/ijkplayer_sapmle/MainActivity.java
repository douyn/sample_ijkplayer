package ijkplayer.samples.dou.com.ijkplayer_sapmle;

import android.Manifest;
import android.graphics.Path;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.MediaController;
import android.widget.TableLayout;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;

import ijkplayer.samples.dou.com.ijkplayer_sapmle.media.AndroidMediaController;
import ijkplayer.samples.dou.com.ijkplayer_sapmle.media.IjkVideoView;
import rx.functions.Action1;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    IjkVideoView mVideoView;

    TableLayout mHudView;

    AndroidMediaController mMediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initView();
        initData();
    }

    private void initView() {
        mVideoView = (IjkVideoView) findViewById(R.id.ijk_video_view);

        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        mMediaController = new AndroidMediaController(this, false);

        mHudView = (TableLayout) findViewById(R.id.hud_view);
    }

    private void initData() {

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
//                            String path = Environment.getExternalStorageDirectory() + "/Qiwo/test_mp4.mp4";
                            String path = Environment.getExternalStorageDirectory() + "/Qiwo/avi.avi";

//                            Uri net_url = Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");

                            mVideoView.setMediaController(mMediaController);
                            mVideoView.setHudView(mHudView);
//                            mVideoView.setVideoURI(net_url);
                            mVideoView.setVideoURI(Uri.parse(path));
                            mVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(IMediaPlayer iMediaPlayer) {
                                    mVideoView.start();
                                }
                            });
                        } else {
                            Toast.makeText(MainActivity.this, "需要读取文件权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
