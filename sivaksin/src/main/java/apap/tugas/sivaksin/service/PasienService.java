package apap.tugas.sivaksin.service;

import apap.tugas.sivaksin.model.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
public interface PasienService {

    void addPasien(PasienModel Pasien);

    List<PasienModel> getListPasien();

    PasienModel getPasienById(Long idPasien);

    PasienModel updatePasien(PasienModel Pasien);

    PasienModel deleteFaskes(PasienModel Pasien);

    List<PasienModel> getPasienListBulanIni(List<PasienModel> listPasien);

    String findTanggalLahir(LocalDateTime birth);

}