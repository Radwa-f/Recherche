package ma.ensa.full_backend.dto;

import lombok.Data;

@Data
public class ChambreDTO {
    private Long id;
    private String typeChambre;
    private float prix;
    private boolean disponible;

    public ChambreDTO(Long id, String name, float prix, boolean disponible) {
    }
}
