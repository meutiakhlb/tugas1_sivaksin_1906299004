package apap.tugas.sivaksin.service;

import apap.tugas.sivaksin.model.*;
import apap.tugas.sivaksin.repository.DokterDb;
import apap.tugas.sivaksin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class PasienServiceImpl implements PasienService {

    @Autowired
    PasienDb PasienDb;

    @Override
    public void addPasien(PasienModel Pasien) {
        PasienDb.save(Pasien);
    }

    @Override
    public List<PasienModel> getListPasien() {
        return PasienDb.findAll();
    }

    @Override
    public PasienModel getPasienById(Long idPasien) {
        Optional<PasienModel> pasien = PasienDb.findByIdPasien(idPasien);
        if (pasien.isPresent()) return pasien.get();
        else return null;
    }

    @Override
    public PasienModel updatePasien(PasienModel Pasien) {
        return PasienDb.save(Pasien);
    }

    @Override
    public PasienModel deleteFaskes(PasienModel Pasien) {
        PasienDb.delete(Pasien);
        return Pasien;
    }


    @Override
    public List<PasienModel> getPasienListBulanIni(List<PasienModel> listPasien) {
        List<PasienModel> listUniquePasien = new ArrayList<>();
        for (PasienModel pasien : listPasien) {
            if (!listUniquePasien.contains(pasien)) {
                listUniquePasien.add(pasien);
            } else {
            }
        }
        return listUniquePasien;
    }

    @Override
    public  String findTanggalLahir(LocalDateTime lahir) {
        LocalDateTime tanggalLahir = lahir;
        String birth = "";
        birth+= "," + Integer.toString(tanggalLahir.getDayOfMonth());
        birth+=" "+tanggalLahir.getMonth().toString().substring(0,1);
        birth+=tanggalLahir.getMonth().toString().substring(1).toLowerCase();
        birth+= " " + Integer.toString(tanggalLahir.getYear());
        return birth;
    }
}
