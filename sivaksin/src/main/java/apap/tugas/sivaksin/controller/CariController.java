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
    public String homeCari(Model model){return "cari-home";}

    @RequestMapping(value = "/cari/faskes/vaksin", method = RequestMethod.GET)
    public String cariFaskesByVaksin(Model model){
        List<VaksinModel> listVaksin = vaksinService.getListVaksin();
        List<FaskesModel> listFaskes = new ArrayList<>();
        model.addAttribute("listFaskes", listFaskes);
        model.addAttribute("listVaksin", listVaksin);
        model.addAttribute("faskes",new FaskesModel());
        return"cari-faskesVaksin";
    }


    @RequestMapping(value = "/cari/faskes/vaksin", method = RequestMethod.GET, params= {"namaVaksin"})
    public String postCariFaskesByVaksinSubmit(@ModelAttribute FaskesModel faskes,  @RequestParam(required = false, value = "namaVaksin") String namaVaksin,Model model) {
        List<VaksinModel> listVak = vaksinService.getListVaksin();
        model.addAttribute("listFaskes", faskesService.findFaskesByVaksin(namaVaksin));
        model.addAttribute("listVaksin", listVak);
        model.addAttribute("faskes", faskes);
        return"cari-faskesVaksin";
    }

    @RequestMapping(value = "/cari/pasien", method = RequestMethod.GET)
    public String cariPasienKosong(Model model) {

        List<VaksinModel> listVaksin = vaksinService.getListVaksin();
        model.addAttribute("listVaksin", listVaksin);

        List <FaskesModel> listFaskes = faskesService.getListFaskes();
        model.addAttribute("listFaskes", listFaskes);

        return "cari-pasienByVaksinFaskes";
    }

    @RequestMapping(value="/cari/pasien", method=RequestMethod.GET, params= {"jenisVaksin","namaFaskes"})
    public String cariPilot(
            @RequestParam(required = false, value = "jenisVaksin") String jenisVaksin,
            @RequestParam(required = false,value = "namaFaskes") String namaFaskes,
            @ModelAttribute PasienModel pasien,
            Model model){


        List<FaskesModel> listFaskes = faskesService.findFaskesByNama(namaFaskes);
        List<VaksinModel> listVaksin = vaksinService.getListVaksin();
        if (jenisVaksin != null && namaFaskes !=null) {

            List<FaskesModel> listFaskesPasien = pasien.getListPasienFaskes();
            List<String> hasil =  new ArrayList<>();
            List<DokterPasienModel> dokterPasien = pasien.getListDokterPasien();
            List<PasienModel> listPasien = pasienService.cariPasienByVaksinFaskes(jenisVaksin, namaFaskes, listFaskesPasien);
            for(PasienModel pasienModel: listPasien) {
                hasil.add(pasienModel.getNamaPasien());
                hasil.add(pasienModel.getNik());
                hasil.add(pasienModel.getNomorTelepon());
                int gender = pasienModel.getJenisKelamin();
                if(gender == 0) hasil.add("Laki-Laki");
                else if (gender == 1) hasil.add("Perempuan");

            }

            for(DokterPasienModel DP : dokterPasien){
                hasil.add(DP.getBatchId());
                LocalDateTime ws =  DP.getWaktuSuntik();
                hasil.add(dokterPasienService.getWaktuSuntik(ws));
            }
            model.addAttribute("listFaskes", listFaskes);
            model.addAttribute("listVaksin", listVaksin);
            model.addAttribute("listPasien", hasil);
            return "cari-pasienByVaksinFaskes";
        }else if (namaFaskes ==null){

            List<FaskesModel> listFaskesPasien = pasien.getListPasienFaskes();
            List<String> hasil =  new ArrayList<>();
            List<DokterPasienModel> dokterPasien = pasien.getListDokterPasien();
            List<PasienModel> listPasien = pasienService.cariPasienVaksin(jenisVaksin,listFaskesPasien);


            for(PasienModel pasienModel: listPasien) {
                hasil.add(pasienModel.getNamaPasien());
                hasil.add(pasienModel.getNik());
                hasil.add(pasienModel.getNomorTelepon());
                int gender = pasienModel.getJenisKelamin();
                if(gender == 0) hasil.add("Laki-Laki");
                else if (gender == 1) hasil.add("Perempuan");

            }

            for(DokterPasienModel DP : dokterPasien){
                hasil.add(DP.getBatchId());
                LocalDateTime ws =  DP.getWaktuSuntik();
                hasil.add(dokterPasienService.getWaktuSuntik(ws));
            }
            model.addAttribute("listFaskes", listFaskes);
            model.addAttribute("listVaksin", listVaksin);
            model.addAttribute("listPasien", hasil);
            return "cari-pilot";
        }
        else if (jenisVaksin == null ){

            List<FaskesModel> listFaskesPasien = pasien.getListPasienFaskes();
            List<String> hasil =  new ArrayList<>();
            List<DokterPasienModel> dokterPasien = pasien.getListDokterPasien();
            List<PasienModel> listPasien = pasienService.cariPasienFaskes(namaFaskes, listFaskesPasien);
            for(PasienModel pasienModel: listPasien) {
                hasil.add(pasienModel.getNamaPasien());
                hasil.add(pasienModel.getNik());
                hasil.add(pasienModel.getNomorTelepon());
                int gender = pasienModel.getJenisKelamin();
                if(gender == 0) hasil.add("Laki-Laki");
                else if (gender == 1) hasil.add("Perempuan");

            }

            for(DokterPasienModel DP : dokterPasien){
                hasil.add(DP.getBatchId());
                LocalDateTime ws =  DP.getWaktuSuntik();
                hasil.add(dokterPasienService.getWaktuSuntik(ws));
            }
            model.addAttribute("listFaskes", listFaskes);
            model.addAttribute("listVaksin", listVaksin);
            model.addAttribute("listPasien", hasil);
            return "cari-pasienByVaksinFaskes";
        } else {

            model.addAttribute("listFaskes", listFaskes);
            model.addAttribute("listVaksin", listVaksin);
            return "cari-pasienByVaksinFaskes";
        }

    }

//    @RequestMapping(value = "/cari/jumlah-pasien/bulan-ini", method = RequestMethod.GET)
//    public String cariPilotBulanIni(Model model) {
//
//        List<DokterPasienModel> listDP= dokterPasienService.getDokterPasienList();
//        List<DokterPasienModel> listDokpas = dokterPasienService.getFaskesBulanIni(listDP);
//        List<FaskesModel> listFaskes = dokterPasienService.getListFaskes(listDokpas);
//        model.addAttribute("listDokpas", );
//
//        return "cari-pilotbulanini";
//    }
}
