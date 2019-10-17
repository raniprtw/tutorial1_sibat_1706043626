package apap.tugas1.sibat.service;

import apap.tugas1.sibat.model.ObatModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ObatService {

    List<ObatModel> getObatList();

    Optional<ObatModel> getObatByIdObat(Long idObat);

    Optional<ObatModel> getObatByNomorRegistrasi(String nomorRegistrasi);

    String getGudangByObat(ObatModel obatModel);

    String getSupplierByObat(ObatModel obatModel);
}
