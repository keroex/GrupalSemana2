package com.utec.grupalsemana2.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.utec.grupalsemana2.logica.FormularioDTO;
import com.utec.grupalsemana2.repositories.FormularioRepository;

import java.util.List;

public class FormularioViewModel extends AndroidViewModel {

    private FormularioRepository formularioRepository;

    public FormularioViewModel(Application application){
        super(application);
        formularioRepository = new FormularioRepository(application);
    }

    public void insert(FormularioDTO formularioDTO){
        formularioRepository.insert(formularioDTO);
    }

    public void update(FormularioDTO formularioDTO){
        formularioRepository.update(formularioDTO);
    }

    public void delete(FormularioDTO formulario){
        formularioRepository.delete(formulario);
    }

    public int count() { return formularioRepository.count();    }

    public List<FormularioDTO> getFormularios() {
        return formularioRepository.getFormularioes();
    }
}
