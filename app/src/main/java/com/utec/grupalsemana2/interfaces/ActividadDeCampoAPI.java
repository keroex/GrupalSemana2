package com.utec.grupalsemana2.interfaces;

import java.util.List;

import com.utec.grupalsemana2.logica.ActividadDeCampo;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ActividadDeCampoAPI {

   @GET("PFGrupo05/rest/Actividades/obtenerTodos")
   Call<List<ActividadDeCampo>> getActividadesDeCampo();

}
