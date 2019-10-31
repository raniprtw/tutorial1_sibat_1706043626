package apap.tugas1.sibat.service;

import apap.tugas1.sibat.model.ObatSupplierModel;
import apap.tugas1.sibat.repository.ObatSupplierDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class obatSupplierServiceImpl implements ObatSupplierService {
    @Autowired private ObatSupplierDb obatSupplierDb;

    @Override
    public void save(ObatSupplierModel obatSupplier) {
        obatSupplierDb.save(obatSupplier);
    }

    @Override
    public List<ObatSupplierModel> getObatSupplierList() {
        return obatSupplierDb.findAll();
    }
}
