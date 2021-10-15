package apap.tugas.sivaksin.service;

import apap.tugas.sivaksin.model.DokterModel;
import apap.tugas.sivaksin.model.DokterPasienModel;
import apap.tugas.sivaksin.model.FaskesModel;
import apap.tugas.sivaksin.model.PasienModel;
import apap.tugas.sivaksin.repository.DokterDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class DokterServiceImpl implements DokterService {

    @Autowired
    DokterDb DokterDb;

    @Override
    public void addDokter(DokterModel Dokter) {
        DokterDb.save(Dokter);
    }

    @Override
    public List<DokterModel> getListDokter() {
        return DokterDb.findAll();
    }

    @Override
    public DokterModel getDokterById(Long idDokter){
        Optional<DokterModel> dokter = DokterDb.findByIdDokter(idDokter);
        if(dokter.isPresent()) return dokter.get();
        else return null;
    }

    @Override
    public DokterModel updateDokter(DokterModel Dokter){
        return DokterDb.save(Dokter);

    }
}
