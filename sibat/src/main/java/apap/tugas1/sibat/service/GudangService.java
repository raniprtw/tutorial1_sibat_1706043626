package apap.tugas1.sibat.service;

import apap.tugas1.sibat.model.GudangModel;

import java.util.List;

public interface GudangService {
    List<GudangModel> getGudangList();
    void addGudang(GudangModel gudang);
}
