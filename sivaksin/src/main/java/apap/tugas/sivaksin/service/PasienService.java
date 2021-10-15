package apap.tugas.sivaksin.service;

import apap.tugas.sivaksin.model.*;

import java.time.LocalTime;
import java.util.*;
public interface PasienService {

    void addPasien(PasienModel Pasien);

    List<PasienModel> getListPasien();

    PasienModel getPasienById(Long idPasien);

    PasienModel updatePasien(PasienModel Pasien);

    PasienModel deleteFaskes(PasienModel Pasien);

    List<PasienModel> cariPasienByVaksinFaskes(String jenisVaksin, String namaFaskes, List<FaskesModel> faskes);

    List<PasienModel> cariPasienVaksin(String jenisVaksin, List<FaskesModel> faskes);

    List<PasienModel> cariPasienFaskes(String namaFaskes, List<FaskesModel> faskes);

}