package apap.tugas.sivaksin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="faskes")
public class FaskesModel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idFaskes;

    @NotNull
    @Size(max=255)
    @Column(name="nama_fakses", nullable = false)
    private String namaFaskes;

    @NotNull
    @Size(max=255)
    @Column(name="kabupaten", nullable = false)
    private String kabupaten;

    @NotNull
    @Size(max=255)
    @Column(name="provinsi", nullable = false)
    private String provinsi;

    @NotNull
    @Column(name="kuota", nullable = false)
    private int kuota;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    @Column(name="jam_mulai", nullable = false)
    private LocalTime jamMulai;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    @Column(name="jam_tutup", nullable = false)
    private LocalTime jamTutup;

    // relasi faskes dengan vaksin
    @ManyToOne(fetch = FetchType.EAGER, optional=false)
    @JoinColumn(name="id_vaksin", referencedColumnName="idVaksin", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VaksinModel vaksin;

    // relasi pasien dengan faskes
    @ManyToMany
    @JoinTable(name="pasien_faskes", joinColumns = @JoinColumn(name="id_faskes"), inverseJoinColumns = @JoinColumn(name="id_pasien"))
    List <PasienModel> listFaskesPasien;

}
