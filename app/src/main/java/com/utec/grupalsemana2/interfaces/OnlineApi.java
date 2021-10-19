package com.utec.grupalsemana2.interfaces;

import androidx.annotation.Nullable;

import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.logica.UsuarioDTO;

import org.jetbrains.annotations.Contract;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface OnlineApi {

    @GET("PFGrupo05/rest/Online")
    Call<Boolean> online();
}
