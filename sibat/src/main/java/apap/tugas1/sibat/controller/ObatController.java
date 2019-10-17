package apap.tugas1.sibat.controller;

import apap.tugas1.sibat.model.ObatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import apap.tugas1.sibat.service.ObatService;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ObatController {
    @Qualifier("obatServiceImpl")
    @Autowired
    private ObatService obatService;

    /*@RequestMapping("/")
    public String beranda() {return "beranda";}*/

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String viewAll(Model model){
        List<ObatModel> listObat = obatService.getObatList();

        //Add model obat ke "obat" untuk di render
        model.addAttribute("listObat", listObat);

        return "home";
    }

    @RequestMapping(path = "obat/view/{nomorRegistrasi}", method = RequestMethod.GET)
    public String view(@PathVariable(value = "nomorRegistrasi") String nomorRegistrasi, Model model){

        // Mengambil object obat yang ingin ditampilkan
        ObatModel obatModel = obatService.getObatByNomorRegistrasi(nomorRegistrasi).get();

        String gudang = obatService.getGudangByObat(obatModel);
        String supplier = obatService.getSupplierByObat(obatModel);

        // Add odel obat ke "obat" untuk dirender
        model.addAttribute("obat", obatModel);
        model.addAttribute("gudang", gudang);
        model.addAttribute("supplier", supplier);

        return "lihat-obat";
    }
}
