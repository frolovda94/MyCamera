package com.mycamera;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends Activity {

	private Camera mCamera;
    private CameraPreview mPreview;

    private FrameLayout camera_view;
    private Button stop;
	private Button start;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        camera_view = (FrameLayout) findViewById(R.id.camera_preview);
        start = (Button) findViewById(R.id.start_button);
        stop = (Button) findViewById(R.id.stop_button);
        
        start.setVisibility(View.VISIBLE);
    	stop.setVisibility(View.GONE);
        
        start.setOnClickListener(new View.OnClickListener() {
			// show camera preview on "start" click
			@Override
			public void onClick(View v) {
				try{
		        	mCamera = Camera.open();
		        	mPreview = new CameraPreview(MainActivity.this, mCamera);
		        	camera_view.addView(mPreview);
		        	start.setVisibility(View.GONE);
		        	stop.setVisibility(View.VISIBLE);
		        } catch(Exception e){
		        	cameraIsUnavalible();
		        }
			}
		});
        
        stop.setOnClickListener(new View.OnClickListener() {
        	// release camera and clear preview on "stop" click
			@Override
			public void onClick(View v) {
				camera_view.removeAllViews();  //camera will be released by surfaceDestroyed method
		        start.setVisibility(View.VISIBLE);
		        stop.setVisibility(View.GONE);
			}
        });
        
    }
    
    @Override
    public void onPause() {
        super.onPause();
        camera_view.removeAllViews(); //camera will be released by surfaceDestroyed method
        start.setVisibility(View.VISIBLE);
        stop.setVisibility(View.GONE);
    }
    
    private void cameraIsUnavalible(){
    	// in case camera is not supported or busy, show an alert dialog
    	AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
    	builder.setTitle(getString(R.string.alert_text))
    			.setCancelable(true)
    			.setNeutralButton("Ok",
    					new DialogInterface.OnClickListener() {
    						public void onClick(DialogInterface dialog, int id) {
    							dialog.cancel();
    						}
    					});
    	AlertDialog alert = builder.create();
    	alert.show();
    }
    
}
