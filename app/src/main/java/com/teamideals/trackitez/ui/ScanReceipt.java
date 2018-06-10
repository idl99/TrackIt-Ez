package com.teamideals.trackitez.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.cloud.text.FirebaseVisionCloudDocumentTextDetector;
import com.google.firebase.ml.vision.cloud.text.FirebaseVisionCloudText;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.teamideals.trackitez.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ScanReceipt extends AppCompatActivity {

    private Unbinder unbinder;
    @BindView(R.id.textview_ocrResult)
    TextView mResultText;

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_receipt);
        unbinder = ButterKnife.bind(this);
        scanReceipt();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void scanReceipt(){
        // Handling intent to take snapshot of receipt
        Intent intent = new Intent(this,CameraActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK){
            // Handling uri returned by CameraActivity
            String filePath = data.getExtras().getString("snapshotPath");

            try {

                Bitmap bmp = BitmapFactory.decodeFile(filePath);

                FirebaseVisionImage fbvImage =  FirebaseVisionImage.fromBitmap(bmp);

                OcrDetectionTask ocrDetectionTask = new OcrDetectionTask();

                ocrDetectionTask.execute(fbvImage);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private int getRotationCompensation(String cameraId, Activity activity, Context context)
            throws CameraAccessException {
            // Get the device's current rotation relative to its "native" orientation.
            // Then, from the ORIENTATIONS table, look up the angle the image must be
            // rotated to compensate for the device's rotation.
            int deviceRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
            int rotationCompensation = ORIENTATIONS.get(deviceRotation);

            // On most devices, the sensor orientation is 90 degrees, but for some
            // devices it is 270 degrees. For devices with a sensor orientation of
            // 270, rotate the image an additional 180 ((270 + 270) % 360) degrees.
            CameraManager cameraManager = (CameraManager) context.getSystemService(CAMERA_SERVICE);
            int sensorOrientation = cameraManager
                    .getCameraCharacteristics(cameraId)
                    .get(CameraCharacteristics.SENSOR_ORIENTATION);
            rotationCompensation = (rotationCompensation + sensorOrientation + 270) % 360;

            // Return the corresponding FirebaseVisionImageMetadata rotation value.
            int result;
            switch (rotationCompensation) {
                case 0:
                    result = FirebaseVisionImageMetadata.ROTATION_0;
                    break;
                case 90:
                    result = FirebaseVisionImageMetadata.ROTATION_90;
                    break;
                case 180:
                    result = FirebaseVisionImageMetadata.ROTATION_180;
                    break;
                case 270:
                    result = FirebaseVisionImageMetadata.ROTATION_270;
                    break;
                default:
                    result = FirebaseVisionImageMetadata.ROTATION_0;
                    Log.e("My_Log", "Bad rotation value: " + rotationCompensation);
            }
            return result;
    }

    private void showResult(FirebaseVisionCloudText fbvCloudText){
        String resultText = fbvCloudText.getText();
        mResultText.setText(resultText);
    }

    private class OcrDetectionTask extends AsyncTask<FirebaseVisionImage,Void,Void> {

        @Override
        protected Void doInBackground(FirebaseVisionImage... fbvImages) {
            FirebaseVisionCloudDocumentTextDetector detector = FirebaseVision.getInstance()
                    .getVisionCloudDocumentTextDetector();
            Task<FirebaseVisionCloudText> result = detector.detectInImage(fbvImages[0])
                    .addOnSuccessListener(new OnSuccessListener<FirebaseVisionCloudText>() {
                        @Override
                        public void onSuccess(FirebaseVisionCloudText fbvCloudText) {
                            ScanReceipt.this.showResult(fbvCloudText);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
