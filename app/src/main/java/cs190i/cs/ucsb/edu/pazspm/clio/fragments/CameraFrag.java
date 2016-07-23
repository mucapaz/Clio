package cs190i.cs.ucsb.edu.pazspm.clio.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

import cs190i.cs.ucsb.edu.pazspm.clio.MainActivity;
import cs190i.cs.ucsb.edu.pedro_ulmi.clio.R;
import cs190i.cs.ucsb.edu.pazspm.clio.connection.JSONUtils;

/**
 * Created by pedro_000 on 3/13/2016.
 */
public class CameraFrag extends Fragment implements SurfaceHolder.Callback{
    public CameraFrag() {
    }

    View rootView;
    LinearLayout cameraCapture;

    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    TextView textView;

    PictureCallback rawCallback;
    ShutterCallback shutterCallback;
    PictureCallback jpegCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_camera, container, false);

        textView = (TextView) rootView.findViewById(R.id.capture_text);

        surfaceView = (SurfaceView) rootView.findViewById(R.id.camera_surface_view);
        surfaceHolder = surfaceView.getHolder();

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        surfaceHolder.addCallback(this);

        // deprecated setting, but required on Android versions prior to 3.0
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);

        jpegCallback = new PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                animate();
                ((MainActivity) getActivity()).saveUserImage(data);
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                Bitmap bmap = JSONUtils.getResizedBitmap(bitmap, bitmap.getWidth() / 12, bitmap.getHeight() / 12);

                System.out.println((bmap == null) + " aaaaaaaaa asd a");

                ((MainActivity) getActivity()).bitmapToServer(bmap);

//                FileOutputStream outStream = null;
//                try {
//                    outStream = new FileOutputStream(String.format("/sdcard/%d.jpg", System.currentTimeMillis()));
//                    outStream.write(data);
//                    outStream.close();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                }

                refreshCamera();
            }
        };

        cameraCapture = (LinearLayout) rootView.findViewById(R.id.camera_capture);

        cameraCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(null, null, jpegCallback);
            }
        });

        return rootView;
    }

    private void animate() {
        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
        cameraCapture.startAnimation(shake);
    }

    public void refreshCamera() {
        if (surfaceHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        // start preview with new settings
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {

        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            // open the camera
            camera = Camera.open();
        } catch (RuntimeException e) {
            // check for exceptions
            System.err.println(e);
            return;
        }

        try{
            //when the surface is created, we can set the camera to draw images in this surfaceholder
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        //before changing the application orientation, you need to stop the preview, rotate and then start it again
        if(surfaceHolder.getSurface() == null)//check if the surface is ready to receive camera data
            return;

        Camera.Parameters parameters = camera.getParameters();
        parameters.set("orientation", "portrait");

        camera.setParameters(parameters);

        camera.setDisplayOrientation(90);

        try{
            camera.stopPreview();
        } catch (Exception e){
            //this will happen when you are trying the camera if it's not running
        }

        //now, recreate the camera preview
        try{
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        //our app has only one screen, so we'll destroy the camera in the surface
        //if you are unsing with more screens, please move this code your activity
        camera.stopPreview();
        camera.release();
    }



}
