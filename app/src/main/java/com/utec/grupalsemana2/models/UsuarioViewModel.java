package com.utec.grupalsemana2.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.utec.grupalsemana2.logica.UsuarioDTO;
import com.utec.grupalsemana2.repositories.UsuarioRepository;

public class UsuarioViewModel extends AndroidViewModel {

    private UsuarioRepository usuarioRepository;

    public UsuarioViewModel(Application application){
        super(application);
        usuarioRepository = new UsuarioRepository(application);
    }

    public void insert(UsuarioDTO usuarioDTO){
        usuarioRepository.insert(usuarioDTO);
    }

    public long login(UsuarioDTO usuarioDTO) {
        return usuarioRepository.login(usuarioDTO);
    }

    public void update(UsuarioDTO usuarioDTO){
        usuarioRepository.update(usuarioDTO);
    }

}
