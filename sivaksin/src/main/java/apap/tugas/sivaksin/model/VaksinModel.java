package apap.tugas.sivaksin.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="vaksin")
public class VaksinModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVaksin;

    @NotNull
    @Column(name="efikasi", nullable=false)
    private double efikasi;

    @NotNull
    @Size(max = 255)
    @Column(name="jenis_vaksin", nullable=false)
    private String jenisVaksin;

    @NotNull
    @Size(max = 255)
    @Column(name="asal_negara", nullable=false)
    private String asalNegara;

    @OneToMany(mappedBy = "vaksin", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FaskesModel> listVaksinFaskes;
}
