package com.hp.playmusic;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity implements SensorEventListener {
	ListView lv;
	CheckBox cb;
	static SeekBar sb1,sb2;
	static Button b;
	AudioManager am;
	Sensor s;
	SensorManager sm;
	Intent i;
	static String song[]={"A Sky Full Of Stars" , "Paradise" , "Yellow", "Hymn For The Weekend","Adventure Of A Lifetime","Magic"};
	static int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=(ListView) findViewById(R.id.listView1);
        cb=(CheckBox) findViewById(R.id.checkBox1);
        sb1=(SeekBar) findViewById(R.id.seekBar2);
        sb2=(SeekBar) findViewById(R.id.seekBar1);
        b=(Button) findViewById(R.id.button4);
        sm=(SensorManager) getSystemService(SENSOR_SERVICE);
        s=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,song);
        lv.setAdapter(adapter);
        am=(AudioManager) getSystemService(AUDIO_SERVICE);
        int max=am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int current=am.getStreamVolume(AudioManager.STREAM_MUSIC);
        sb2.setMax(max);
        sb2.setProgress(current);
        sb2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
			}
		});
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(serve.mp!=null)
				{
					serve.mp.release();
					serve.mp=null;
				}					
				id=arg2;
				play();
			}
        	
		});
        sb1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if(serve.mp!=null)
					serve.mp.seekTo(progress);
				
			}
		});
        /* Thread t=new Thread() {
        	public void run()
        	{
        		super.run();
        		while(true)
        		{
        			if(serve.mp==null)
        				sb1.setProgress(0);
        			else if(serve.mp!=null)
        			{
        				sb1.setProgress(serve.mp.getCurrentPosition());
        			}
        		}
        	}
		};
		t.start();        */
    }
    public void playb(View v)
    {
    	if(b.getText().toString().equals("Play"))
    	{
    		play();
    	}
    	else
    	{
    		i=new Intent(this,serve.class);
        	i.putExtra("key", "pause");
        	startService(i);
    	}
    }

    public void stopb(View v)
    {
    	i=new Intent(this,serve.class);
    	i.putExtra("key", "stop");
    	startService(i);
    }
    
    public void prevb(View v)
    {
	    prev();    	
    }
    
    public void nextb(View v)
    {
    	next();    	
    }
    
    public void play() {
		// TODO Auto-generated method stub
    	i=new Intent(this,serve.class);
    	i.putExtra("key", "play");
    	startService(i);
    	Intent i1=new Intent(this,notif.class);
    	startService(i1);
	}
    public void prev()
    {
    	if(serve.mp!=null)
		{
			serve.mp.release();
			serve.mp=null;
		}
		if(id==0)
			id=song.length-1;
		else
			id--;
		play();
    }
    public void next()
    {
    	if(serve.mp!=null)
		{
			serve.mp.release();
			serve.mp=null;
		}
    	if(id==(song.length-1))
    		id=0;
    	else id++;
    	play();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sm.registerListener(this, s,SensorManager.SENSOR_DELAY_NORMAL);
		}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		sm.unregisterListener(this);
	}
	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		float f[]=event.values;
		if(f[0]<-8)
			next();
		else if(f[0]>8)
			prev();
		else if(f[1]<-3)
			sb2.setProgress((int) (am.getStreamVolume(AudioManager.STREAM_MUSIC)+(f[1]/8)));
		else if(f[1]>3)
			sb2.setProgress((int) (am.getStreamVolume(AudioManager.STREAM_MUSIC)+(f[1]/8)));
		
	}    
}