package com.utec.grupalsemana2.models;

import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.repositories.ActividadDeCampoRepository;

import java.net.ConnectException;
import java.util.List;

public class ActividadDeCampoViewModel extends AndroidViewModel {

    private ActividadDeCampoRepository actividadDeCampoRepository;
    private final List<ActividadDeCampo> actividadDeCampos;

    public ActividadDeCampoViewModel(Application application){
        super(application);
        actividadDeCampoRepository = new ActividadDeCampoRepository(application);
        actividadDeCampos = actividadDeCampoRepository.getActividadDeCampos();
    }

    public List<ActividadDeCampo> getActividadDeCampos() {
        return actividadDeCampos;
    }

    public void insertRest(ActividadDeCampo actividadDeCampo){
        actividadDeCampoRepository.insertRest(actividadDeCampo, getApplication());
    }

    public void insertDao(ActividadDeCampo actividadDeCampo, Context context){
        actividadDeCampoRepository.insertDao(actividadDeCampo, context);
    }


    public void update(ActividadDeCampo actividadDeCampo){
        actividadDeCampoRepository.update(actividadDeCampo);
    }

    public void delete(ActividadDeCampo actividadDeCampo){
        actividadDeCampoRepository.delete(actividadDeCampo);
    }

    public int count() { return actividadDeCampoRepository.count();    }

    public ActividadDeCampo actividadDeCampoVieja() { return actividadDeCampoRepository.actividadDeCampoVieja();}
}

