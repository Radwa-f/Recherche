package ma.ensaj.protogrpcApp;

import android.util.Log;

import ensaj.protogrpcApp.reservation.*;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GrpcClient {

    private ManagedChannel channel;
    private ReservationServiceGrpc.ReservationServiceBlockingStub stub;

    public GrpcClient(String host, int port) {
        try {
            channel = ManagedChannelBuilder.forAddress(host, port)
                    .usePlaintext()
                    .keepAliveTimeout(30, TimeUnit.SECONDS)
                    .keepAliveTime(30, TimeUnit.SECONDS)
                    .build();

            stub = ReservationServiceGrpc.newBlockingStub(channel);

        } catch (Exception e) {
            Log.e("GrpcClient Error", "Error initializing gRPC client: " + e.getMessage(), e);
            throw new RuntimeException("Failed to configure gRPC client", e);
        }
    }

    public CreateReservationResponse createReservation(long clientId, String checkInDate, String checkOutDate, List<Long> chambreIds) {
        try {
            CreateReservationRequest request = CreateReservationRequest.newBuilder()
                    .setClientId(clientId)
                    .setCheckInDate(checkInDate)
                    .setCheckOutDate(checkOutDate)
                    .addAllChambreIds(chambreIds)
                    .build();

            CreateReservationResponse response = stub.createReservation(request);
            Log.d("GrpcClient", "Reservation created: ID = " + response.getId());
            return response;
        } catch (StatusRuntimeException e) {
            Log.e("GrpcClient Error", "Error during CreateReservation: " + e.getMessage(), e);
            return null;
        }
    }

    public GetReservationResponse getReservation(long reservationId) {
        try {
            GetReservationRequest request = GetReservationRequest.newBuilder()
                    .setId(reservationId)
                    .build();

            GetReservationResponse response = stub.getReservation(request);
            Log.d("GrpcClient", "Reservation retrieved: " + response.getReservation());
            return response;
        } catch (StatusRuntimeException e) {
            Log.e("GrpcClient Error", "Error during GetReservation: " + e.getMessage(), e);
            return null;
        }
    }

    public UpdateReservationResponse updateReservation(long reservationId, long clientId, String checkInDate, String checkOutDate, List<Long> chambreIds) {
        try {
            UpdateReservationRequest request = UpdateReservationRequest.newBuilder()
                    .setId(reservationId)
                    .setClientId(clientId)
                    .setCheckInDate(checkInDate)
                    .setCheckOutDate(checkOutDate)
                    .addAllChambreIds(chambreIds)
                    .build();

            UpdateReservationResponse response = stub.updateReservation(request);
            Log.d("GrpcClient", "Reservation updated: " + response.getReservation());
            return response;
        } catch (StatusRuntimeException e) {
            Log.e("GrpcClient Error", "Error during UpdateReservation: " + e.getMessage(), e);
            return null;
        }
    }

    public DeleteReservationResponse deleteReservation(long reservationId) {
        try {
            DeleteReservationRequest request = DeleteReservationRequest.newBuilder()
                    .setId(reservationId)
                    .build();

            DeleteReservationResponse response = stub.deleteReservation(request);
            Log.d("GrpcClient", "Reservation deleted: " + response.getMessage());
            return response;
        } catch (StatusRuntimeException e) {
            Log.e("GrpcClient Error", "Error during DeleteReservation: " + e.getMessage(), e);
            return null;
        }
    }

    public void shutdown() {
        if (channel != null && !channel.isShutdown()) {
            channel.shutdown();
        }
    }
}
