package com.paquete.produccion;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProductionDashboardActivity extends AppCompatActivity {
    private EditText etComponentNumber;
    private Button btnSendRequest;
    private TextView tvNotifications;

    // This would be replaced with a proper database or shared preferences in a real app
    public static boolean requestAccepted = false;
    public static boolean requestRejected = false;
    public static boolean requestPending = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_dashboard);

        etComponentNumber = findViewById(R.id.etComponentNumber);
        btnSendRequest = findViewById(R.id.btnSendRequest);
        tvNotifications = findViewById(R.id.tvNotifications);

        btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String componentNumber = etComponentNumber.getText().toString();

                if (componentNumber.isEmpty()) {
                    Toast.makeText(ProductionDashboardActivity.this, "Ingrese un número de componente", Toast.LENGTH_SHORT).show();
                    return;
                }

                // In a real app, this would send the request to a server or database
                MaterialsDashboardActivity.pendingComponentRequest = componentNumber;
                requestPending = true;
                requestAccepted = false;
                requestRejected = false;

                Toast.makeText(ProductionDashboardActivity.this, "Solicitud enviada", Toast.LENGTH_SHORT).show();
                etComponentNumber.setText("");
            }
        });

        // Start a thread to check for notifications
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000); // Check every second
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateNotifications();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void updateNotifications() {
        if (requestAccepted) {
            tvNotifications.setText("Solicitud aceptada por Materiales");
            tvNotifications.setVisibility(View.VISIBLE);
            requestAccepted = false;
        } else if (requestRejected) {
            tvNotifications.setText("Solicitud rechazada por Materiales");
            tvNotifications.setVisibility(View.VISIBLE);
            requestRejected = false;
        } else if (!requestPending) {
            tvNotifications.setVisibility(View.GONE);
        }
    }
}