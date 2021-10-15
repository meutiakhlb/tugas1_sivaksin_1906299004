package apap.tugas.sivaksin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;



@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="pasien")
public class PasienModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idPasien;

    @NotNull
    @Size(max=255)
    @Column(name="nama_pasien", nullable = false)
    private String namaPasien;

    @NotNull
    @Size(max=16)
    @Column(name="nik", nullable = false)
    private  String nik;

    @NotNull
    @Size(max=13)
    @Column(name="nomor_telepon", nullable = false)
    private  String nomorTelepon;

    @NotNull
    @Column(name="jenis_kelamin", nullable = false)
    private int jenisKelamin ;

    @NotNull
    @Column(name="tanggal_lahir", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalLahir;

    @NotNull
    @Column(name="tempat_lahir", nullable = false)
    private String tempatLahir;

    @NotNull
    @Column(name="pekerjaan", nullable = false)
    private String pekerjaan;

    // relasi pasien dengan dokterPasien
    @OneToMany(mappedBy = "pasienDP", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DokterPasienModel>  listDokterPasien;

    // relasi Pasien dengan Faskes
    @ManyToMany(mappedBy = "listFaskesPasien")
    private List<FaskesModel> listPasienFaskes;

}
