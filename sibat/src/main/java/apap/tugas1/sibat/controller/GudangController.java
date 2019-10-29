package apap.tugas1.sibat.controller;

import apap.tugas1.sibat.model.GudangModel;
import apap.tugas1.sibat.model.GudangObatModel;
import apap.tugas1.sibat.model.ObatModel;
import apap.tugas1.sibat.service.GudangObatService;
import apap.tugas1.sibat.service.GudangService;
import apap.tugas1.sibat.service.ObatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GudangController {
    @Qualifier("gudangServiceImpl")
    @Autowired private GudangService gudangService;
    @Autowired private ObatService obatService;
    @Autowired private GudangObatService gudangObatService;

    @RequestMapping(value="/gudang", method = RequestMethod.GET)
    public String viewAll(Model model){
        List<GudangModel> listGudang = gudangService.getGudangList();

        model.addAttribute("listGudang", listGudang);

        return "daftar-gudang";
    }

    @RequestMapping(value="/gudang/tambah", method = RequestMethod.GET)
    public String addGudang(Model model) {

        //Membuat object RestoranModel
        GudangModel newGudang = new GudangModel();

        model.addAttribute("gudang", newGudang);

        //return view template
        return "form-add-gudang";
    }

    @RequestMapping(value = "gudang/tambah", method = RequestMethod.POST)
    private String addProductSubmit(@ModelAttribute GudangModel gudang, Model model){
        gudangService.addGudang(gudang);

        model.addAttribute("namaGudang", gudang.getNama());

        return "notif-add-gudang";
    }

    @RequestMapping(path="/gudang/view/{idGudang}", method=RequestMethod.GET)
    private String viewGudang(@PathVariable(value = "idGudang") Long idGudang, Model model){
        GudangModel gudangModel = gudangService.getGudangByIdGudang(idGudang).get();
        List<ObatModel> listObat = gudangService.getObatByGudang(gudangModel);
        List<ObatModel> allObat = obatService.getObatList();

        model.addAttribute("gudang", gudangModel);
        model.addAttribute("listObat", listObat);
        model.addAttribute("allObat", allObat);

        return "view-gudang";
    }

    //request mappingnya requires idGudang karena saya pakai many to one dan one to many supaya bisa setGudang nya
    @RequestMapping(value = "gudang/tambah-obat/{idGudang}", method = RequestMethod.POST)
    private String addObatToGudang(@PathVariable(value = "idGudang")@ModelAttribute Long idGudang, GudangObatModel gudangObat, Model model){
        GudangModel gudang = gudangService.getGudangByIdGudang(idGudang).get();
        gudangObat.setGudang(gudang);
        gudangObatService.add(gudangObat);

        List<ObatModel> listObat = gudangService.getObatByGudang(gudang);
        List<ObatModel> allObat = obatService.getObatList();

        model.addAttribute("gudang", gudang);
        model.addAttribute("listObat", listObat);
        model.addAttribute("allObat", allObat);

        return "view-gudang";
    }

    @RequestMapping(value="/gudang/hapus/{idGudang}")
    public String deleteGudang(@PathVariable(value = "idGudang") Long idGudang, Model model) {
        GudangModel gudang = gudangService.getGudangByIdGudang(idGudang).get();
        String namaGudang = gudang.getNama();
        System.out.println(gudang.getListGudangobat().size() + " jumlah obat di gudang");
        if(gudang.getListGudangobat().equals(0)){
            gudangService.delete(gudang);
            model.addAttribute("namaGudang", namaGudang);
            return "notif-hapus-gudang";
        }
        else{
            model.addAttribute("namaGudang", namaGudang);
            return "notif-hapus-gudang-notnull";
        }

    }

    @RequestMapping(value = "/gudang/cari", method = RequestMethod.GET)
    public String selectGudang(Model model){
        List<GudangModel> listGudang = gudangService.getGudangList();

        model.addAttribute("allGudang", listGudang);

        return "expired";
    }

    @RequestMapping(value = "/gudang/expired-obat/{idGudang}", method = RequestMethod.POST)
    public String selectedGudang(@PathVariable(value = "idGudang") Long idGudang,Model model){
        GudangModel gudangModel = gudangService.getGudangByIdGudang(idGudang).get();
        List<ObatModel> listObat = gudangService.getObatByGudang(gudangModel);

        model.addAttribute("gudang", gudangModel);
        model.addAttribute("listObat", listObat);

        return "expired";
    }
}
