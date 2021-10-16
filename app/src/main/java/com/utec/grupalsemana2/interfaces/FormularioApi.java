package com.utec.grupalsemana2.interfaces;

import com.utec.grupalsemana2.logica.FormularioDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FormularioApi {

    @GET("PFGrupo05/rest/Formularios/obtenerTodos")
    Call<List<FormularioDTO>> getFormularios();

}
