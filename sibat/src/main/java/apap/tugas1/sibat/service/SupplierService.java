package apap.tugas1.sibat.service;

import apap.tugas1.sibat.model.ObatModel;
import apap.tugas1.sibat.model.SupplierModel;

import java.util.List;
import java.util.Optional;

public interface SupplierService {
    List<SupplierModel> getSupplierList();
    List<SupplierModel> getSupplierByObat(ObatModel obat);
    Optional<SupplierModel> getSupplierByIdSupplier(Long idSupplier);
}
