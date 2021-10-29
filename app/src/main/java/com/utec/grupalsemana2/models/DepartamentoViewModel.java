package com.utec.grupalsemana2.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.utec.grupalsemana2.logica.DepartamentoDTO;
import com.utec.grupalsemana2.repositories.DepartamentoRepository;

import java.util.List;

public class DepartamentoViewModel extends AndroidViewModel {

    private DepartamentoRepository departamentoRepository;

    public DepartamentoViewModel(Application application){
        super(application);
        departamentoRepository = new DepartamentoRepository(application);
    }

    public void insert(DepartamentoDTO departamentoDTO){
        departamentoRepository.insert(departamentoDTO);
    }

    public void update(DepartamentoDTO departamentoDTO){
        departamentoRepository.update(departamentoDTO);
    }

    public void delete(DepartamentoDTO departamento){
        departamentoRepository.delete(departamento);
    }

    public int count() { return departamentoRepository.count();    }

    public List<DepartamentoDTO> getDepartamentos() {
        return departamentoRepository.getDepartamentoes();
    }

    public List<DepartamentoDTO> getDepartamentosXRegion(long idregion) {
        return departamentoRepository.getDepartamentosXRegion(idregion);
    }
}
