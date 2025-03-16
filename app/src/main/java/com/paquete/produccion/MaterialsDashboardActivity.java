package com.paquete.produccion;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MaterialsDashboardActivity extends AppCompatActivity {
    private LinearLayout layoutRequests;
    private TextView tvNoRequests;
    private TextView tvComponentRequest;
    private Button btnAccept, btnReject;

    // This would be replaced with a proper database in a real app
    public static String pendingComponentRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materials_dashboard);

        layoutRequests = findViewById(R.id.layoutRequests);
        tvNoRequests = findViewById(R.id.tvNoRequests);
        tvComponentRequest = findViewById(R.id.tvComponentRequest);
        btnAccept = findViewById(R.id.btnAccept);
        btnReject = findViewById(R.id.btnReject);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pendingComponentRequest != null) {
                    // In a real app, this would update a database
                    ProductionDashboardActivity.requestAccepted = true;
                    ProductionDashboardActivity.requestPending = false;
                    pendingComponentRequest = null;

                    Toast.makeText(MaterialsDashboardActivity.this, "Solicitud aceptada", Toast.LENGTH_SHORT).show();
                    updateRequestsView();
                }
            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pendingComponentRequest != null) {
                    // In a real app, this would update a database
                    ProductionDashboardActivity.requestRejected = true;
                    ProductionDashboardActivity.requestPending = false;
                    pendingComponentRequest = null;

                    Toast.makeText(MaterialsDashboardActivity.this, "Solicitud rechazada", Toast.LENGTH_SHORT).show();
                    updateRequestsView();
                }
            }
        });

        // Start a thread to check for new requests
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000); // Check every second
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateRequestsView();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void updateRequestsView() {
        if (pendingComponentRequest != null) {
            tvNoRequests.setVisibility(View.GONE);
            layoutRequests.setVisibility(View.VISIBLE);
            tvComponentRequest.setText("Solicitud de componente: " + pendingComponentRequest);
        } else {
            tvNoRequests.setVisibility(View.VISIBLE);
            layoutRequests.setVisibility(View.GONE);
        }
    }
}
