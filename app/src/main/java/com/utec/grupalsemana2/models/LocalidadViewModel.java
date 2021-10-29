package com.utec.grupalsemana2.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.utec.grupalsemana2.logica.LocalidadDTO;
import com.utec.grupalsemana2.repositories.LocalidadRepository;

import java.util.List;

public class LocalidadViewModel extends AndroidViewModel {

    private LocalidadRepository localidadRepository;

    public LocalidadViewModel(Application application) {
        super(application);
        localidadRepository = new LocalidadRepository(application);
    }

    public void insert(LocalidadDTO localidadDTO){
        localidadRepository.insert(localidadDTO);
    }

    public void update(LocalidadDTO localidadDTO){
        localidadRepository.update(localidadDTO);
    }

    public void delete(LocalidadDTO localidad){
        localidadRepository.delete(localidad);
    }

    public int count() { return localidadRepository.count();    }

    public List<LocalidadDTO> getLocalidades() {
        return localidadRepository.getLocalidades();
    }

    public List<LocalidadDTO> getLocalidadesXDepartamento(long iddepartamento) {
        return localidadRepository.getLocalidadesXDepartamento(iddepartamento);
    }
}
