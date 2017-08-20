package com.hp.playmusic;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class notif extends Service {
Notification n;
NotificationManager nm;
Notification.Builder nb;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		nm=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nb=new Notification.Builder(this);
		Intent i1=new Intent(this,serve.class);
		i1.putExtra("key", "play");
		Intent i2=new Intent(this,serve.class);
		i2.putExtra("key", "pause");
		Intent i3=new Intent(this,serve.class);
		i3.putExtra("key", "stop");
		PendingIntent pi1=PendingIntent.getService(this, 1, i1, PendingIntent.FLAG_UPDATE_CURRENT);
    	PendingIntent pi2=PendingIntent.getService(this, 2, i2, PendingIntent.FLAG_UPDATE_CURRENT);
    	PendingIntent pi3=PendingIntent.getService(this, 3, i3, PendingIntent.FLAG_UPDATE_CURRENT);
    	nb.setSmallIcon(R.drawable.ic_launcher);
    	nb.setTicker("Playing Music");
    	nb.setContentTitle(MainActivity.song[MainActivity.id]);
    	nb.setContentText("Coldplay");
    	nb.addAction(android.R.drawable.ic_media_play, "Play", pi1);
    	nb.addAction(android.R.drawable.ic_media_pause, "Pause", pi2);
    	nb.addAction(android.R.drawable.ic_media_play, "Stop", pi3);
    	n=nb.build();
    	nm.notify(1,n);
		return super.onStartCommand(intent, flags, startId);
	}

}
