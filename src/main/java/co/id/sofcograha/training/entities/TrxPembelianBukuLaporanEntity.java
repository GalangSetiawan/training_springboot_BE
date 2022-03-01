package co.id.sofcograha.training.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TrxPembelianBukuLaporanEntity implements Cloneable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name="bulan")
    private String bulan;

    @Column(name="total_qty_penjualan")
    private Integer TotalQtyPenjualanBuku;

    @Column(name="total_nominal_penjualan")
    private Double TotalNominalPenjualanBuku;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public Integer getTotalQtyPenjualanBuku() {
        return TotalQtyPenjualanBuku;
    }

    public void setTotalQtyPenjualanBuku(Integer totalQtyPenjualanBuku) {
        TotalQtyPenjualanBuku = totalQtyPenjualanBuku;
    }

    public Double getTotalNominalPenjualanBuku() {
        return TotalNominalPenjualanBuku;
    }

    public void setTotalNominalPenjualanBuku(Double totalNominalPenjualanBuku) {
        TotalNominalPenjualanBuku = totalNominalPenjualanBuku;
    }
}
