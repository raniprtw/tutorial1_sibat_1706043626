package apap.tugas1.sibat.service;

import apap.tugas1.sibat.model.GudangModel;
import apap.tugas1.sibat.model.GudangObatModel;
import apap.tugas1.sibat.model.ObatModel;
import apap.tugas1.sibat.repository.GudangObatDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class gudangObatServiceImpl implements GudangObatService {
    @Autowired private GudangObatDb gudangObatDb;

    @Override
    public void add(GudangObatModel gudangObat) {
        gudangObatDb.save(gudangObat);
    }

    @Override
    public List<GudangObatModel> getGudangObatList() {
        return gudangObatDb.findAll();
    }
}
