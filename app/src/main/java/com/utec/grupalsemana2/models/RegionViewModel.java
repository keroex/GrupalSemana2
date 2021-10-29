package com.utec.grupalsemana2.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.utec.grupalsemana2.logica.RegionDTO;
import com.utec.grupalsemana2.logica.RegionDTO;
import com.utec.grupalsemana2.repositories.RegionRepository;

import java.util.List;

public class RegionViewModel extends AndroidViewModel {

    private RegionRepository regionRepository;

    public RegionViewModel(Application application){
        super(application);
        regionRepository = new RegionRepository(application);
    }

    public void insert(RegionDTO regionDTO){
        regionRepository.insert(regionDTO);
    }

    public void update(RegionDTO regionDTO){
        regionRepository.update(regionDTO);
    }

    public void delete(RegionDTO region){
        regionRepository.delete(region);
    }

    public int count() { return regionRepository.count();    }

    public List<RegionDTO> getRegions() {
        return regionRepository.getRegiones();
    }
    
}
