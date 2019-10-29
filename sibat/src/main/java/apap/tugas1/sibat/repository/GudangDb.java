package apap.tugas1.sibat.repository;

import apap.tugas1.sibat.model.GudangModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GudangDb extends JpaRepository<GudangModel, Long> {
}
