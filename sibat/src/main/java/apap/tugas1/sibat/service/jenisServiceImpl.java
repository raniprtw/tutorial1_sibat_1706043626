package apap.tugas1.sibat.service;

import apap.tugas1.sibat.model.JenisModel;
import apap.tugas1.sibat.repository.JenisDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class jenisServiceImpl implements JenisService{
    @Autowired private JenisDb jenisDb;

    @Override
    public List<JenisModel> getJenisList() {
        return jenisDb.findAll();    }
}
