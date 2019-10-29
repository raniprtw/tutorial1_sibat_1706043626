package apap.tugas1.sibat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "obat")
public class ObatModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idObat;

    @NotNull
    @Size(max = 255)
    @Column(name = "bentuk", nullable = false)
    private String bentuk;

    @NotNull
    @Column(name = "harga", nullable = false)
    private Double harga;

    @NotNull
    /*@UniqueElements*/
    @Size(max = 255)
    @Column(name = "kode", nullable = false)
    private String kode;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max = 255)
    @Column(name = "nomorRegistrasi", nullable = false)
    private String nomorRegistrasi;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "tanggalTerbit", nullable = false)
    private LocalDate tanggalTerbit;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "jenisId", referencedColumnName = "idJenis", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private JenisModel jenis;

    @OneToMany(mappedBy = "obat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GudangObatModel> listGudangObat;

    @OneToMany(mappedBy = "obat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ObatSupplierModel> listObatSupplier;

    public Long getIdObat() {
        return idObat;
    }

    public void setIdObat(Long idObat) {
        this.idObat = idObat;
    }

    public String getBentuk() {
        return bentuk;
    }

    public void setBentuk(String bentuk) {
        if(bentuk=="1"){this.bentuk = "Cairan";}
        if(bentuk=="2"){this.bentuk = "Kapsul";}
        else{this.bentuk="Tablet";}
    }

    public String getHarga() {
        return "Rp " + Math.round(harga);
    }

    public void setHarga(Double harga) {
        this.harga = harga;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomorRegistrasi() {
        return nomorRegistrasi;
    }

    public void setNomorRegistrasi(String nomorRegistrasi) {
        this.nomorRegistrasi = nomorRegistrasi;
    }

    public LocalDate getTanggalTerbit() {
        return tanggalTerbit;
    }

    public void setTanggalTerbit(LocalDate tanggalTerbit) {
        this.tanggalTerbit = tanggalTerbit;
    }

    public JenisModel getJenis() {
        return jenis;
    }

    public void setJenis(JenisModel jenis) {
        this.jenis = jenis;
    }

    public List<GudangObatModel> getListGudangobat() {
        return listGudangObat;
    }

    public void setListGudangobat(List<GudangObatModel> listGudangobat) {
        this.listGudangObat = listGudangobat;
    }

    public List<ObatSupplierModel> getListObatSupplier() {
        return listObatSupplier;
    }

    public void setListObatSupplier(List<ObatSupplierModel> listObatSupplier) { this.listObatSupplier = listObatSupplier; }


}
