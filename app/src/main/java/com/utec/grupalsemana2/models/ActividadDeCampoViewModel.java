package com.utec.grupalsemana2.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.repositories.ActividadDeCampoRepository;

import java.util.List;

public class ActividadDeCampoViewModel extends AndroidViewModel {

    private ActividadDeCampoRepository actividadDeCampoRepository;
    private final List<ActividadDeCampo> actividadDeCampos;
    private final LiveData<List<ActividadDeCampo>> actividadesDeCampoXUsuario;

    public ActividadDeCampoViewModel(@NonNull Application application){
        super(application);
        actividadDeCampoRepository = new ActividadDeCampoRepository(application);
        actividadDeCampos = actividadDeCampoRepository.getActividadDeCampos();
        actividadesDeCampoXUsuario = actividadDeCampoRepository.getActividadesDeCampoXUsuario();
    }

    public List<ActividadDeCampo> getActividadDeCampos() {
        return actividadDeCampos;
    }

    public LiveData<List<ActividadDeCampo>> getActividadesDeCampoXUsuario() {
        return actividadesDeCampoXUsuario;
    }

    public void insert(ActividadDeCampo actividadDeCampo){
        actividadDeCampoRepository.insert(actividadDeCampo);
    }

    /*public void update(ActividadDeCampo actividadDeCampo){
        actividadDeCampoRepository.update(actividadDeCampo);
    }

    public void delete(ActividadDeCampo actividadDeCampo){
        actividadDeCampoRepository.delete(actividadDeCampo);
    }
*/
    public int count() { return actividadDeCampoRepository.count();    }
}
