package apap.tugas1.sibat.service;

import apap.tugas1.sibat.model.JenisModel;
import apap.tugas1.sibat.model.ObatModel;
import apap.tugas1.sibat.model.SupplierModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ObatService {

    List<ObatModel> getObatList();

    Optional<ObatModel> getObatByIdObat(Long idObat);

    Optional<ObatModel> getObatByNomorRegistrasi(String nomorRegistrasi);

    String getGudangByObat(ObatModel obatModel);

    String getSupplierByObat(ObatModel obatModel);

    List<SupplierModel> getSupplierByObatList(ObatModel obatModel);

    String getKode(ObatModel obatModel);

    void add(ObatModel obat);

    boolean cekKode(String kode);

    String createKode(ObatModel obat, JenisModel jenis);

    void delete(ObatModel obat);
}
