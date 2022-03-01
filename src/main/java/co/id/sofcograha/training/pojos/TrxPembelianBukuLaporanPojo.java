package co.id.sofcograha.training.pojos;

import co.id.sofcograha.base.constants.BaseConstants;
import co.id.sofcograha.base.utils.searchData.SearchFieldMapping;
import co.id.sofcograha.training.entities.MembershipGetSaldoKasEntity;
import co.id.sofcograha.training.entities.TrxPembelianBukuLaporanEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class TrxPembelianBukuLaporanPojo {

    public String id;
    public String bulan;
    public Integer TotalQtyPenjualanBuku;
    public Double TotalNominalPenjualanBuku;

    public TrxPembelianBukuLaporanEntity toEntity() {

        TrxPembelianBukuLaporanEntity entity = new TrxPembelianBukuLaporanEntity();
        entity.setId(id);
        entity.setBulan(bulan);
        entity.setTotalQtyPenjualanBuku(TotalQtyPenjualanBuku);
        entity.setTotalNominalPenjualanBuku(TotalNominalPenjualanBuku);

        return entity;
    }

    public static TrxPembelianBukuLaporanPojo fromEntity (TrxPembelianBukuLaporanEntity entity) {
        return fromEntity(entity, BaseConstants.DEFAULT_QUERY_DEPTH);
    }

    public static TrxPembelianBukuLaporanPojo fromEntity(TrxPembelianBukuLaporanEntity entity, int depthLevel) {
        if (entity == null) return null;
        if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;

        TrxPembelianBukuLaporanPojo pojo = new TrxPembelianBukuLaporanPojo();

        pojo.id = entity.getId();
        if (depthLevel > 0) {
            depthLevel--;

            pojo.id = entity.getId();
            pojo.bulan = entity.getBulan();
            pojo.TotalQtyPenjualanBuku = entity.getTotalQtyPenjualanBuku();
            pojo.TotalNominalPenjualanBuku = entity.getTotalNominalPenjualanBuku();

        }

        return pojo;
    }

    public static List<TrxPembelianBukuLaporanPojo> fromEntities(List<TrxPembelianBukuLaporanEntity> entities){
        return fromEntities(entities,BaseConstants.DEFAULT_QUERY_DEPTH);
    }

    public static List<TrxPembelianBukuLaporanPojo> fromEntities(List<TrxPembelianBukuLaporanEntity> entities, int depthLevel){
        if (entities == null) return new ArrayList<>();
        if (depthLevel < 0) depthLevel = BaseConstants.DEFAULT_QUERY_DEPTH;

        List<TrxPembelianBukuLaporanPojo> pojos = new ArrayList<>();

        for (TrxPembelianBukuLaporanEntity entity : entities) {
            pojos.add(TrxPembelianBukuLaporanPojo.fromEntity(entity, depthLevel));
        }
        return pojos;
    }

    public static List<SearchFieldMapping> getFieldMappings() {
        List<SearchFieldMapping> mappings = new ArrayList<>();

        mappings.add(new SearchFieldMapping("bulan", "A.nama_member"));

        return mappings;
    }

}
