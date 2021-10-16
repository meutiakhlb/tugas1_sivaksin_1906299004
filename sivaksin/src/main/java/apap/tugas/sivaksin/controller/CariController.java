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
public class CariController {

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

    @GetMapping("/cari")
    public String homeCari(Model model) {
        return "cari-home";
    }

    @RequestMapping(value = "/cari/faskes/vaksin", method = RequestMethod.GET)
    public String cariFaskesByVaksin(Model model) {
        List<VaksinModel> listVaksin = vaksinService.getListVaksin();
        List<FaskesModel> listFaskes = new ArrayList<>();
        model.addAttribute("listFaskes", listFaskes);
        model.addAttribute("listVaksin", listVaksin);
        model.addAttribute("faskes", new FaskesModel());
        return "cari-faskes";
    }


    @RequestMapping(value = "/cari/faskes/vaksin", method = RequestMethod.GET, params = {"namaVaksin"})
    public String postCariFaskesByVaksinSubmit(@ModelAttribute FaskesModel faskes, @RequestParam(required = false, value = "namaVaksin") String namaVaksin, Model model) {
        List<VaksinModel> listVak = vaksinService.getListVaksin();
        model.addAttribute("listFaskes", faskesService.findFaskesByVaksin(namaVaksin));
        model.addAttribute("listVaksin", listVak);
        model.addAttribute("faskes", faskes);
        return "cari-faskes";
    }

    @RequestMapping(value = "/cari/pasien", method = RequestMethod.GET)
    public String cariPasienKosong(Model model) {
        List<FaskesModel> listFaskes = faskesService.getListFaskes();
        List<VaksinModel> listVaksin = vaksinService.getListVaksin();
        model.addAttribute("listFaskes", listFaskes);
        model.addAttribute("listVaksin", listVaksin);
        return "cari-pasien";
    }

    @RequestMapping(value="/cari/pasien", method=RequestMethod.GET, params= {"jenisVaksin","namaFaskes"})
    public String cariPasien(
            @RequestParam(required = false, value = "jenisVaksin") String jenisVaksin,
            @RequestParam(required = false,value = "namaFaskes") String namaFaskes,
            Model model){

        if (jenisVaksin == null || jenisVaksin.equals("")) {
            model.addAttribute("vaksin", new VaksinModel());
            if (namaFaskes == null || namaFaskes.equals("")) {
                model.addAttribute("listPasien", new ArrayList<>());
            }
            else {
                model.addAttribute("listPasien", dokterPasienService.getAllByIdFaskes(faskesService.getFaskesByNamaFaskes(namaFaskes).getIdFaskes()));
            }
        }
        else {
            if (namaFaskes == null || namaFaskes.equals("")) {
                model.addAttribute("listPasien", dokterPasienService.getListByVaksin(jenisVaksin));
            }
            else {
                model.addAttribute("listPasien",dokterPasienService.getListByVaksinFaskes(jenisVaksin, faskesService.getFaskesByNamaFaskes(namaFaskes).getIdFaskes()));
            }
        }

        model.addAttribute("listVaksin", vaksinService.getListVaksin());
        model.addAttribute("listFaskes", faskesService.getListFaskes());
        return "cari-pasien";

    }

    @GetMapping("/cari/jumlah-pasien/bulan-ini")
    public String daftarJumlahPasien(
            Model model
    ) {
        List<FaskesModel> listFaskes = faskesService.getListFaskes();
        List<Integer> listJumlahPasien = faskesService.getSumPasienBulanIni(listFaskes);
        model.addAttribute("listJumlahPasien", listJumlahPasien);
        model.addAttribute("listFaskes", listFaskes);
        return "cari-pasien-bulanini";
    }



}







