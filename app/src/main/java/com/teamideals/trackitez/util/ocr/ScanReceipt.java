package com.teamideals.trackitez.util.ocr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.cloud.text.FirebaseVisionCloudDocumentTextDetector;
import com.google.firebase.ml.vision.cloud.text.FirebaseVisionCloudText;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.teamideals.trackitez.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ScanReceipt extends AppCompatActivity {

    private Unbinder unbinder;
    @BindView(R.id.textview_ocrResult)
    TextView mResultText;

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

    private void scanReceipt() {
        // Handling intent to take snapshot of receipt
        Intent intent = new Intent(this, CameraActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Handling uri returned by CameraActivity
            String filePath = data.getExtras().getString("snapshotPath");

            try {

                Bitmap bmp = BitmapFactory.decodeFile(filePath);

                FirebaseVisionImage fbvImage = FirebaseVisionImage.fromBitmap(bmp);

                OcrDetectionTask ocrDetectionTask = new OcrDetectionTask();

                ocrDetectionTask.execute(fbvImage);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void showResult(FirebaseVisionCloudText fbvCloudText) {
        String resultText = fbvCloudText.getText();
        mResultText.setText(resultText);
    }

    private class OcrDetectionTask extends AsyncTask<FirebaseVisionImage, Void, Void> {

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
