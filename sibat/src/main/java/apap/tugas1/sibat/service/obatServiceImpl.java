package apap.tugas1.sibat.service;

import apap.tugas1.sibat.model.*;
import apap.tugas1.sibat.repository.GudangObatDb;
import apap.tugas1.sibat.repository.ObatDb;
import apap.tugas1.sibat.repository.ObatSupplierDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class obatServiceImpl implements ObatService{
    @Autowired private ObatDb obatDb;
    @Autowired private GudangObatDb gudangObatDb;
    @Autowired private ObatSupplierDb obatSupplierDb;

    @Override
    public List<ObatModel> getObatList() {
        return obatDb.findAll();
    }

    @Override
    public Optional<ObatModel> getObatByIdObat(Long idObat) {
        return obatDb.findById(idObat);
    }

    @Override
    public Optional<ObatModel> getObatByNomorRegistrasi(String nomorRegistrasi) {
        return obatDb.findByNomorRegistrasi(nomorRegistrasi);
    }

    public String getGudangByObat(ObatModel obatModel){
        List<GudangObatModel> allRelation = gudangObatDb.findAll();
        GudangModel gudang = new GudangModel();
        for (GudangObatModel target : allRelation){
            if(target.getObat().getKode().equals(obatModel.getKode())){
                gudang = target.getGudang();
            }
        }
        if(gudang == null){
            gudang.setNama(" ");
        }
        return gudang.getNama();
    }

    public String getSupplierByObat(ObatModel obatModel){
        List<ObatSupplierModel> allRelation = obatSupplierDb.findAll();
        SupplierModel supplier = new SupplierModel();
        for(ObatSupplierModel target : allRelation){
            if(target.getObat().getKode().equals(obatModel.getKode())){
                supplier = target.getSupplier();
            }
        }
        if(supplier==null){
            supplier.setNama(" ");
        }
        return supplier.getNama();
    }
}
