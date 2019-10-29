package apap.tugas1.sibat.service;

import apap.tugas1.sibat.model.GudangModel;
import apap.tugas1.sibat.model.GudangObatModel;
import apap.tugas1.sibat.model.ObatModel;
import apap.tugas1.sibat.repository.GudangDb;
import apap.tugas1.sibat.repository.GudangObatDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class gudangServiceImpl implements GudangService{
    @Autowired private GudangDb gudangDb;
    @Autowired private GudangObatDb gudangObatDb;

    @Override
    public List<GudangModel> getGudangList() {
        return gudangDb.findAll();
    }

    @Override
    public void addGudang(GudangModel gudang) {
        gudangDb.save(gudang);
    }

    @Override
    public Optional<GudangModel> getGudangByIdGudang(Long idGudang) {
        return gudangDb.findById(idGudang);
    }

    //untuk akses data obat dalam gudang dia
    public List<ObatModel> getObatByGudang(GudangModel gudangModel){
        List<GudangObatModel> allRelation = gudangObatDb.findAll();
        List<ObatModel> listObat =new ArrayList<ObatModel>();
        for (GudangObatModel target : allRelation){
            if(target.getGudang().getIdGudang().equals(gudangModel.getIdGudang())){
                listObat.add(target.getObat());
            }
        }
        return listObat;
    }

    @Override
    public void addObat(ObatModel obat, GudangModel gudang) {
        System.out.println("JEDERRRR MASUK SERVICE IMPLLLL");
        GudangObatModel gudangObat = new GudangObatModel();
        gudangObat.setGudang(gudang);
        gudangObat.setObat(obat);
        System.out.println("NIHHHH GUDANGNYA ===" + gudangObat.getGudang().getNama());
        gudangObatDb.save(gudangObat);
    }

    @Override
    public void delete(GudangModel gudang) {
        gudangDb.delete(gudang);
    }
}

