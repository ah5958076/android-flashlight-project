package com.example.flashlightapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;
import android.Manifest;

public class MainActivity extends AppCompatActivity {

    @Override
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Switch backLight = findViewById(R.id.backLight);
        Switch frontLight = findViewById(R.id.frontLight);
        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);


        backLight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cameraManager.setTorchMode("0", isChecked);
                }else{
                    Camera camera = Camera.open(0);
                    Camera.Parameters parameters = camera.getParameters();
                    if(isChecked)
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                    else
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    camera.setParameters(parameters);
                    camera.startPreview();
                }
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        frontLight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cameraManager.setTorchMode("1", isChecked);
                }else{
                    Camera camera = Camera.open(1);
                    Camera.Parameters parameters = camera.getParameters();
                    if(isChecked)
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                    else
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    camera.setParameters(parameters);
                    camera.startPreview();
                }
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}