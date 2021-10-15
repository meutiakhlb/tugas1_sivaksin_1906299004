package apap.tugas.sivaksin.service;
import apap.tugas.sivaksin.model.DokterModel;

import java.time.LocalTime;
import java.util.*;

public interface DokterService {
    // Method untuk menambahkan dokter
    void addDokter(DokterModel Dokter);

    List<DokterModel> getListDokter();

    DokterModel getDokterById(Long idDokter);

    DokterModel updateDokter(DokterModel Dokter);

}
