package com.example.gamezale.QR;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.gamezale.AddContactActivity;
import com.example.gamezale.Database.Employee;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanQR extends AppCompatActivity {

    private static Employee currentEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Scan a QR");
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(true);
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null) {

            if(result.getContents() == null) {

                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                finish();
            }
            else {

                Intent intent = new Intent(this, AddContactActivity.class);
                intent.putExtra("qrData", result.getContents());
                finish();
                startActivity(intent);
            }
        }
        else {

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
