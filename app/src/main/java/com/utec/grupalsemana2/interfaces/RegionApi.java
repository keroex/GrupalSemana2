package com.utec.grupalsemana2.interfaces;

import com.utec.grupalsemana2.logica.RegionDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RegionApi {

    @GET("PFGrupo05/rest/Regiones/obtenerTodos")
    Call<List<RegionDTO>> getRegiones();

}
