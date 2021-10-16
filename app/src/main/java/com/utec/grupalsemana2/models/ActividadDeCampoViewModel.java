package com.utec.grupalsemana2.models;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.repositories.ActividadDeCampoRepository;

import java.util.List;

public class ActividadDeCampoViewModel extends AndroidViewModel {

    private ActividadDeCampoRepository actividadDeCampoRepository;
    //private final LiveData<List<ActividadDeCampo>> actividadDeCampos;
    private final List<ActividadDeCampo> actividadDeCampos;

    public ActividadDeCampoViewModel(Application application){
        super(application);
        actividadDeCampoRepository = new ActividadDeCampoRepository(application);
        actividadDeCampos = actividadDeCampoRepository.getActividadDeCampos();
    }

    //public LiveData<List<ActividadDeCampo>> getActividadDeCampos() {        return actividadDeCampos;    }

    public List<ActividadDeCampo> getActividadDeCampos() {
        return actividadDeCampos;
    }

    public void insert(ActividadDeCampo actividadDeCampo, Context context){
        actividadDeCampoRepository.insert(actividadDeCampo, context);
    }

    public void update(ActividadDeCampo actividadDeCampo){
        actividadDeCampoRepository.update(actividadDeCampo);
    }

    public void delete(ActividadDeCampo actividadDeCampo){
        actividadDeCampoRepository.delete(actividadDeCampo);
    }

    public int count() { return actividadDeCampoRepository.count();    }
}

