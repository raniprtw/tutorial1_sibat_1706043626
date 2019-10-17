package apap.tugas1.sibat.service;

import apap.tugas1.sibat.model.GudangModel;
import apap.tugas1.sibat.repository.GudangDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class gudangServiceImpl implements GudangService{
    @Autowired private GudangDb gudangDb;

    @Override
    public List<GudangModel> getGudangList() {
        return gudangDb.findAll();
    }

    @Override
    public void addGudang(GudangModel gudang) {
        gudangDb.save(gudang);
    }
}

