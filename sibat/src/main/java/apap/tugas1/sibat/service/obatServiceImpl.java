package apap.tugas1.sibat.service;

import apap.tugas1.sibat.model.*;
import apap.tugas1.sibat.repository.GudangObatDb;
import apap.tugas1.sibat.repository.ObatDb;
import apap.tugas1.sibat.repository.ObatSupplierDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class obatServiceImpl implements ObatService{
    @Autowired private ObatDb obatDb;
    @Autowired private GudangObatDb gudangObatDb;
    @Autowired private ObatSupplierDb obatSupplierDb;

    @Override public List<ObatModel> getObatList() {
        return obatDb.findAll();
    }

    @Override public Optional<ObatModel> getObatByIdObat(Long idObat) {
        return obatDb.findById(idObat);
    }

    @Override public Optional<ObatModel> getObatByNomorRegistrasi(String nomorRegistrasi) {
        return obatDb.findByNomorRegistrasi(nomorRegistrasi);
    }

    public String getGudangByObat(ObatModel obatModel){
        List<GudangObatModel> allRelation = gudangObatDb.findAll();
        List<GudangModel> gudangModel = new ArrayList<GudangModel>();
        for (GudangObatModel target : allRelation){
            if(target.getObat().getKode().equals(obatModel.getKode())){
                gudangModel.add(target.getGudang());
            }
        }
        if(gudangModel.size() == 1){ return gudangModel.get(0).getNama(); }
        if(gudangModel.size()==0){ return "-";}
        else{
            String inv = "";
            for(GudangModel x : gudangModel){
                System.out.println("gudang " + x + " " + x.getNama());
                inv += x.getNama() + ", ";
            }
            return inv;
        }
    }

    public String getSupplierByObat(ObatModel obatModel){
        List<ObatSupplierModel> allRelation = obatSupplierDb.findAll();
        List<SupplierModel> supplierModel = new ArrayList<SupplierModel>();
        String supp = "";
        for(ObatSupplierModel target : allRelation){
            if(target.getObat().getKode().equals(obatModel.getKode())){
                supplierModel.add(target.getSupplier());
            }
        }
        if(supplierModel.size()==1){ return supplierModel.get(0).getNama(); }
        if(supplierModel.size()==0){ return "-";}
        else{
            for(SupplierModel x : supplierModel){
                supp = supp + x.getNama() + ", ";
            }
            return supp;
        }
    }

    public List<SupplierModel> getSupplierByObatList(ObatModel obatModel){
        List<ObatSupplierModel> allRelation = obatSupplierDb.findAll();
        List<SupplierModel> supplierModel = new ArrayList<SupplierModel>();
        for(ObatSupplierModel target : allRelation){
            if(target.getObat().getKode().equals(obatModel.getKode())){
                supplierModel.add(target.getSupplier());
            }
        }
        return supplierModel;
    }

    @Override
    public String getKode(ObatModel obatModel) {
        String kode = "";

        return kode;
    }

    @Override
    public void add(ObatModel obat) { obatDb.save(obat);}

    @Override
    public void delete(ObatModel obat) { obatDb.delete(obat); }

    public String createKode(ObatModel obat, JenisModel jenis){
        String kode = "";
        if(jenis.getNama().equals("Paten")){kode += "1";}
        else{kode += "2";}

        if(obat.getBentuk().equals("Cairan")){kode += "01";}
        if(obat.getBentuk().equals("Kapsul")){kode += "02";}
        else{kode += "03";}

        String year = String.valueOf(obat.getTanggalTerbit().getYear());
        kode += year;
        kode += String.valueOf(obat.getTanggalTerbit().getYear() + 5);

        Random hurufRandom = new Random();
        char random1 = (char)(hurufRandom.nextInt(26) + 'A');
        char random2 = (char)(hurufRandom.nextInt(26) + 'A');
        kode += random1;
        kode += random2;

        boolean checked = cekKode(kode);
        if(checked==false){
            createKode(obat, jenis);
        }

        return kode;
    }

    @Override
    public boolean cekKode(String kode) {
        List<ObatModel> allObat = obatDb.findAll();
        boolean status = true;
        for (ObatModel obat : allObat ){
            if(obat.getKode().equals(kode)){

                return false;
            }
        }
        return status;
    }

}
