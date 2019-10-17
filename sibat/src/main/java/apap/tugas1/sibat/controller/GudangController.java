package apap.tugas1.sibat.controller;

import apap.tugas1.sibat.model.GudangModel;
import apap.tugas1.sibat.service.GudangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class GudangController {
    @Qualifier("gudangServiceImpl")
    @Autowired
    private GudangService gudangService;

    @RequestMapping(value="/gudang", method = RequestMethod.GET)
    public String viewAll(Model model){
        List<GudangModel> listGudang = gudangService.getGudangList();

        System.out.println("INI GUDANG === " + listGudang);
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
        System.out.println(gudang.getNama());
        model.addAttribute("namaGudang", gudang.getNama());

        return "notif-add-gudang";
    }

}
