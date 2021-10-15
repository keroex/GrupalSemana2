package com.utec.grupalsemana2.interfaces;

import java.util.List;

import com.utec.grupalsemana2.logica.ActividadDeCampo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ActividadDeCampoAPI {

   @GET("PFGrupo05/rest/Actividades/obtenerTodos")
   Call<List<ActividadDeCampo>> getActividadesDeCampo();

   @POST("PFGrupo05/rest/Actividades/agregarActividadDeCampo")
   Call<ActividadDeCampo> agregarActividadDeCampo(@Body ActividadDeCampo actividadDeCampo);

}
