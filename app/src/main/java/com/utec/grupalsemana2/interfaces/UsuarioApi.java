package com.utec.grupalsemana2.interfaces;

import com.utec.grupalsemana2.logica.UsuarioDTO;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsuarioApi {

    @POST("PFGrupo05/rest/Usuarios/login")
    Call<UsuarioDTO> login(@Body UsuarioDTO usuarioLogueado);

}
