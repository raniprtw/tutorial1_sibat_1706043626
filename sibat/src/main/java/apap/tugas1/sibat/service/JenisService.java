package apap.tugas1.sibat.service;

import apap.tugas1.sibat.model.JenisModel;

import java.util.List;
import java.util.Optional;

public interface JenisService {
    List<JenisModel> getJenisList();
    Optional<JenisModel> getJenisByIdJenis(Long idJenis);
}
