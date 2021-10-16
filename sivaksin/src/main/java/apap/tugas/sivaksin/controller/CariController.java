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
        FaskesModel faskes = faskesService.getFaskesByNamaFaskes(namaFaskes);
        VaksinModel vaksin = vaksinService.getVaksinByNama(jenisVaksin);
        List<FaskesModel> listFaskes = faskesService.getListFaskes();
        List<VaksinModel> listVaksin = vaksinService.getListVaksin();

        if(faskes == null && vaksin == null ){
            model.addAttribute("listFaskes", listFaskes);
            model.addAttribute("listVaksin", listVaksin);
            return "cari-pasien";
        } else if(faskes == null){
            List<DokterPasienModel> dp = dokterPasienService.getListPasienDariVaksin(vaksin);
            List<List<String>> result =  new ArrayList<>();

            for(DokterPasienModel dopas : dp){
                List<String> data =  new ArrayList<>();
                data.add(dopas.getPasienDP().getNamaPasien());
                data.add(dopas.getPasienDP().getNik());
                data.add(dopas.getPasienDP().getNomorTelepon());
                if(dopas.getPasienDP().getJenisKelamin() == 0) {
                    data.add("Perempuan");
                } else {
                    data.add("Laki-Laki");
                }
                data.add(dopas.getBatchId());
                data.add(dokterPasienService.getWaktuSuntik(dopas.getWaktuSuntik()));

                result.add(data);
            }
            model.addAttribute("listFaskes", listFaskes);
            model.addAttribute("listVaksin", listVaksin);
            model.addAttribute("listPasien", result);

            return "cari-pasien";

        } else if (vaksin==null){
            List<DokterPasienModel> dp = dokterPasienService.findPasienByFaskes(faskes);
            List<List<String>> result =  new ArrayList<>();

            for(DokterPasienModel dopas : dp){
                List<String> data =  new ArrayList<>();
                data.add(dopas.getPasienDP().getNamaPasien());
                data.add(dopas.getPasienDP().getNik());
                data.add(dopas.getPasienDP().getNomorTelepon());
                if(dopas.getPasienDP().getJenisKelamin() == 0) {
                    data.add("Perempuan");
                } else {
                    data.add("Laki-Laki");
                }
                data.add(dopas.getBatchId());
                data.add(dokterPasienService.getWaktuSuntik(dopas.getWaktuSuntik()));

                result.add(data);
            }
            model.addAttribute("listFaskes", listFaskes);
            model.addAttribute("listVaksin", listVaksin);
            model.addAttribute("listPasien", result);
            return "cari-pasien";
        } else {
            if(faskesService.terhubungFaskesVaksin(faskes,vaksin)){
                List<DokterPasienModel> dp = dokterPasienService.getListCariPasien(faskes.getIdFaskes());
                List<List<String>> result =  new ArrayList<>();

                for(DokterPasienModel dopas : dp){
                    List<String> data =  new ArrayList<>();
                    data.add(dopas.getPasienDP().getNamaPasien());
                    data.add(dopas.getPasienDP().getNik());
                    data.add(dopas.getPasienDP().getNomorTelepon());
                    if(dopas.getPasienDP().getJenisKelamin() == 0) {
                        data.add("Perempuan");
                    } else {
                        data.add("Laki-Laki");
                    }
                    data.add(dopas.getBatchId());
                    data.add(dokterPasienService.getWaktuSuntik(dopas.getWaktuSuntik()));

                    result.add(data);
                }
                model.addAttribute("listFaskes", listFaskes);
                model.addAttribute("listVaksin", listVaksin);
                model.addAttribute("listPasien", result);
                return "cari-pasien";
            } else {
                List<DokterPasienModel> dp = new ArrayList<>();

                model.addAttribute("listFaskes", listFaskes);
                model.addAttribute("listVaksin", listVaksin);
                model.addAttribute("listPasien", dp );
                return "cari-pasien";
            }

        }
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







