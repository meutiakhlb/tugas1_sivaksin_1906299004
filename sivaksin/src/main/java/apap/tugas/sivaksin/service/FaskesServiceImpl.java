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

//    @Override
//    public FaskesModel getFaskesByNama(String namaFaskes) {
//        Optional<FaskesModel> faskes = FaskesDb.findByNamaFaskes(namaFaskes);
//        if(faskes.isPresent()) return faskes.get();
//        else return null;
//    }

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
    public List<FaskesModel> findFaskesByNama(String namaFaskes){
        List<FaskesModel> hasilCariFaskes = new ArrayList<>();
        List<FaskesModel> allFaskes = getListFaskes();
        for(FaskesModel faskes: allFaskes){
            if(faskes.getNamaFaskes().equalsIgnoreCase(namaFaskes)){
                hasilCariFaskes.add(faskes);

            }
        }
        return hasilCariFaskes;
    }



}
