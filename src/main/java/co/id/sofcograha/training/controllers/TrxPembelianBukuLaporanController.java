package co.id.sofcograha.training.controllers;

import co.id.sofcograha.base.utils.ApiResponse;
import co.id.sofcograha.base.utils.searchData.SearchParameter;
import co.id.sofcograha.base.utils.searchData.SearchResult;
import co.id.sofcograha.training.entities.TrxHeaderEntity;
import co.id.sofcograha.training.entities.TrxPembelianBukuLaporanEntity;
import co.id.sofcograha.training.pojos.TrxHeaderPojo;
import co.id.sofcograha.training.pojos.TrxPembelianBukuLaporanPojo;
import co.id.sofcograha.training.repositories.TrxHeaderRepository;
import co.id.sofcograha.training.services.TrxCompositePembelianBukuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/laporan-penjualan-buku")
public class TrxPembelianBukuLaporanController {

    @Autowired
    private TrxCompositePembelianBukuService trxCompositePembelianBukuService;
    @Autowired
    private TrxHeaderRepository trxHeaderRepository;

    // -------------------Retrieve Some ---------------------------------------------
    @GetMapping(value = "/search")
    public ApiResponse search(@RequestParam Map<String, String> searchParameter) {
        List<TrxPembelianBukuLaporanEntity> searchResult = trxHeaderRepository.getLaporanPenjualan();
        return ApiResponse.data("items", TrxPembelianBukuLaporanPojo.fromEntities(searchResult));
    }

}
