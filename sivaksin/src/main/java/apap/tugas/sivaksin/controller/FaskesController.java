package apap.tugas.sivaksin.controller;

import apap.tugas.sivaksin.model.*;
import apap.tugas.sivaksin.service.*;
import apap.tugas.sivaksin.service.VaksinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.*;
import java.util.*;

@Controller
public class FaskesController {

    @Qualifier("faskesServiceImpl")
    @Autowired
    private FaskesService faskesService;

    @Qualifier("vaksinServiceImpl")
    @Autowired
    private VaksinService vaksinService;

    @Qualifier("pasienServiceImpl")
    @Autowired
    private PasienService pasienService;

    @Qualifier("dokterServiceImpl")
    @Autowired
    private DokterService dokterService;

    @Qualifier("dokterPasienServiceImpl")
    @Autowired
    private DokterPasienService dokterPasienService;

    @GetMapping("/faskes")
    public String viewAllFaskes(Model model) {
        List<FaskesModel> faskes = faskesService.getListFaskes();
        model.addAttribute("listFaskes", faskes);
        return "all-faskes2";
    }

    @GetMapping("/faskes/add")
    public String addFaskesForm(Model model) {
        List<VaksinModel> listVaksin = vaksinService.getListVaksin();
        model.addAttribute("listVaksin", listVaksin);
        model.addAttribute("faskes", new FaskesModel());
        return "form-add-faskes2";
    }

    @PostMapping("/faskes/add")
    public String addFaskesSubmit(
            @ModelAttribute FaskesModel faskes,
            Model model
    ){
        faskesService.addFaskes(faskes);
        model.addAttribute("namaFaskes", faskes.getNamaFaskes());
        return "add-faskes";

    }

    @GetMapping("/faskes/{idFaskes}")
    public String viewFaskes(
            @PathVariable Long idFaskes,
            Model model
    ) {

        FaskesModel faskes = faskesService.getFaskesById(idFaskes);
        VaksinModel vaksin = vaksinService.getVaksinById(faskes.getVaksin().getIdVaksin());

        model.addAttribute("faskes", faskes);
        model.addAttribute("vaksin", vaksin);
        model.addAttribute("listPasien", faskes.getListFaskesPasien());
        return "detail-faskes2";

    }

    @GetMapping("/faskes/ubah/{idFaskes}")
    public String updateFaskesformPage(
            @PathVariable Long idFaskes,
            Model model
    ) {
        FaskesModel faskes = faskesService.getFaskesById(idFaskes);
        List<VaksinModel> listVaksin = vaksinService.getListVaksin();
        LocalTime sekarang = LocalTime.now();
        if(sekarang.compareTo(faskes.getJamTutup()) >= 0 && faskes.getListFaskesPasien().size() == 0 ) {
            model.addAttribute("faskes", faskes);
            model.addAttribute("listVaksin", listVaksin);
        } else {
            model.addAttribute("msg", "Tidak bisa di update, Faskes masih beroperasi dan/atau ada pasien");
        }

        return "form-update-faskes";
    }

    @PostMapping("/faskes/ubah")
    public String updateFaskesSubmitPage(
            @ModelAttribute FaskesModel faskes,
            Model model
    ) {
        FaskesModel updatedFaskes = faskesService.updateFaskes(faskes);

        if(updatedFaskes != null) {
            model.addAttribute("namaFaskes", updatedFaskes.getNamaFaskes());
        } else {
            model.addAttribute("msg", "Tidak bisa di update, Faskes Belum di Add");
        }

        return "update-faskes";
    }

    @GetMapping("/faskes/hapus/{idFaskes}")
    public String deleteFaskes(@PathVariable("idFaskes") Long idFaskes, Model model) {
        FaskesModel faskes = faskesService.getFaskesById(idFaskes);
        LocalTime sekarang = LocalTime.now();
        if(sekarang.compareTo(faskes.getJamTutup()) >= 0 && faskes.getListFaskesPasien().size() == 0 ) {
            faskesService.deleteFaskes(faskes.getIdFaskes());
            model.addAttribute("namaFaskes", faskes.getNamaFaskes());
        } else {
            model.addAttribute("msg", "Tidak bisa di update, Faskes Sedang Beroperasi dan/atau ada pasien");
        }

        return "delete-faskes";


    }

    @GetMapping("/faskes/{idFaskes}/tambah/pasien")
    public String addPasienFaskes(@PathVariable Long idFaskes, Model model) {

        FaskesModel faskes = faskesService.getFaskesById(idFaskes);
        List<PasienModel> pasien = pasienService.getListPasien();
        List<DokterModel> dokter = dokterService.getListDokter();
        model.addAttribute("DokterPasien", new DokterPasienModel());
        model.addAttribute("listDokter", dokter);
        model.addAttribute("listPasien", pasien);
        model.addAttribute("faskes", faskes);
        return "form-add-pasienFaskes";
    }

    @PostMapping("/faskes/{idFaskes}/tambah/pasien")
    public String suksesAddPasienFaskes(@ModelAttribute DokterPasienModel dokterPasien,
                                        @PathVariable Long idFaskes, Model model) {


        FaskesModel faskes = faskesService.getFaskesById(idFaskes);
        PasienModel pasien = dokterPasien.getPasienDP();
        if(dokterPasien.getWaktuSuntik().getHour() < faskes.getJamTutup().getHour() && dokterPasien.getWaktuSuntik().getHour() > faskes.getJamMulai().getHour()){
            faskes.getListFaskesPasien().add(pasien);
            pasien.getListPasienFaskes().add(faskes);
            dokterPasien.setBatchId(dokterPasienService.getBatchId(dokterPasien.getPasienDP()));
            dokterPasienService.addDokterPasien(dokterPasien);
            model.addAttribute("namaPasien", pasien.getNamaPasien());
            model.addAttribute("namaFaskes",faskes.getNamaFaskes());
        } else {
            model.addAttribute("msg", "Tidak Bisa Melakukan Tambah Pasien ke Faskes, Lakukan di Jam Operasional ");
        }

        return "add-pasienFaskes";

    }
}
