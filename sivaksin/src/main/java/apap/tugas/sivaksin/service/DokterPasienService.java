package apap.tugas.sivaksin.service;

import apap.tugas.sivaksin.model.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
public interface DokterPasienService {
    void addDokterPasien(DokterPasienModel DokterPasien);

    List<DokterPasienModel> getDokterPasienList();

    String getBatchId(PasienModel pasien);

    String getWaktuSuntik(LocalDateTime waktuSuntik);

//    List<List<String>> getDokterPasien(PasienModel pasien);

//    List<DokterPasienModel> getListDPbyPasien(PasienModel pasien);

//    List<PasienModel> cariPasienByVaksinFaskes(String jenisVaksin, String namaFaskes, List<FaskesModel> faskes);
//
//    List<PasienModel> cariPasienVaksin(String jenisVaksin, List<FaskesModel> faskes);
//
//    List<PasienModel> cariPasienFaskes(String namaFaskes, List<FaskesModel> faskes);
//
//    List<DokterPasienModel> getFaskesBulanIni(List<DokterPasienModel> listDP);

    List<DokterPasienModel> getListCariPasien(Long idFaskes);

    List<DokterPasienModel> getAllByIdFaskes(Long idFaskes);

    List<DokterPasienModel> getListByVaksinFaskes(String namaVaksin, Long idFaskes);

    List<DokterPasienModel> getListByVaksin(String jenisVaksin);

//    List<List<String>> getListCariPasienFaskes(Long idFaskes);

//    List<DokterPasienModel> getListPasienDariVaksin(VaksinModel vaksin);

    List<DokterPasienModel> getAllByIdPasien(Long idPasien);

//    List<List<String>>  getResultByJenisVaksin(List<List<DokterPasienModel>> dokpas);



}
