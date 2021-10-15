package apap.tugas.sivaksin.service;

import apap.tugas.sivaksin.model.*;
import apap.tugas.sivaksin.repository.DokterDb;
import apap.tugas.sivaksin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class PasienServiceImpl implements PasienService {

    @Autowired
    PasienDb PasienDb;

    @Override
    public void addPasien(PasienModel Pasien){
        PasienDb.save(Pasien);
    }

    @Override
    public List<PasienModel> getListPasien(){
        return PasienDb.findAll();
    }

    @Override
    public PasienModel getPasienById(Long idPasien) {
        Optional<PasienModel> pasien = PasienDb.findByIdPasien(idPasien);
        if(pasien.isPresent()) return pasien.get();
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
    public List<PasienModel> cariPasienByVaksinFaskes(String jenisVaksin, String namaFaskes, List<FaskesModel> faskes){
        List<FaskesModel> result = new ArrayList<>();
        int count = 0;
        List<PasienModel> resultPasien = new ArrayList<>();
        for(FaskesModel faskesmodel : faskes){
            if(faskesmodel.getVaksin().getJenisVaksin().equalsIgnoreCase(jenisVaksin)){
                if(faskesmodel.getNamaFaskes().equalsIgnoreCase(namaFaskes)){
                    result.add(faskesmodel);
                }

            }
        }

        for(FaskesModel faskesM : result) {
            for(PasienModel pasien : faskesM.getListFaskesPasien()){
                System.out.println(count++);
                resultPasien.add(pasien);
            }
        }
        System.out.println(resultPasien);
        return resultPasien;



    }

    @Override
    public List<PasienModel> cariPasienVaksin(String jenisVaksin, List<FaskesModel> faskes) {
            List<FaskesModel> result = new ArrayList<>();
            int count = 0;
            List<PasienModel> resultPasien = new ArrayList<>();
            for(FaskesModel faskesModel : faskes) {
                if(faskesModel.getVaksin().getJenisVaksin().equalsIgnoreCase(jenisVaksin)){
                    result.add(faskesModel);
                }
            }

            for(FaskesModel faskesM : result){
                for(PasienModel pasien : faskesM.getListFaskesPasien()){
                    System.out.println(count++);
                    resultPasien.add(pasien);
                }
            }
            System.out.println(resultPasien);
            return resultPasien;

    }

    @Override
    public List<PasienModel> cariPasienFaskes(String namaFaskes, List<FaskesModel> faskes) {
        List<FaskesModel> result = new ArrayList<>();
        int count = 0;
        List<PasienModel> resultPasien = new ArrayList<>();
        for(FaskesModel faskesModel : faskes) {
            if(faskesModel.getNamaFaskes().equalsIgnoreCase(namaFaskes)){
                result.add(faskesModel);
            }
        }

        for(FaskesModel faskesM : result){
            for(PasienModel pasien : faskesM.getListFaskesPasien()){
                System.out.println(count++);
                resultPasien.add(pasien);
            }
        }
        System.out.println(resultPasien);
        return resultPasien;

    }

}
