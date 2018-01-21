package com.qyh.coderepository.menu;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.AlphabeticIndex;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.qyh.coderepository.R;

/**
 * @author 邱永恒
 * @time 2018/1/21  21:25
 * @desc $TODO$
 */


public class BLEActivity extends AppCompatActivity{
    AudioManager mAm;
    InavrSR inavrSR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAm = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mAm.setMode(AudioManager.MODE_IN_COMMUNICATION);
        initSCO();
        initBlueToothHeadset();

    }

    private BluetoothHeadset bluetoothHeadset;
    private BluetoothDevice bluetoothDevice;
    BluetoothProfile.ServiceListener blueHeadsetListener = new BluetoothProfile.ServiceListener() {

        @Override
        public void onServiceDisconnected(int profile) {
            Log.i("blueHeadsetListener>>", "onServiceDisconnected:" + profile);
            if (profile == BluetoothProfile.HEADSET) {
                if (bluetoothHeadset != null) {
                    bluetoothHeadset.stopVoiceRecognition(bluetoothDevice);
                }
                mAm.stopBluetoothSco();
                mAm.setBluetoothScoOn(false);
                bluetoothHeadset = null;
            }
            //            initBlueToothHeadset();
        }

        @Override
        public void onServiceConnected(int profile, BluetoothProfile proxy) {
            Log.i("blueHeadsetListener", "onServiceConnected:" + profile);
            if (profile == BluetoothProfile.HEADSET) {
                bluetoothHeadset = (BluetoothHeadset) proxy;
                scoEnable();
                Log.i("sco", mAm.isBluetoothA2dpOn() + "," + mAm.isBluetoothScoOn());
                if (bluetoothHeadset.getConnectedDevices().size() > 0) {
                    bluetoothDevice = bluetoothHeadset.getConnectedDevices().get(0);
                    Log.i("Main2Activity:", bluetoothHeadset.startVoiceRecognition(bluetoothDevice) + "");

                }
            }
        }
    };

    /**
     * 初始化蓝牙连接为HEADSET
     */
    private void initBlueToothHeadset() {
        BluetoothAdapter adapter;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {//android4.3之前直接用BluetoothAdapter.getDefaultAdapter()就能得到BluetoothAdapter
            adapter = BluetoothAdapter.getDefaultAdapter();
        } else {
            BluetoothManager bm = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            adapter = bm.getAdapter();

        }
        adapter.getProfileProxy(this, blueHeadsetListener, BluetoothProfile.HEADSET);
    }

    public void record() {
        final Player player = new Player();
        AlphabeticIndex.Record re = new AlphabeticIndex.Record(new IReward() {
            @Override
            public void onReward(byte[] bytes, int i, int i1) {
                player.play(bytes);
            }

            @Override
            public void onError(RewardException e) {
                inavrSR.getWebsocketManager().newSocketConnection();
            }
        });

        re.startRecord();
    }

    /* Broadcast receiver for the SCO State broadcast intent.*/
    private final BroadcastReceiver mSCOHeadsetAudioState = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.bluetooth.device.action.ACL_CONNECTED")) {
                initBlueToothHeadset();
            } else {
                //if(DEBUG)
                int state = intent.getIntExtra(AudioManager.EXTRA_SCO_AUDIO_STATE, -1);
                Log.e("SCO", " mSCOHeadsetAudioState--->onReceive:" + state);
                if (state == AudioManager.SCO_AUDIO_STATE_CONNECTED) {
                    Log.i("SCO", "SCO_AUDIO_STATE_CONNECTED");
                    record();
                    unregisterReceiver(mSCOHeadsetAudioState);
                } else if (state == AudioManager.SCO_AUDIO_STATE_DISCONNECTED) {
                    Log.i("SCO  ", "SCO_AUDIO_STATE_DISCONNECTED");
                }
            }

        }
    };

    /**
     * 接收SCO状态改变广播
     */
    private void initSCO() {
        IntentFilter newintent = new IntentFilter();
        newintent.addAction(AudioManager.ACTION_SCO_AUDIO_STATE_UPDATED);
        newintent.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        registerReceiver(mSCOHeadsetAudioState, newintent);
    }

    /**
     * 如果SCO已经打开了, 重启SCO
     */
    private void scoEnable() {
        if (mAm.isBluetoothScoOn()) {
            mAm.stopBluetoothSco();
            mAm.setBluetoothScoOn(false);
        }

        mAm.setBluetoothScoOn(true);
        mAm.startBluetoothSco();
    }
}
