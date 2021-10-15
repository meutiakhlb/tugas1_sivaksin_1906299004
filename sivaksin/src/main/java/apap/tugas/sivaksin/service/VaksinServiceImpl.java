package apap.tugas.sivaksin.service;

import apap.tugas.sivaksin.model.*;
import apap.tugas.sivaksin.repository.DokterDb;
import apap.tugas.sivaksin.repository.VaksinDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class VaksinServiceImpl implements VaksinService{

    @Autowired
    VaksinDb VaksinDb;

    @Override
    public void addVaksin(VaksinModel Vaksin) {
        VaksinDb.save(Vaksin);
    }

    @Override
    public List<VaksinModel> getListVaksin() {
        return VaksinDb.findAll();
    }

    @Override
    public VaksinModel getVaksinById(Long idVaksin) {
        Optional<VaksinModel> vaksin = VaksinDb.findByIdVaksin(idVaksin);
        if(vaksin.isPresent()) return vaksin.get();
        else return null;
    }

}
