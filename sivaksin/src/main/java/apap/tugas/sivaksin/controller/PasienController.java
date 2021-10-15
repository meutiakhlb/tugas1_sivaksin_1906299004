package apap.tugas.sivaksin.controller;


import apap.tugas.sivaksin.model.FaskesModel;
import apap.tugas.sivaksin.model.DokterModel;
import apap.tugas.sivaksin.model.PasienModel;
import apap.tugas.sivaksin.model.VaksinModel;
import apap.tugas.sivaksin.model.DokterPasienModel;
import apap.tugas.sivaksin.service.*;
import apap.tugas.sivaksin.service.VaksinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class PasienController {

    @Qualifier("pasienServiceImpl")
    @Autowired
    private PasienService pasienService;

    @Qualifier("faskesServiceImpl")
    @Autowired
    private FaskesService faskesService;

    @Qualifier("vaksinServiceImpl")
    @Autowired
    private VaksinService vaksinService;

    @Qualifier("dokterPasienServiceImpl")
    @Autowired
    private DokterPasienService dokterPasienService;

    @Qualifier("dokterServiceImpl")
    @Autowired
    private DokterService dokterService;

    @GetMapping("/pasien")
    public String viewAllPasien(Model model) {
        List<PasienModel> pasien = pasienService.getListPasien();
        model.addAttribute("listPasienFaskes", pasien);
        return "all-pasien";
    }

    @GetMapping("/pasien/add")
    public String addPasienForm(Model model) {
        model.addAttribute("pasien", new PasienModel());
        return "form-add-pasien";
    }

    @PostMapping("/pasien/add")
    public String addPasienSubmit(
            @ModelAttribute PasienModel pasien,
            Model model
    ){
        pasienService.addPasien(pasien);
        model.addAttribute("namaPasien", pasien.getNamaPasien());
        return "add-pasien";

    }

    @GetMapping("/pasien/{idPasien}")
    public String viewPasien(
            @PathVariable Long idPasien,
            Model model
    ) {
        PasienModel pasien = pasienService.getPasienById(idPasien);
        LocalDateTime tanggalLahir = pasien.getTanggalLahir();
        String birth= "";
        birth+=tanggalLahir.getDayOfWeek().toString().substring(0,1);
        birth+=tanggalLahir.getDayOfWeek().toString().substring(1,3).toLowerCase();
        birth+= "," + Integer.toString(tanggalLahir.getDayOfMonth());
        birth+=" "+tanggalLahir.getMonth().toString().substring(0,1);
        birth+=tanggalLahir.getMonth().toString().substring(1).toLowerCase();
        birth+= " " + Integer.toString(tanggalLahir.getYear());
        birth+= "," + String.format("%02d", tanggalLahir.getHour());
        birth+=":" +String.format("%02d", tanggalLahir.getMinute());
        List<List<String>> listResult = new ArrayList<>();
        List <DokterPasienModel> listDP = pasien.getListDokterPasien();
        for(DokterPasienModel DP: listDP) {
            List<String> detail = new ArrayList<>();
            FaskesModel faskes = faskesService.getFaskesById(DP.getIdFaskes());
            DokterModel dokter  = dokterService.getDokterById(DP.getDokterDP().getIdDokter());
            detail.add(faskes.getNamaFaskes());
            detail.add(faskes.getVaksin().getJenisVaksin());
            detail.add(DP.getBatchId());
            LocalDateTime waktuSuntik = DP.getWaktuSuntik();
            String WS = "";
            WS+=waktuSuntik.getDayOfWeek().toString().substring(0,1);
            WS+=waktuSuntik.getDayOfWeek().toString().substring(1,3).toLowerCase();
            WS+= "," + Integer.toString(waktuSuntik.getDayOfMonth());
            WS+=" "+waktuSuntik.getMonth().toString().substring(0,1);
            WS+=waktuSuntik.getMonth().toString().substring(1).toLowerCase();
            WS+= " " + Integer.toString(waktuSuntik.getYear());
            WS+= "," + String.format("%02d", waktuSuntik.getHour());
            WS+=":" +String.format("%02d", waktuSuntik.getMinute());

            detail.add(WS);
            detail.add(dokter.getNamaDokter());
            detail.add(dokter.getNip());
            detail.add(dokter.getNoTelepon());
            listResult.add(detail);


        }
        model.addAttribute("pasien", pasien);
        model.addAttribute("birthDate",birth);
        model.addAttribute("listVaksinasi", listResult);
        return "detail-pasien";
    }

    @GetMapping("/pasien/ubah/{idPasien}")
    public String updatePasienformPage(
            @PathVariable Long idPasien,
            Model model
    ) {
        PasienModel pasien = pasienService.getPasienById(idPasien);
        if(pasien != null ) {
            model.addAttribute("pasien", pasien);
        } else {
            model.addAttribute("msg", "Tidak bisa di update,Pasien dengan ID tersebut belum di add");
        }

        return "form-update-pasien";
    }

    @PostMapping("/pasien/ubah")
    public String updatePasienSubmitPage(
            @ModelAttribute PasienModel pasien,
            Model model
    ) {
        PasienModel updatedPasien = pasienService.updatePasien(pasien);

        model.addAttribute("namaPasien", updatedPasien.getNamaPasien());
        return "update-pasien";
    }


}
