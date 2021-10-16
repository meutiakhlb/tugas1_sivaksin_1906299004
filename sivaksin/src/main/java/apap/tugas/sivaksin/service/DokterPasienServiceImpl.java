package apap.tugas.sivaksin.service;

import apap.tugas.sivaksin.model.*;
import apap.tugas.sivaksin.service.*;
import apap.tugas.sivaksin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class DokterPasienServiceImpl implements DokterPasienService{

    @Autowired
    DokterPasienDb DokterPasienDb;

    @Autowired
    private PasienService pasienService;

    @Autowired
    private FaskesService faskesService;

    @Override
    public void addDokterPasien(DokterPasienModel DokterPasien){
        DokterPasienDb.save(DokterPasien);
    }

    @Override
    public List<DokterPasienModel> getDokterPasienList() {
        return DokterPasienDb.findAll();
    }

    @Override
    public String getBatchId(PasienModel pasien){
        String batch = "";
        if(pasien.getJenisKelamin() == 0) batch+="2";
        else if (pasien.getJenisKelamin() == 1 ) batch+="1";
        batch+=pasien.getNamaPasien().substring(pasien.getNamaPasien().length()-1).toUpperCase();
        batch+=pasien.getTempatLahir().substring(0,2).toUpperCase();
        LocalDate birth = pasien.getTanggalLahir().toLocalDate();
        String lahir = birth.toString();
        String[] tanggal = lahir.split("-");
        System.out.println(Arrays.toString(tanggal));
        batch += tanggal[2];
        batch += tanggal[1];
        String tahun = Integer.toString((int) Math.floor(Integer.parseInt(tanggal[0])/10));
        batch+= tahun;
        Random rnd = new Random();
        char c = (char) ('a' + rnd.nextInt(26));
        char d = (char) ('a' + rnd.nextInt(27));
        batch += Character.toString(c).toUpperCase();
        batch += Character.toString(d).toUpperCase();
        return batch;

    }

    @Override
    public String getWaktuSuntik(LocalDateTime waktuSuntik){
        String birth= "";
        birth+=waktuSuntik.getDayOfWeek().toString().substring(0,1);
        birth+=waktuSuntik.getDayOfWeek().toString().substring(1,3).toLowerCase();
        birth+= "," + Integer.toString(waktuSuntik.getDayOfMonth());
        birth+=" "+waktuSuntik.getMonth().toString().substring(0,1);
        birth+=waktuSuntik.getMonth().toString().substring(1).toLowerCase();
        birth+= " " + Integer.toString(waktuSuntik.getYear());
        birth+= "," + String.format("%02d", waktuSuntik.getHour());
        birth+=":" +String.format("%02d", waktuSuntik.getMinute());
        return birth;
    }

    @Override
    public List<DokterPasienModel> getAllByIdFaskes(Long idFaskes){
        List<DokterPasienModel> dokter = new ArrayList<>();
        List<DokterPasienModel> dokpas = getDokterPasienList();
        for(DokterPasienModel db: dokpas){
            if(db.getIdFaskes().equals(idFaskes)){
                dokter.add(db);
            }
        }
        return dokter;
    }

    @Override
    public List<DokterPasienModel> getAllByIdPasien(Long idPasien){
        List<DokterPasienModel> dokter = new ArrayList<>();
        List<DokterPasienModel> dokpas = getDokterPasienList();
        for(DokterPasienModel db: dokpas){
            if(db.getPasienDP().getIdPasien().equals(idPasien)){
                dokter.add(db);
            }
        }
        return dokter;
    }

    @Override
    public List<DokterPasienModel> getListCariPasien(Long idFaskes) {
        List<DokterPasienModel> result = new ArrayList<>();
        List<DokterPasienModel> dokpas = getAllByIdFaskes(idFaskes);
        return dokpas;


    }

//    @Override
//    public List<DokterPasienModel> getListPasienDariVaksin(VaksinModel vaksin){
//        List<DokterPasienModel> result = new ArrayList<>();
//        List<FaskesModel> listFaskesVak = faskesService.findFaskesByVaksin(vaksin.getJenisVaksin());
//        for(FaskesModel faskes : listFaskesVak){
//            List<DokterPasienModel> tempFaskes = getAllByIdFaskes(faskes.getIdFaskes());
//            for(DokterPasienModel dopas : tempFaskes){
//                result.add(dopas);
//            }
//
//        }
//        return result;
//    }

//    @Override
//    public List<DokterPasienModel> findPasienByFaskes(FaskesModel faskes){
//        List<DokterPasienModel> result = new ArrayList<>();
//        List<FaskesModel> listFaskesVak = faskesService.getListFaskes();
//        for(FaskesModel faskesM : listFaskesVak){
//            List<DokterPasienModel> tempFaskes = getAllByIdFaskes(faskesM.getIdFaskes());
//            for(DokterPasienModel dopas : tempFaskes){
//                result.add(dopas);
//            }
//
//        }
//        return result;
//    }


    @Override
    public List<DokterPasienModel> getListByVaksinFaskes(String namaVaksin, Long idFaskes) {
        List<DokterPasienModel> result = new ArrayList<>();
        for (DokterPasienModel dokpas:getDokterPasienList()){
            if (dokpas.getIdFaskes() == idFaskes) {
                if (faskesService.getFaskesById(dokpas.getIdFaskes()).getVaksin().getJenisVaksin().equals(namaVaksin)) {
                    result.add(dokpas);
                }
            }
        }
        return result;
    }

    @Override
    public List<DokterPasienModel> getListByVaksin(String jenisVaksin){
        List<DokterPasienModel> dokpasAll = new ArrayList<>();
        for (DokterPasienModel dokpas:getDokterPasienList()){
            if (faskesService.getFaskesById(dokpas.getIdFaskes()).getVaksin().getJenisVaksin().equals(jenisVaksin)) {
                dokpasAll.add(dokpas);

            }
        }
        return dokpasAll;
    }


}
