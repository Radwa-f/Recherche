package ma.ensaj.protogrpcApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private GrpcClient grpcClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.convert_button);

        grpcClient = new GrpcClient("192.168.1.2", 9099);

        btn.setOnClickListener(v -> {

            new Thread(() -> {
                testCreateReservation();
                testGetReservation(1);
                testUpdateReservation(1);
                testDeleteReservation(1);
            }).start();
        });


    }

    private void testCreateReservation() {
        grpcClient.createReservation(
                1,
                "2024-12-20",
                "2024-12-25",
                Arrays.asList(5L)
        );
    }

    private void testGetReservation(long reservationId) {
        grpcClient.getReservation(reservationId);
    }

    private void testUpdateReservation(long reservationId) {
        grpcClient.updateReservation(
                reservationId,
                1,
                "2024-12-21",
                "2024-12-26",
                Arrays.asList(6L)
        );
    }

    private void testDeleteReservation(long reservationId) {
        grpcClient.deleteReservation(reservationId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (grpcClient != null) {
            grpcClient.shutdown();
        }
    }
}
