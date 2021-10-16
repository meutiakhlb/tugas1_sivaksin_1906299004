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

    List<FaskesModel> findFaskesById(Long idFaskes);

    FaskesModel getFaskesByNamaFaskes(String namaFaskes);

    boolean terhubungFaskesVaksin(FaskesModel faskes, VaksinModel vaksin);

    List<Integer> getSumPasienBulanIni(List<FaskesModel> listFaskes);

//    FaskesModel getFaskesByNama(String namaFaskes);
}
