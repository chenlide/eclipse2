package com.example.receiver;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends Activity implements OnClickListener{

	private Button mReceive;

	private VideoView mVideoView;
	
	private TextView mAddress;
	
	private ImageView image;

	private MediaController mMediaController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		addListener();
		
		setIp();
		openSocket();
	}
	
	private void setIp(){
		mAddress.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mAddress.setText(getAddress()+":8180");//192.168.94.108
			}
		});
	
	}
	
	private void openSocket(){
		TcpReceiver.getInstance().openServer();
	}

	private void initView() {
		mReceive = (Button) findViewById(R.id.receive);

		mVideoView = (VideoView) findViewById(R.id.video_view);
		
		mAddress=(TextView)findViewById(R.id.address);
		
	

		mMediaController = new MediaController(this);

		mVideoView.setMediaController(mMediaController);

		mMediaController.setMediaPlayer(mVideoView);
	}

	private void playVideo() {
		File file = new File("/sdcard/receive.mp4");
		mVideoView.setVideoPath(file.getAbsolutePath());
		mVideoView.start();
	}



	private void addListener() {
		mReceive.setOnClickListener(this);

	}

	private String getAddress() {
		// 获取wifi服务
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		// 判断wifi是否开启
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		String ip = intToIp(ipAddress);
		return ip;
	}

	private String intToIp(int i) {

		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + (i >> 24 & 0xFF);
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.receive:
			playVideo();
			break;
		}

	}

	
}
