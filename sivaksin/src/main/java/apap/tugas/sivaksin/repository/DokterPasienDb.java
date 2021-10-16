package apap.tugas.sivaksin.repository;


import apap.tugas.sivaksin.model.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface DokterPasienDb extends JpaRepository<DokterPasienModel,Long>{
    List<DokterPasienModel> findAllByIdFaskes(Long idFaskes);
//    List<DokterPasienModel> getListDokPasByPasien(PasienModel pasienDP);
}
