package apap.tugas1.sibat.service;

import apap.tugas1.sibat.model.ObatSupplierModel;

import java.util.List;

public interface ObatSupplierService {
    void save(ObatSupplierModel obatSupplier);

    List<ObatSupplierModel> getObatSupplierList();
}
