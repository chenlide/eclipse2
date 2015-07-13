package com.example.receiver;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpReceiver {
	private static TcpReceiver mReceiver;

	private ServerSocket mServerSocket;

	private int port = 8180;

	private TcpReceiver() {
		//777
		initServer();
	}

	public static TcpReceiver getInstance() {
		if (mReceiver == null) {
			mReceiver = new TcpReceiver();
		}
		return mReceiver;
	}

	private void initServer() {
		try {
			mServerSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void openServer() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					BufferedInputStream bis=null;

					FileOutputStream fos=null;
					try {
						Socket socket = mServerSocket.accept();
						InputStream is = socket.getInputStream();

						bis = new BufferedInputStream(is);

						fos = new FileOutputStream(new File(
								"/sdcard/receive.mp4"));
						byte[] b = new byte[1024];
						int len = 0;
						while ((len = bis.read(b)) > 0) {
							fos.write(b, 0, len);
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						if(fos!=null){
							try {
								fos.close();
								fos=null;
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						if(bis!=null){
							try {
								bis.close();
								bis=null;
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					
						}
					}
				}
			}
		}).start();

	}
	//hehe
	//test

}
