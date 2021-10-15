package apap.tugas.sivaksin.service;

import apap.tugas.sivaksin.model.DokterModel;
import apap.tugas.sivaksin.model.DokterPasienModel;
import apap.tugas.sivaksin.model.FaskesModel;
import apap.tugas.sivaksin.model.PasienModel;
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

//    @Override
//    public List<PasienModel> cariPasienByVaksinFaskes(String jenisVaksin, String namaFaskes, List<FaskesModel> faskes){
//        List<FaskesModel> result = new ArrayList<>();
//        int count = 0;
//        List<PasienModel> resultPasien = new ArrayList<>();
//        for(FaskesModel faskesmodel : faskes){
//            if(faskesmodel.getVaksin().getJenisVaksin().equalsIgnoreCase(jenisVaksin)){
//                if(faskesmodel.getNamaFaskes().equalsIgnoreCase(namaFaskes)){
//                    result.add(faskesmodel);
//                }
//
//            }
//        }
//
//        for(FaskesModel faskesM : result) {
//            for(PasienModel pasien : faskesM.getListFaskesPasien()){
//                System.out.println(count++);
//                resultPasien.add(pasien);
//            }
//        }
//        System.out.println(resultPasien);
//        return resultPasien;
//
//
//
//    }
//
//    @Override
//    public List<PasienModel> cariPasienVaksin(String jenisVaksin, List<FaskesModel> faskes) {
//        List<FaskesModel> result = new ArrayList<>();
//        int count = 0;
//        List<PasienModel> resultPasien = new ArrayList<>();
//        for(FaskesModel faskesModel : faskes) {
//            if(faskesModel.getVaksin().getJenisVaksin().equalsIgnoreCase(jenisVaksin)){
//                result.add(faskesModel);
//            }
//        }
//
//        for(FaskesModel faskesM : result){
//            for(PasienModel pasien : faskesM.getListFaskesPasien()){
//                System.out.println(count++);
//                resultPasien.add(pasien);
//            }
//        }
//        System.out.println(resultPasien);
//        return resultPasien;
//
//    }
//
//    @Override
//    public List<PasienModel> cariPasienFaskes(String namaFaskes, List<FaskesModel> faskes) {
//        List<FaskesModel> result = new ArrayList<>();
//        int count = 0;
//        List<PasienModel> resultPasien = new ArrayList<>();
//        for(FaskesModel faskesModel : faskes) {
//            if(faskesModel.getNamaFaskes().equalsIgnoreCase(namaFaskes)){
//                result.add(faskesModel);
//            }
//        }
//
//        for(FaskesModel faskesM : result){
//            for(PasienModel pasien : faskesM.getListFaskesPasien()){
//                System.out.println(count++);
//                resultPasien.add(pasien);
//            }
//        }
//        System.out.println(resultPasien);
//        return resultPasien;
//
//    }
//
//
//








}
