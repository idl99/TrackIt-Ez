package com.teamideals.trackitez.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.teamideals.trackitez.R;
import com.teamideals.trackitez.services.OcrService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ScanReceipt extends AppCompatActivity {

    private Unbinder unbinder;
    @BindView(R.id.textview_ocrResult)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_receipt);
        unbinder = ButterKnife.bind(this);
        scanReceipt();
    }

    private String processImage(String photoPath){
        String text ="";
        try {
            OcrService ocrService = new OcrService(this,photoPath);
            ocrService.runTextRecognition();
            text = ocrService.getProcessedText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
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
            String photoPath = data.getExtras().getString("photoPath");
            String processedText = processImage(photoPath);
            mTextView.setText(processedText);
        }
    }
}
