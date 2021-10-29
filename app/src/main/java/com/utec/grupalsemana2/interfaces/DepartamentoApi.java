package com.utec.grupalsemana2.interfaces;

import com.utec.grupalsemana2.logica.DepartamentoDTO;
import com.utec.grupalsemana2.logica.RegionDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DepartamentoApi {

    @POST("PFGrupo05/rest/Departamentos/obtenerTodosXRegion")
    Call<List<DepartamentoDTO>> getDepartamentos(@Body RegionDTO regionDTO);

    @GET("PFGrupo05/rest/Departamentos/obtenerTodos")
    Call<List<DepartamentoDTO>> getDepartamentosTodos();

}
