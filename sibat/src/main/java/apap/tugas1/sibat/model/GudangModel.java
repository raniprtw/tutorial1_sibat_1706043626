package apap.tugas1.sibat.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "gudang")
public class GudangModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGudang;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max = 255)
    @Column(name = "alamat", nullable = false)
    private String alamat;

    @OneToMany(mappedBy = "gudang", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GudangObatModel> listGudangobat;

    public Long getIdGudang() {
        return idGudang;
    }

    public void setIdGudang(Long idGudang) {
        this.idGudang = idGudang;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public List<GudangObatModel> getListGudangobat() {
        return listGudangobat;
    }

    public void setListGudangobat(List<GudangObatModel> listGudangobat) {
        this.listGudangobat = listGudangobat;
    }
}
