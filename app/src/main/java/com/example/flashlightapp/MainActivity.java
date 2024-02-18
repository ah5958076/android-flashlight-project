package com.example.flashlightapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String frontCameraId="";
        String backCameraId="";
        Switch frontLight = findViewById(R.id.frontLight);
        TextView frontLightLabel = findViewById(R.id.frontLightLabel);
        Switch backLight = findViewById(R.id.backLight);
        TextView backLightLabel = findViewById(R.id.backLightLabel);
        CameraManager cameraManager = null;


        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
                for (String cameraId : cameraManager.getCameraIdList()) {
                    int cameraFacing = cameraManager.getCameraCharacteristics(cameraId).get(CameraCharacteristics.LENS_FACING);
                    if (cameraFacing == CameraCharacteristics.LENS_FACING_FRONT) {
                        frontCameraId = cameraId;
                    } else if (cameraFacing == CameraCharacteristics.LENS_FACING_BACK) {
                        backCameraId = cameraId;
                    }
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Cannot get flashlights", Toast.LENGTH_SHORT).show();
        }

        if(backCameraId.isEmpty()){
            backLightLabel.setVisibility(View.VISIBLE);
            backLight.setClickable(false);
        }
        if(frontCameraId.isEmpty()){
            frontLightLabel.setVisibility(View.VISIBLE);
            frontLight.setClickable(false);
        }


        CameraManager finalCameraManager = cameraManager;
        String finalFrontCameraId = frontCameraId;
        String finalBackCameraId = backCameraId;
        frontLight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(isChecked){
                        finalCameraManager.setTorchMode(finalBackCameraId, false);
                        backLight.setChecked(false);
                    }
                    finalCameraManager.setTorchMode(finalFrontCameraId, isChecked);
                }
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });



        backLight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(isChecked){
                        finalCameraManager.setTorchMode(finalFrontCameraId, false);
                        frontLight.setChecked(false);
                    }
                    finalCameraManager.setTorchMode(finalBackCameraId, isChecked);
                }
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}