package com.mycamera;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    
    private String TAG = this.getClass().getName();

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;

        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
        	mCamera.setDisplayOrientation(90);
        	mCamera.setPreviewDisplay(holder);
            
            Camera.Parameters parameters = mCamera.getParameters();
            
            //make preview fit to our screen size
            List<Size> size = parameters.getSupportedPreviewSizes();
            parameters.setPreviewSize(size.get(0).width, size.get(0).height);
            
            //set auto-focus on
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            
            mCamera.setParameters(parameters);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    	// release camera for other activities
    	if (mCamera != null){
            mCamera.release();
            mCamera = null;
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
    }
}