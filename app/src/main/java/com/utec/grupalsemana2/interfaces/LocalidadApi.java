package com.utec.grupalsemana2.interfaces;

import com.utec.grupalsemana2.logica.DepartamentoDTO;
import com.utec.grupalsemana2.logica.LocalidadDTO;
import com.utec.grupalsemana2.logica.RegionDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LocalidadApi {

    @POST("PFGrupo05/rest/Localidades/obtenerTodosXDepartamento")
    Call<List<LocalidadDTO>> getLocalidades(@Body DepartamentoDTO departamentoDTO);
}
