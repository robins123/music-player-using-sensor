package com.hp.playmusic;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class serve extends Service{
	static MediaPlayer mp;
	int songs[]={R.raw.a,R.raw.paradise,R.raw.yellow,R.raw.hftw,R.raw.adventure,R.raw.magic};
	String s2="";
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		String s=intent.getStringExtra("key").toString();
		//String s2=intent.getStringExtra("nf").toString();
		if(s.equals("play") || s2.equals("play"))
			play();
		else if(s.equals("pause") || s2.equals("pause"))
			pause();
		else if(s.equals("stop") || s2.equals("stop"))
			stop();
		return super.onStartCommand(intent, flags, startId);
	}
	public void play()
	{
		if(mp==null)
		{
			//Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
			mp=MediaPlayer.create(serve.this, songs[MainActivity.id]);
			MainActivity.sb1.setMax(mp.getDuration());
		}
		mp.start();
		MainActivity.b.setText("Pause");
	}
	public void pause()
	{
		if(mp!=null)
		{
			mp.pause();
			MainActivity.b.setText("Play");
		}
	}
	public void stop()
	{
		if(mp!=null)
		{
			mp.stop();
			mp.release();
			mp=null;
			MainActivity.b.setText("Play");
		}
	}
}