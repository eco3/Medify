package de.mobilecomputing.ekrememre.medify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.BarcodeFormat;

import java.util.ArrayList;
import java.util.Arrays;

public class BarcodeScanActivity extends AppCompatActivity {
    private static final String TAG = "BarcodeScanActivity";

    private static final int CAMERA_REQUEST_CODE = 34;
    public static String EXTRA_BARCODE_RESULT = "EXTRA_BARCODE_RESULT";

    private CodeScanner mCodeScanner;

    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scan);

        rootView = findViewById(R.id.scanner_root);

        if (!checkCameraPermissions()) {
            requestCameraPermissions();
        } else {
            findAndOpenCamera();
        }
    }

    private void findAndOpenCamera() {
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setFormats(new ArrayList<>(Arrays.asList(BarcodeFormat.EAN_8, BarcodeFormat.EAN_13)));
        mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_BARCODE_RESULT, result.getText());

            setResult(RESULT_OK, intent);
            finish();
        }));
        scannerView.setOnClickListener(view -> mCodeScanner.startPreview());
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (checkCameraPermissions()) {
            mCodeScanner.startPreview();
        }
    }

    @Override
    protected void onPause() {
        if (checkCameraPermissions()) {
            mCodeScanner.releaseResources();
        }

        super.onPause();
    }

    private boolean checkCameraPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if (permissionState != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "CAMERA permission has NOT been granted.");
            return false;
        } else {
            Log.i(TAG, "CAMERA permission has already been granted.");
            return true;
        }
    }

    private void requestCameraPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Log.i(TAG, "Displaying camera permission rationale to provide additional context.");
            // Request Camera permission
            Snackbar.make(rootView, R.string.camera_permission, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, view -> ActivityCompat.requestPermissions(BarcodeScanActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE))
                    .show();
        } else {
            Log.i(TAG, "Requesting camera permission");
            // Request Camera permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(BarcodeScanActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted.
                findAndOpenCamera();
            } else {
                // Permission denied.
                Snackbar.make(rootView, R.string.camera_denied, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.settings, (View.OnClickListener) view -> {
                        // Build intent that displays the App settings screen.
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
                        intent.setData(uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    })
                    .show();
            }
        }
    }
}