package apap.tugas1.sibat.controller;

import apap.tugas1.sibat.model.GudangModel;
import apap.tugas1.sibat.model.JenisModel;
import apap.tugas1.sibat.model.ObatModel;
import apap.tugas1.sibat.model.SupplierModel;
import apap.tugas1.sibat.service.GudangService;
import apap.tugas1.sibat.service.JenisService;
import apap.tugas1.sibat.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import apap.tugas1.sibat.service.ObatService;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Controller
public class ObatController {

    @Qualifier("obatServiceImpl")
    @Autowired private ObatService obatService;
    @Autowired private JenisService jenisService;
    @Autowired private SupplierService supplierService;
    @Autowired private GudangService gudangService;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String viewAll(Model model){
        List<ObatModel> listObat = obatService.getObatList();

        //Add model obat ke "obat" untuk di render
        model.addAttribute("listObat", listObat);

        return "home";
    }

    @RequestMapping(value = "/bonus", method = RequestMethod.GET)
    public String bonus(Model model){
        List<ObatModel> listObat = obatService.getObatList();

        //Add model obat ke "obat" untuk di render
        model.addAttribute("listObat", listObat);

        return "bonus";
    }

    @RequestMapping(path = "obat/view/{nomorRegistrasi}", method = RequestMethod.GET)
    public String view(@PathVariable(value = "nomorRegistrasi") String nomorRegistrasi, Model model){

        // Mengambil object obat yang ingin ditampilkan
        ObatModel obatModel = obatService.getObatByNomorRegistrasi(nomorRegistrasi).get();
        List<SupplierModel> allSupplier = obatService.getSupplierByObatList(obatModel);
        int jmlSupplier = allSupplier.size();

        String gudang = obatService.getGudangByObat(obatModel);
        String supplier = obatService.getSupplierByObat(obatModel);

        // Add odel obat ke "obat" untuk dirender
        model.addAttribute("obat", obatModel);
        model.addAttribute("gudang", gudang);
        model.addAttribute("supplier", supplier);
        model.addAttribute("jmlSupplier", jmlSupplier);

        return "lihat-obat";
    }

    @RequestMapping(value="/obat/tambah", method = RequestMethod.GET)
    public String addObat(Model model) {

        ObatModel obat = new ObatModel();
        List<SupplierModel> allSupplier = supplierService.getSupplierList();
        List<JenisModel> allJenis = jenisService.getJenisList();

        model.addAttribute("obat", obat);
        model.addAttribute("allJenis", allJenis);
        model.addAttribute("allSupplier", allSupplier);

        //return view template
        return "form-add-obat";
    }

    @RequestMapping(value = "obat/tambah", method = RequestMethod.POST)
    private String addProductSubmit(@ModelAttribute ObatModel obat, JenisModel jenis, SupplierModel supplier, Model model){
        System.out.println(obat.getNama() + " " + obat.getBentuk());
        String kode = obatService.createKode(obat, jenis);

        obat.setKode(kode);
        obatService.add(obat);
        model.addAttribute("namaObat", obat.getNama());

        return "notif-add-obat";
    }

    @RequestMapping(value="/obat/hapus/{idObat}")
    public String deleteGudang(@PathVariable(value = "idObat") Long idObat, Model model) {
        ObatModel obat = obatService.getObatByIdObat(idObat).get();
        String namaObat = obat.getNama();
        obatService.delete(obat);
        model.addAttribute("namaObat", namaObat);
        return "notif-hapus-obat";

    }

    @RequestMapping(value="/filter")
    public String cariObat(Model model){
        List<JenisModel> allJenis = jenisService.getJenisList();
        List<GudangModel> allGudang = gudangService.getGudangList();
        List<SupplierModel> allSupplier = supplierService.getSupplierList();
        ObatModel allObat = new ObatModel();
        allObat.setHarga((double) 0);

        model.addAttribute("allGudang", allGudang);
        model.addAttribute("allJenis", allJenis);
        model.addAttribute("allSupplier", allSupplier);
        model.addAttribute("allObat", allObat);

        return "filter";
    }


}
