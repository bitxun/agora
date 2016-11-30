package com.ctoboe.irservice.remotevideo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ctoboe.irservice.remotevideo.util.NetworkConnectivityUtils;

import java.util.Random;

import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;


/**
 * some refs:
 * <p/>
 * 1. http://stackoverflow.com/questions/6026625/layout-design-surfaceview-doesnt-display
 * 2. http://stackoverflow.com/questions/1096618/android-surfaceview-scrolling/2216788#2216788
 */

/**
 * Created by apple on 15/9/9.
 */
public class ChannelActivity extends BaseEngineEventHandlerActivity {



    private SurfaceView mLocalView;
    private String vendorKey = "";
    private String channelId = "";

    private AlertDialog alertDialog;
    RtcEngine rtcEngine;
    @Override
    public void onCreate(Bundle savedInstance) {

        super.onCreate(savedInstance);
        setContentView(R.layout.activity_remotevideo);


//        float xdpi = getResources().getDisplayMetrics().xdpi;
//        float ydpi = getResources().getDisplayMetrics().ydpi;
//        int hpx = getResources().getDisplayMetrics().heightPixels;
//        int wpx = getResources().getDisplayMetrics().widthPixels;


        // keep screen on - turned on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

//        mRemoteUserViewWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 160, getResources().getDisplayMetrics());


        setupRtcEngine();
        initViews();


        this.onUserInteraction();

        // check network
        if (!NetworkConnectivityUtils.isConnectedToNetwork(getApplicationContext())) {
            onError(104);
        }
    }

    void setupChannel() {

        this.channelId = "123";
        int selfUid = new Random().nextInt(Math.abs((int) System.currentTimeMillis()));
        log("selfUid is " + selfUid);
        this.rtcEngine.joinChannel(
                this.vendorKey,
                this.channelId,
                "" /*optionalInfo*/,
                selfUid  /*optionalUid*/);
    }

    void setupRtcEngine() {


        this.vendorKey ="62b0cc4b55c246c7bc375f0742f6312c";

        // setup engine
        ((AgoraApplication) getApplication()).setRtcEngine(vendorKey);
        rtcEngine = ((AgoraApplication) getApplication()).getRtcEngine();
//        LogUtil.log.d(getApplicationContext().getExternalFilesDir(null).toString() + "/agorasdk.log");
        rtcEngine.setLogFile(getApplicationContext().getExternalFilesDir(null).toString() + "/agorasdk.log");


        // setup engine event activity
        ((AgoraApplication) getApplication()).setEngineEventHandlerActivity(this);

        rtcEngine.enableVideo();

    }


    void ensureLocalViewIsCreated() {


        if (this.mLocalView == null) {
            log("ensure local view");
            // local view has not been added before
            FrameLayout localViewContainer = (FrameLayout) findViewById(R.id.user_local_view);
            SurfaceView localView = rtcEngine.CreateRendererView(getApplicationContext());
            this.mLocalView = localView;
            localViewContainer.addView(localView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            localView.setZOrderOnTop(true);
            localView.setZOrderMediaOverlay(true);
            rtcEngine.enableVideo();
            rtcEngine.setupLocalVideo(new VideoCanvas(this.mLocalView));
        }

    }

    /**
     * Initialize views and its listeners
     */
    void initViews() {

        // muter
//        CheckBox muter = (CheckBox) findViewById(R.id.action_muter);
//        muter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean mutes) {
//
//                rtcEngine.muteLocalAudioStream(mutes);
//                compoundButton.setBackgroundResource(mutes ? R.drawable.ic_room_mute_pressed : R.drawable.ic_room_mute);
//
//            }
//        });
//
//        // speaker
//        CheckBox speaker = (CheckBox) findViewById(R.id.action_speaker);
//        speaker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean usesSpeaker) {
//
//                rtcEngine.setEnableSpeakerphone(usesSpeaker);
//                compoundButton.setBackgroundResource(usesSpeaker ? R.drawable.ic_room_loudspeaker : R.drawable.ic_room_loudspeaker_pressed);
//
//            }
//        });
//
//        // setup states of action buttons
//        muter.setChecked(false);
//        speaker.setChecked(true);


    }


    /**
     * 切换视频音频通话时，更新 view 的显示。只是更新重用的 view，并不新添加。
     *
     * @param callingType
     */
    void updateRemoteUserViews(int callingType) {


//        for (int i = 0, size = mRemoteUserContainer.getChildCount(); i < size; i++) {
//
//            View singleRemoteView = mRemoteUserContainer.getChildAt(i);
//            singleRemoteView.findViewById(R.id.remote_user_voice_container).setVisibility(visibility);

//            if (CALLING_TYPE_VIDEO == callingType) {
//                // re-setup remote video
//
//                FrameLayout remoteVideoUser = (FrameLayout) singleRemoteView.findViewById(R.id.viewlet_remote_video_user);
//                // ensure remote video view setup
//                if(remoteVideoUser.getChildCount()>0) {
//                    final SurfaceView remoteView = (SurfaceView) remoteVideoUser.getChildAt(0);
//                    if(remoteView!=null) {
//                        remoteView.setZOrderOnTop(true);
//                        remoteView.setZOrderMediaOverlay(true);
//                        int savedUid = (Integer) remoteVideoUser.getTag();
//                        log("saved uid: " + savedUid);
//                        rtcEngine.setupRemoteVideo(new VideoCanvas(remoteView, VideoCanvas.RENDER_MODE_ADAPTIVE, savedUid));
//                    }
//                }
//
//            }
//        }
    }

    @Override
    public void onUserInteraction() {

        // enable video call
        ensureLocalViewIsCreated();

        rtcEngine.enableVideo();  //chongfu
        rtcEngine.muteLocalVideoStream(false);
        rtcEngine.muteLocalAudioStream(false);
        rtcEngine.muteAllRemoteVideoStreams(false);
//123123123s
        // join video call
//                if (mRemoteUserContainer.getChildCount() == 0) {
        this.setupChannel();
//                }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {

        new Thread(new Runnable() {

            @Override
            public void run() {

                new AlertDialog.Builder(ChannelActivity.this)
                        .setTitle("是否退出远程视频")
                        .setPositiveButton("是",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        // TODO Auto-generated method stub
                                        rtcEngine.leaveChannel();
                                    }
                                })
                        .setNegativeButton("否",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        // TODO Auto-generated method stub

                                    }
                                }).show();
            }
        }).run();

        // keep screen on - turned off
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public synchronized void onFirstRemoteVideoDecoded(final int uid, int width, int height, final int elapsed) {

        log("onFirstRemoteVideoDecoded: uid: " + uid + ", width: " + width + ", height: " + height);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {

//                View remoteUserView = mRemoteUserContainer.findViewById(Math.abs(uid));

                // ensure container is added
//                if (remoteUserView == null) {
//
//                    LayoutInflater layoutInflater = getLayoutInflater();
//
//                    View singleRemoteUser = layoutInflater.inflate(R.layout.viewlet_remote_user, null);
//                    singleRemoteUser.setId(Math.abs(uid));
//
//                    TextView username = (TextView) singleRemoteUser.findViewById(R.id.remote_user_name);
//                    username.setText(String.valueOf(uid));
//
//                    mRemoteUserContainer.addView(singleRemoteUser, new LinearLayout.LayoutParams(mRemoteUserViewWidth, mRemoteUserViewWidth));
//
//                    remoteUserView = singleRemoteUser;
//                }
                FrameLayout remoteVideoUser = (FrameLayout) findViewById(R.id.user_remote_view);


                remoteVideoUser.removeAllViews();
                // ensure remote video view setup
                final SurfaceView remoteView = RtcEngine.CreateRendererView(getApplicationContext());
                remoteVideoUser.addView(remoteView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                rtcEngine.enableVideo();
                int successCode = rtcEngine.setupRemoteVideo(new VideoCanvas(remoteView, VideoCanvas.RENDER_MODE_HIDDEN, uid));

                if (successCode < 0) {
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rtcEngine.setupRemoteVideo(new VideoCanvas(remoteView, VideoCanvas.RENDER_MODE_HIDDEN, uid));
                            remoteView.invalidate();
                        }
                    }, 500);
                }
                // app hints before you join
                TextView appNotification = (TextView) findViewById(R.id.app_notification);
                appNotification.setText("");

            }
        });

    }

    public synchronized void onUserJoined(final int uid, int elapsed) {

        log("onUserJoined:   new  user   uid: " + uid);

//        View existedUser = mRemoteUserContainer.findViewById(Math.abs(uid));
//        if (existedUser != null) {
//            // user view already added
//            return;
//        }
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//
//                // Handle the case onFirstRemoteVideoDecoded() is called before onUserJoined()
//                View singleRemoteUser = mRemoteUserContainer.findViewById(Math.abs(uid));
//                if (singleRemoteUser != null) {
//                    return;
//                }
//
//                LayoutInflater layoutInflater = getLayoutInflater();
//                singleRemoteUser = layoutInflater.inflate(R.layout.viewlet_remote_user, null);
//                singleRemoteUser.setId(Math.abs(uid));
//
//                TextView username = (TextView) singleRemoteUser.findViewById(R.id.remote_user_name);
//                username.setText(String.valueOf(uid));
//
//                mRemoteUserContainer.addView(singleRemoteUser, new LinearLayout.LayoutParams(mRemoteUserViewWidth, mRemoteUserViewWidth));
//
//
//                // app hints before you join
//                TextView appNotification = (TextView) findViewById(R.id.app_notification);
//                appNotification.setText("");
//                setRemoteUserViewVisibility(true);
//
//            }
//        });
    }

    public void onUserOffline(final int uid) {

        log("onUserOffline: uid: " + uid);

        if (isFinishing()) {
            return;
        }

//        if(mRemoteUserContainer==null){
//            return;
//        }

        runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              FrameLayout remoteVideoUser = (FrameLayout) findViewById(R.id.user_remote_view);
                              remoteVideoUser.removeAllViews();


                              TextView appNotification = (TextView) findViewById(R.id.app_notification);
                              appNotification.setText(R.string.room_prepare);
                          }
                      }


        );
    }


    @Override
    public void finish() {

        if (alertDialog != null) {
            alertDialog.dismiss();
        }

        super.finish();
    }

    @Override
    public void onLeaveChannel(IRtcEngineEventHandler.RtcStats stats) {
        try {
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onUserMuteVideo(final int uid, final boolean muted) {

        log("onUserMuteVideo uid: " + uid + ", muted: " + muted);

//        if(isFinishing()){
//            return;
//        }
//
//        if(mRemoteUserContainer==null){
//            return;
//        }
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//                View remoteView = mRemoteUserContainer.findViewById(Math.abs(uid));
//                remoteView.findViewById(R.id.remote_user_voice_container).setVisibility(
//                        (CALLING_TYPE_VOICE==mCallingType || (CALLING_TYPE_VIDEO==mCallingType && muted))
//                                ? View.VISIBLE
//                                : View.GONE);
//                remoteView.invalidate();
//            }
//        });

    }

    @Override
    public synchronized void onError(int err) {


        if (isFinishing()) {
            return;
        }


        // incorrect vendor key
        if (101 == err) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (alertDialog != null) {
                        return;
                    }

                    alertDialog = new AlertDialog.Builder(ChannelActivity.this).setCancelable(false)
                            .setMessage(getString(R.string.error_101))
                            .setPositiveButton(getString(R.string.error_confirm), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    // Go to login
//                                    Intent toLogin = new Intent(ChannelActivity.this, LoginActivity.class);
//                                    toLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                                    startActivity(toLogin);

                                    rtcEngine.leaveChannel();

                                }
                            }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialogInterface) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .create();

                    alertDialog.show();
                }
            });


        }

        // no network connection
        if (104 == err) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView appNotification = (TextView) findViewById(R.id.app_notification);
                    appNotification.setText(R.string.network_error);
                }
            });
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((AgoraApplication) getApplication()).setEngineEventHandlerActivity(null);
    }


    @Override
    //yangxun 按home键后 释放camera
    protected void onStop() {
        super.onStop();
        rtcEngine.disableVideo();
    }

    //yangxun
    protected void onResume() {
        super.onStop();
        rtcEngine.enableVideo();
    }

    public void clickButton(View v) {
        switch (v.getId()) {
            case R.id.hung_up:
                onBackPressed();
                break;
            default:
                break;
        }
    }
}