package apap.tugas.sivaksin.repository;

import apap.tugas.sivaksin.model.FaskesModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface FaskesDb extends JpaRepository<FaskesModel, Long>{
    Optional<FaskesModel> findByIdFaskes(Long idFaskes);
//    Optional<FaskesModel> findByNamaFaskes(String namaFaskes);
}