package apap.tugas1.sibat.controller;

import apap.tugas1.sibat.model.*;
import apap.tugas1.sibat.repository.ObatSupplierDb;
import apap.tugas1.sibat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
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
    @Autowired private ObatSupplierService obatSupplierService;
    @Autowired private GudangObatService gudangObatService;

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
        List<JenisModel> allJenis = jenisService.getJenisList();

        model.addAttribute("obat", obat);
        model.addAttribute("allJenis", allJenis);

        //return view template
        return "form-add-obat";
    }

    @RequestMapping(value = "obat/tambah", method = RequestMethod.POST, params = "save")
    private String addProductSubmit(@ModelAttribute ObatModel obat, JenisModel jenis, Model model){
        List<SupplierModel> allSupplier = supplierService.getSupplierList();
        String kode = obatService.createKode(obat, jenis);
        obat.setKode(kode);

        obatService.add(obat); //obat udah ke save
        model.addAttribute("id", obat.getIdObat());
        model.addAttribute("notif", "Obat " + obat.getNama() + " dengan kode " + kode + " berhasil ditambahkan.");
        model.addAttribute("namaObat", obat.getNama());
        model.addAttribute("allSupplier", allSupplier);

        return "notif-add-obat";
    }

    @RequestMapping(value = "obat/tambah-supplier/{idObat}", method = RequestMethod.POST)
    private String addObatToGudang(@PathVariable(value = "idObat")@ModelAttribute Long idObat, ObatSupplierModel obatSupplier, Model model){
        ObatModel obat = obatService.getObatByIdObat(idObat).get();
        obatSupplier.setObat(obat);
        obatSupplierService.save(obatSupplier);

        List<SupplierModel> listSupplier = supplierService.getSupplierByObat(obat);
        List<SupplierModel> allSupplier = supplierService.getSupplierList();

        model.addAttribute("obat", obat);
        model.addAttribute("id", obat.getIdObat());
        model.addAttribute("listSupplier", listSupplier);
        model.addAttribute("allSupplier", allSupplier);
        model.addAttribute("notif", "Supplier " + obatSupplier.getSupplier().getNama() +
                " berhasil ditambahkan sebagai penyedia obat " + obat.getNama() + " pada " + java.time.LocalDate.now() );
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

    @RequestMapping(value = "/obat/ubah{idObat}", method = RequestMethod.GET)
    public String changeObat(@PathVariable Long idObat, Model model) {
        ObatModel existingobat = obatService.getObatByIdObat(idObat).get();
        model.addAttribute("obat", existingobat);
        return "form-change-obat";
    }

    @RequestMapping(value = "obat/ubah{idObat}", method = RequestMethod.POST)
    private String changeObat(@PathVariable(value = "idObat")@ModelAttribute Long idObat, ObatModel obat, Model model){
        ObatModel currObat = obatService.getObatByIdObat(idObat).get();
        List<String> input = Arrays.asList(obat.getHarga().split(" "));
        double price = Double.parseDouble(input.get(1));

        currObat.setHarga(price);
        currObat.setTanggalTerbit(obat.getTanggalTerbit());
        currObat.setNama(obat.getNama());
        obat.setJenis(currObat.getJenis());
        String kode = obatService.createKode(obat, obat.getJenis());
        currObat.setKode(kode);

        obatService.save(currObat);

        model.addAttribute("namaObat", currObat.getNama());
        model.addAttribute("kodeObat", currObat.getKode());

        return "notif-update-obat";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public String filter(@RequestParam(value = "idGudang", required = false) Long idGudang, @RequestParam(value = "idSupplier",
            required = false) Long idSupplier, @RequestParam(value = "idJenis", required = false) Long idJenis, Model model){
        List<GudangModel> listGudang = gudangService.getGudangList();
        List<JenisModel> listJenis = jenisService.getJenisList();
        List<SupplierModel> listSupplier = supplierService.getSupplierList();

        model.addAttribute("listGudang", listGudang);
        model.addAttribute("listJenis", listJenis);
        model.addAttribute("listSupplier", listSupplier);

        if(!(idGudang==null || idSupplier==null || idJenis==null)){
            List<ObatModel> listObat = new ArrayList<ObatModel>();

            GudangModel gudang = gudangService.getGudangByIdGudang(idGudang).get();
            SupplierModel supplier = supplierService.getSupplierByIdSupplier(idSupplier).get();
            JenisModel jenis = jenisService.getJenisByIdJenis(idJenis).get();

            List<GudangObatModel> allRelationGudangObat = gudangObatService.getGudangObatList();
            List<ObatModel> data1 = new ArrayList<ObatModel>();
            List<ObatSupplierModel> allRelationObatSupplier = obatSupplierService.getObatSupplierList();
            List<ObatModel> data2 = new ArrayList<ObatModel>();

            for(GudangObatModel gudangObat : allRelationGudangObat){
                if(gudangObat.getGudang().getIdGudang().equals(gudang.getIdGudang())){
                    data1.add(gudangObat.getObat());
                }
            }
            for(ObatSupplierModel obatSupplier : allRelationObatSupplier){
                if(obatSupplier.getSupplier().equals(supplier)){
                    data2.add(obatSupplier.getObat());
                }
            }
            List<ObatModel> data3 = jenis.getListObat();

            for (ObatModel obat : data1){
                if (data2.contains(obat) && data3.contains(obat)){
                    listObat.add(obat);
                }
            }

            System.out.println(listObat.get(1).getNama());

            model.addAttribute("allObat", listObat);
            model.addAttribute("gudangg",gudang.getNama());
            model.addAttribute("supplierr", supplier.getNama());
            model.addAttribute("jeniss", jenis.getNama());
        }

        if((idGudang!=null) && (idSupplier==null) && idJenis==null){
            List<ObatModel> listObat = new ArrayList<ObatModel>();
            GudangModel gudang = gudangService.getGudangByIdGudang(idGudang).get();
            List<GudangObatModel> allRelationGudangObat = gudangObatService.getGudangObatList();
            for(GudangObatModel gudangObat : allRelationGudangObat){
                if(gudangObat.getGudang().getIdGudang().equals(gudang.getIdGudang())){
                    listObat.add(gudangObat.getObat());
                }
            }
            model.addAttribute("listObat", listObat);
            model.addAttribute("gudang",gudang.getNama());
            model.addAttribute("supplier", " ");
            model.addAttribute("jenis", " ");
        }

        if((idGudang!=null) && (idSupplier!=null) && idJenis==null){
            List<ObatModel> listObat = new ArrayList<ObatModel>();
            GudangModel gudang = gudangService.getGudangByIdGudang(idGudang).get();
            SupplierModel supplier = supplierService.getSupplierByIdSupplier(idSupplier).get();

            List<GudangObatModel> allRelationGudangObat = gudangObatService.getGudangObatList();
            List<ObatModel> data1 = new ArrayList<ObatModel>();
            List<ObatSupplierModel> allRelationObatSupplier = obatSupplierService.getObatSupplierList();
            List<ObatModel> data2 = new ArrayList<ObatModel>();

            for(GudangObatModel gudangObat : allRelationGudangObat){
                if(gudangObat.getGudang().getIdGudang().equals(gudang.getIdGudang())){
                    data1.add(gudangObat.getObat());
                }
            }
            for(ObatSupplierModel obatSupplier : allRelationObatSupplier){
                if(obatSupplier.getSupplier().equals(supplier)){
                    data2.add(obatSupplier.getObat());
                }
            }
            for (ObatModel obat : data1){
                if (data2.contains(obat)){
                    listObat.add(obat);
                }
            }
            model.addAttribute("listObat", listObat);
            model.addAttribute("gudang",gudang.getNama());
            model.addAttribute("supplier", supplier.getNama());
            model.addAttribute("jenis", " ");
        }

        if((idGudang!=null) && (idSupplier==null) && (idJenis!=null)){
            List<ObatModel> listObat = new ArrayList<ObatModel>();
            GudangModel gudang = gudangService.getGudangByIdGudang(idGudang).get();
            JenisModel jenis = jenisService.getJenisByIdJenis(idJenis).get();
            List<GudangObatModel> allRelationGudangObat = gudangObatService.getGudangObatList();
            List<ObatModel> data1 = new ArrayList<ObatModel>();
            for(GudangObatModel gudangObat : allRelationGudangObat){
                if(gudangObat.getGudang().getIdGudang().equals(gudang.getIdGudang())){
                    data1.add(gudangObat.getObat());
                }
            }
            List<ObatModel> data3 = jenis.getListObat();
            for (ObatModel obat : data1){
                if (data3.contains(obat)){
                    listObat.add(obat);
                }
            }
            model.addAttribute("listObat", listObat);
            model.addAttribute("gudang",gudang.getNama());
            model.addAttribute("jenis", jenis.getNama());
            model.addAttribute("supplier", " ");
        }

        if((idGudang==null) && idSupplier!=null && idJenis!=null){
            List<ObatModel> listObat = new ArrayList<ObatModel>();
            SupplierModel supplier = supplierService.getSupplierByIdSupplier(idSupplier).get();
            JenisModel jenis = jenisService.getJenisByIdJenis(idJenis).get();

            List<ObatSupplierModel> allRelationObatSupplier = obatSupplierService.getObatSupplierList();
            List<ObatModel> data2 = new ArrayList<ObatModel>();

            for(ObatSupplierModel obatSupplier : allRelationObatSupplier){
                if(obatSupplier.getSupplier().equals(supplier)){
                    data2.add(obatSupplier.getObat());
                }
            }
            List<ObatModel> data3 = jenis.getListObat();

            for (ObatModel obat : data2){
                if (data3.contains(obat)){
                    listObat.add(obat);
                }
            }

            model.addAttribute("listObat", listObat);
            model.addAttribute("gudang"," ");
            model.addAttribute("supplier", supplier.getNama());
            model.addAttribute("jenis", jenis.getNama());
        }

        if(idGudang==null && idSupplier!=null && idJenis==null){
            List<ObatModel> listObat = new ArrayList<ObatModel>();
            SupplierModel supplier = supplierService.getSupplierByIdSupplier(idSupplier).get();

            List<ObatSupplierModel> allRelationObatSupplier = obatSupplierService.getObatSupplierList();
            List<ObatModel> data2 = new ArrayList<ObatModel>();

            for(ObatSupplierModel obatSupplier : allRelationObatSupplier){
                if(obatSupplier.getSupplier().equals(supplier)){
                    listObat.add(obatSupplier.getObat());
                }
            }

            model.addAttribute("listObat", listObat);
            model.addAttribute("gudang"," ");
            model.addAttribute("supplier", supplier.getNama());
            model.addAttribute("jenis", " ");
        }

        if(idGudang==null && idSupplier==null && idJenis!=null){
            List<ObatModel> listObat = new ArrayList<ObatModel>();
            JenisModel jenis = jenisService.getJenisByIdJenis(idJenis).get();

            List<ObatModel> data3 = jenis.getListObat();

            listObat.add((ObatModel) data3);

            model.addAttribute("listObat", listObat);
            model.addAttribute("gudang"," ");
            model.addAttribute("supplier", " ");
            model.addAttribute("jenis", jenis.getNama());
        }

        else{
            List<ObatModel> listObat = new ArrayList<ObatModel>();
            model.addAttribute("listGudang", listGudang);
            model.addAttribute("listJenis", listJenis);
            model.addAttribute("listSupplier", listSupplier);

            model.addAttribute("listObat", listObat);
            model.addAttribute("gudang"," ");
            model.addAttribute("supplier", " ");
            model.addAttribute("jenis", " ");
        }

        return "filter";
    }

/*@RequestMapping(value="/filter", method = RequestMethod.GET)
    public String cariObat(@RequestParam(value = "idGudang")Long idGudang, @RequestParam(value = "idJenis") Long idJenis,
                           @RequestParam(value = "idSupplier") Long idSupplier, @ModelAttribute Model model){
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
    }*/

/*  @RequestMapping(value="/obat/tambah", method = RequestMethod.GET)
    public String addObat(Model model) {
        List<SupplierModel> allSupplier = supplierService.getSupplierList();

        ObatModel obat = new ObatModel();

        //bikin relasi obatsupplier baru
        ArrayList<ObatSupplierModel> newObatSupplierList = new ArrayList<ObatSupplierModel>();
        //masukin model baru kedalem relasi yang dibuat barusan
        newObatSupplierList.add(new ObatSupplierModel());
        //ngeset yang dibikin di atas ke si obat
        obat.setListObatSupplier(newObatSupplierList);

        List<JenisModel> allJenis = jenisService.getJenisList();

        model.addAttribute("obat", obat);
        model.addAttribute("allJenis", allJenis);
        model.addAttribute("allSupplier", allSupplier);

        //return view template
        return "form-add-obat";
    }*/

/*    @RequestMapping(value="/obat/tambah", method = RequestMethod.POST, params = "addRow")
    public String addSupplierRow(@ModelAttribute ObatModel obat, Model model) {
        if(obat.getListObatSupplier()==null){
            obat.setListObatSupplier(new ArrayList<ObatSupplierModel>());
        }
        obat.getListObatSupplier().add(new ObatSupplierModel());
        List<SupplierModel> allSupplier = supplierService.getSupplierList();

        List<JenisModel> allJenis = jenisService.getJenisList();

        model.addAttribute("obat", obat);
        model.addAttribute("allJenis", allJenis);
        model.addAttribute("allSupplier", allSupplier);
        return "form-add-obat";
    }*/

/*     @RequestMapping(value = "obat/tambah", method = RequestMethod.POST, params = "save")
    private String addProductSubmit(@ModelAttribute ObatModel obat, JenisModel jenis, Model model){
        System.out.println(obat.getNama() + " nama obatnya");
        System.out.println("nama jenisnya " + jenis.getNama());
        String kode = obatService.createKode(obat, jenis);
        obat.setKode(kode);

        //listObatSupplier HARUSNYA itu relasi2 obatSupplier
        List<ObatSupplierModel> listObatSupplier = obat.getListObatSupplier();
        if (listObatSupplier.size() > 0){
            //ini ngeset relasi2 baru itu
            for (ObatSupplierModel sup : listObatSupplier){
                ObatSupplierModel obatSupplier = new ObatSupplierModel();
                obatSupplier.setObat(obat);
                System.out.println(obatSupplier + " alamat obat supplier");
                System.out.println("nama supplier " + obatSupplier.getSupplier().getNama());
                obatSupplierService.save(obatSupplier);
            }
        }

        obatService.add(obat); //obat udah ke save
        model.addAttribute("namaObat", obat.getNama());

        return "notif-add-obat";
    }*/

}
