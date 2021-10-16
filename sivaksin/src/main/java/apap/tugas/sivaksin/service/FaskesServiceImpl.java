package apap.tugas.sivaksin.service;

import apap.tugas.sivaksin.model.DokterModel;
import apap.tugas.sivaksin.model.DokterPasienModel;
import apap.tugas.sivaksin.model.*;
import apap.tugas.sivaksin.model.PasienModel;
import apap.tugas.sivaksin.repository.DokterDb;
import apap.tugas.sivaksin.repository.FaskesDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class FaskesServiceImpl implements FaskesService{

    @Autowired
    FaskesDb FaskesDb;

    @Autowired
    PasienService pasienService;


    @Override
    public void addFaskes(FaskesModel Faskes) {
        FaskesDb.save(Faskes);
    }

    @Override
    public List<FaskesModel> getListFaskes() {
        return FaskesDb.findAll();
    }

    @Override
    public FaskesModel getFaskesById(Long idFaskes) {
        Optional<FaskesModel> faskes = FaskesDb.findByIdFaskes(idFaskes);
        if(faskes.isPresent()) return faskes.get();
        else return null;
    }

    @Override
    public FaskesModel getFaskesByNamaFaskes(String namaFaskes) {
        List<FaskesModel> allFaskes = getListFaskes();
        for(FaskesModel faskes: allFaskes) {
            if (faskes.getNamaFaskes().equalsIgnoreCase(namaFaskes)) {
               return faskes;

            }
        }
        return null;
    }

    @Override
    public  boolean terhubungFaskesVaksin(FaskesModel faskes, VaksinModel vaksin){
        FaskesModel faskesModel = getFaskesById(faskes.getIdFaskes());

        if (faskesModel.getVaksin().equals(vaksin)) {
            return true;
        } else {
            return false;
        }


    }

    @Override
    public List<Integer> getSumPasienBulanIni(List<FaskesModel> listFaskes) {
        List<Integer> listJumlahPasien = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        int bulan = cal.get(Calendar.MONTH) + 1;
        int tahun = cal.get(Calendar.YEAR);
        int sumPasien = 0;

        for (FaskesModel faskes:listFaskes) {
            List<PasienModel> listPasien = pasienService.getPasienListBulanIni(faskes.getListFaskesPasien());
            for (PasienModel pasien:listPasien) {
                List<DokterPasienModel> dokpas = pasien.getListDokterPasien();
                for (DokterPasienModel dp : dokpas) {
                    if (dp.getWaktuSuntik().getMonthValue() == bulan && dp.getWaktuSuntik().getYear() == tahun) {
                        sumPasien++;
                        break;
                    }
                }


            }
            listJumlahPasien.add(sumPasien);
            sumPasien = 0;
        }

        return listJumlahPasien;
    }


    @Override
    public FaskesModel updateFaskes(FaskesModel Faskes){
        return FaskesDb.save(Faskes);
    }

    @Override
    public void deleteFaskes(Long idFaskes){
        FaskesDb.delete(getFaskesById(idFaskes));
    }

    @Override
    public List<FaskesModel> findFaskesByVaksin(String jenisVaksin){
        List<FaskesModel> hasilCariFaskes = new ArrayList<>();
        List<FaskesModel> allFaskes = getListFaskes();
        for(FaskesModel faskes: allFaskes){
            if(faskes.getVaksin().getJenisVaksin().equalsIgnoreCase(jenisVaksin)){
                hasilCariFaskes.add(faskes);

            }
        }
        return hasilCariFaskes;
    }


    @Override
    public List<FaskesModel> findFaskesById(Long idFaskes){
        List<FaskesModel> hasilCariFaskes = new ArrayList<>();
        List<FaskesModel> allFaskes = getListFaskes();
        for(FaskesModel faskes: allFaskes){
            if(faskes.getIdFaskes().equals(idFaskes)){
                hasilCariFaskes.add(faskes);

            }
        }
        return hasilCariFaskes;
    }



}
