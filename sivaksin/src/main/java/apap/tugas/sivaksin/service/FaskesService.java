package apap.tugas.sivaksin.service;


import apap.tugas.sivaksin.model.*;
import apap.tugas.sivaksin.model.FaskesModel;
import apap.tugas.sivaksin.model.PasienModel;

import java.time.LocalTime;
import java.util.*;
public interface FaskesService {
    // Method untuk menambahkan dokter
    void addFaskes(FaskesModel Faskes);

    List<FaskesModel> getListFaskes();

//    List<PasienModel> getListPasienFaskes();

    FaskesModel getFaskesById(Long idFaskes);

    FaskesModel updateFaskes(FaskesModel Faskes);

    void deleteFaskes(Long idFaskes);

    List<FaskesModel> findFaskesByVaksin(String jenisVaksin);

    List<FaskesModel> findFaskesByNama(String namaFaskes);

//    FaskesModel getFaskesByNama(String namaFaskes);
}
