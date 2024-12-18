package ma.ensa.full_backend.controller;

import ma.ensa.full_backend.dto.ChambreDTO;
import ma.ensa.full_backend.dto.ReservationDTO;
import ma.ensa.full_backend.model.Client;
import ma.ensa.full_backend.model.Reservation;
import ma.ensa.full_backend.service.ReservationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class RestReservationController {
    @Autowired
    private ReservationService service;

    @PostMapping
    public Reservation create(
            @RequestBody ReservationRequest reservationRequest
    ) {
        return service.createReservation(
                reservationRequest.getReservation(),
                reservationRequest.getChambreIds()
        );
    }

    @PutMapping("/{id}")
    public Reservation update(
            @PathVariable Long id,
            @RequestBody ReservationRequest reservationRequest
    ) {
        return service.updateReservation(
                id,
                reservationRequest.getReservation(),
                reservationRequest.getChambreIds()
        );
    }

    @GetMapping("/{id}")
    public Reservation read(@PathVariable Long id) {
        return service.getReservation(id);
    }
    @GetMapping("/dummy/{id}")
    public ReservationDTO dummyGetReservation(@PathVariable Long id) {
        // Construct a ReservationDTO with dummy data
        ReservationDTO dto = new ReservationDTO();
        dto.setId(id);
        dto.setCheckInDate(new Date());
        dto.setCheckOutDate(new Date());
        dto.setClient(new Client(1L, "John", "Doe", "john.doe@example.com", "1234567890"));

        // Fill up chambres with dummy data
        List<ChambreDTO> dummyChambres = new ArrayList<>();
        for (int i = 0; i < 1663; i++) { // Adjust this number to reach 1 KB
            dummyChambres.add(new ChambreDTO(
                    (long) i,
                    "DUMMY_TYPE",
                    123.45f,
                    true
            ));
        }
        dto.setChambres(dummyChambres);

        // Add padding string to reach exact size
        StringBuilder padding = new StringBuilder();
        while (padding.length() < 465) { // Adjust to fill remaining bytes
            padding.append("PAD_DATA_");
        }
        dto.setPadding(padding.toString());

        return dto;
    }
    @PostMapping("/dummy")
    public ReservationDTO dummyCreate(@RequestBody ReservationRequest reservationRequest) {
        ReservationDTO dto = new ReservationDTO();
        dto.setId(1L);
        dto.setCheckInDate(new Date());
        dto.setCheckOutDate(new Date());
        dto.setClient(new Client(1L, "John", "Doe", "john.doe@example.com", "1234567890"));

        // Fill dummy chambres
        List<ChambreDTO> dummyChambres = new ArrayList<>();
        for (int i = 0; i < 2; i++) { // Adjust to achieve 1 KB
            dummyChambres.add(new ChambreDTO(
                    (long) i,
                    "DUMMY_TYPE",
                    123.45f,
                    true
            ));
        }
        dto.setChambres(dummyChambres);

        // Add padding to reach exact size
        StringBuilder padding = new StringBuilder();
        while (padding.length() < 410) { // Adjust as needed
            padding.append("PAD_DATA_");
        }
        dto.setPadding(padding.toString());

        return dto;
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    // Static inner class to handle request with both Reservation and Chamber IDs
    public static class ReservationRequest {
        private Reservation reservation;
        private List<Long> chambreIds;

        public Reservation getReservation() {
            return reservation;
        }

        public void setReservation(Reservation reservation) {
            this.reservation = reservation;
        }

        public List<Long> getChambreIds() {
            return chambreIds;
        }

        public void setChambreIds(List<Long> chambreIds) {
            this.chambreIds = chambreIds;
        }
    }
}