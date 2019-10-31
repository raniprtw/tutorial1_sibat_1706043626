package apap.tugas1.sibat.service;

import apap.tugas1.sibat.model.ObatModel;
import apap.tugas1.sibat.model.ObatSupplierModel;
import apap.tugas1.sibat.model.SupplierModel;
import apap.tugas1.sibat.repository.ObatSupplierDb;
import apap.tugas1.sibat.repository.SupplierDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class supplierServiceImpl implements SupplierService {
    @Autowired private SupplierDb supplierDb;
    @Autowired private ObatSupplierDb obatSupplierDb;

    @Override
    public List<SupplierModel> getSupplierList() {
        return supplierDb.findAll();
    }

    @Override
    public List<SupplierModel> getSupplierByObat(ObatModel obat) {
        List<ObatSupplierModel> allRelation = obatSupplierDb.findAll();
        List<SupplierModel> listSupplier =new ArrayList<SupplierModel>();
        for (ObatSupplierModel target : allRelation){
            if(target.getObat().getIdObat().equals(obat.getIdObat())){
                listSupplier.add(target.getSupplier());
            }
        }
        return listSupplier;
    }

    @Override
    public Optional<SupplierModel> getSupplierByIdSupplier(Long idSupplier) {
        return supplierDb.findById(idSupplier);
    }
}
