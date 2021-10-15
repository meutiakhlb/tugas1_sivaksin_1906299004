package apap.tugas.sivaksin.service;

import apap.tugas.sivaksin.model.VaksinModel;
import apap.tugas.sivaksin.model.FaskesModel;
import apap.tugas.sivaksin.model.PasienModel;

import java.time.LocalTime;
import java.util.*;
public interface VaksinService {

    void addVaksin(VaksinModel Vaksin);

    List<VaksinModel> getListVaksin();

    VaksinModel getVaksinById(Long idVaksin);


}
