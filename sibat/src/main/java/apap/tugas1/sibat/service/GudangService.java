package apap.tugas1.sibat.service;

import apap.tugas1.sibat.model.GudangModel;
import apap.tugas1.sibat.model.ObatModel;

import java.util.List;
import java.util.Optional;

public interface GudangService {
    List<GudangModel> getGudangList();
    void addGudang(GudangModel gudang);
    Optional<GudangModel> getGudangByIdGudang(Long idGudang);
    List<ObatModel> getObatByGudang(GudangModel gudangModel);
    void addObat(ObatModel obat, GudangModel gudang);
    void delete(GudangModel gudang);
}
