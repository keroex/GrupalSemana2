package com.utec.grupalsemana2.servicios;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.utec.grupalsemana2.interfaces.DepartamentoApi;
import com.utec.grupalsemana2.interfaces.FormularioApi;
import com.utec.grupalsemana2.interfaces.LocalidadApi;
import com.utec.grupalsemana2.interfaces.RegionApi;
import com.utec.grupalsemana2.interfaces.UsuarioApi;
import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.logica.DepartamentoDTO;
import com.utec.grupalsemana2.logica.FormularioDTO;
import com.utec.grupalsemana2.logica.LocalidadDTO;
import com.utec.grupalsemana2.logica.RegionDTO;
import com.utec.grupalsemana2.models.ActividadDeCampoViewModel;
import com.utec.grupalsemana2.models.DepartamentoViewModel;
import com.utec.grupalsemana2.models.FormularioViewModel;
import com.utec.grupalsemana2.models.LocalidadViewModel;
import com.utec.grupalsemana2.models.RegionViewModel;
import com.utec.grupalsemana2.sesion.Sesion;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicioSpiners extends Service {
    Runnable runnable;
    private Handler handler = new Handler();
    private MutableLiveData<List<RegionDTO>> regionesRest = new MutableLiveData<>();
    private RegionApi regionApi = RestAppClient.getClient().create(RegionApi.class);
    private RegionViewModel regionViewModel;
    private MutableLiveData<List<DepartamentoDTO>> departamentoesRest = new MutableLiveData<>();
    private DepartamentoApi departamentoApi = RestAppClient.getClient().create(DepartamentoApi.class);
    private DepartamentoViewModel departamentoViewModel;
    private MutableLiveData<List<LocalidadDTO>> localidadesRest = new MutableLiveData<>();
    private LocalidadApi localidadApi = RestAppClient.getClient().create(LocalidadApi.class);
    private LocalidadViewModel localidadViewModel;
    private MutableLiveData<List<FormularioDTO>> formulariosRest = new MutableLiveData<>();
    private FormularioApi formularioApi = RestAppClient.getClient().create(FormularioApi.class);
    private FormularioViewModel formularioViewModel;
    private int contador=0;
    private int tiempo=500;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        runnable = new Runnable() {
            @Override
            public void run() {
                //Log.i("SERVICIO_REGIONES", "run");

                regionViewModel = new RegionViewModel(getApplication());
                departamentoViewModel = new DepartamentoViewModel(getApplication());
                localidadViewModel = new LocalidadViewModel(getApplication());
                formularioViewModel = new FormularioViewModel(getApplication());
                List<FormularioDTO> formulariosBD = formularioViewModel.getFormularios();
                List<RegionDTO> regionesBD = regionViewModel.getRegions();
                List<DepartamentoDTO> departamentoesBD = departamentoViewModel.getDepartamentos();
                List<LocalidadDTO> localidadesBD = localidadViewModel.getLocalidades();
                if (formulariosBD.size()==0) {
                    actualizarFormularios();
                }
                else if (regionesBD.size()==0) {
                    actualizarRegiones();
                }
                else if (departamentoesBD.size()==0) {
                    actualizarDepartamentos();
                }
                else if (localidadesBD.size()==0) {
                    actualizarLocalidades();
                }

                if (regionesBD.size()>0 && departamentoesBD.size()>0 && localidadesBD.size()>0 && formulariosBD.size()>0) {
                    tiempo=60000;
                    Sesion.setHabilitaAlta(true);
                    actualizarFormularios();
                    actualizarRegiones();
                    actualizarDepartamentos();
                    actualizarLocalidades();
                }


                handler.postDelayed(this, tiempo);
            }
        };
        handler.postDelayed(runnable, 0);


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private class sincronizarAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

           return null;
        }
    }

    private void actualizarRegiones() {

        List<RegionDTO> regionesBD = regionViewModel.getRegions();
        getRegionesRest();


        if (regionesRest.getValue()!=null) {
            //Si existe actualizo y sino agrego
            for (RegionDTO r : regionesRest.getValue()) {
                boolean existe = false;
                for (RegionDTO rdto : regionesBD) {
                    if (r.getIdregion() == rdto.getIdregion()) {
                        existe = true;
                    }
                }
                if (existe) {
                    regionViewModel.update(r);

                } else {
                    regionViewModel.insert(r);

                }
            }

            //Si esta en la bd y no en el rest borro
            for (RegionDTO rdto : regionesBD) {
                boolean encontre = false;
                for (RegionDTO r : regionesRest.getValue()) {
                    if (r.getIdregion() == rdto.getIdregion()) {
                        encontre = true;
                    }
                }
                if (!encontre) {
                    regionViewModel.delete(rdto);

                }

            }
        }

    }

    private void getRegionesRest() {

        Call<List<RegionDTO>> call = regionApi.getRegiones();
        call.enqueue(new Callback<List<RegionDTO>>() {
            @Override
            public void onResponse(Call<List<RegionDTO>> call, Response<List<RegionDTO>> response) {
                if(response.isSuccessful()) {
                    List<RegionDTO> misRegiones = response.body();
                    if(misRegiones!=null) {
                        regionesRest.setValue(misRegiones);
                    }

                }
            }
            @Override
            public void onFailure(Call<List<RegionDTO>> call, Throwable t) {

            }
        });
    }

    private void actualizarDepartamentos() {

        List<DepartamentoDTO> departamentoesBD = departamentoViewModel.getDepartamentos();
        getDepartamentoesRest();


        if (departamentoesRest.getValue()!=null) {
            //Si existe actualizo y sino agrego
            for (DepartamentoDTO r : departamentoesRest.getValue()) {
                boolean existe = false;
                for (DepartamentoDTO rdto : departamentoesBD) {
                    if (r.getIddepartamento() == rdto.getIddepartamento()) {
                        existe = true;
                    }
                }
                if (existe) {
                    departamentoViewModel.update(r);

                } else {
                    departamentoViewModel.insert(r);

                }
            }


            //Si esta en la bd y no en el rest borro
            for (DepartamentoDTO rdto : departamentoesBD) {
                boolean encontre = false;
                for (DepartamentoDTO r : departamentoesRest.getValue()) {
                    if (r.getIddepartamento() == rdto.getIddepartamento()) {
                        encontre = true;
                    }
                }
                if (!encontre) {
                    departamentoViewModel.delete(rdto);

                }

            }
        }

    }

    private void getDepartamentoesRest() {

        Call<List<DepartamentoDTO>> call = departamentoApi.getDepartamentosTodos();
        call.enqueue(new Callback<List<DepartamentoDTO>>() {
            @Override
            public void onResponse(Call<List<DepartamentoDTO>> call, Response<List<DepartamentoDTO>> response) {
                if(response.isSuccessful()) {
                    List<DepartamentoDTO> misDepartamentoes = response.body();
                    if(misDepartamentoes!=null) {
                        departamentoesRest.setValue(misDepartamentoes);
                    }

                }
            }
            @Override
            public void onFailure(Call<List<DepartamentoDTO>> call, Throwable t) {

            }
        });
    }

    private void actualizarLocalidades() {

        List<LocalidadDTO> localidadesBD = localidadViewModel.getLocalidades();
        getLocalidadesRest();


        if (localidadesRest.getValue()!=null) {
            //Si existe actualizo y sino agrego
            for (LocalidadDTO r : localidadesRest.getValue()) {
                boolean existe = false;
                for (LocalidadDTO rdto : localidadesBD) {
                    if (r.getIdlocalidad() == rdto.getIdlocalidad()) {
                        existe = true;
                    }
                }
                if (existe) {
                    localidadViewModel.update(r);

                } else {
                    localidadViewModel.insert(r);

                }
            }


            //Si esta en la bd y no en el rest borro
            for (LocalidadDTO rdto : localidadesBD) {
                boolean encontre = false;
                for (LocalidadDTO r : localidadesRest.getValue()) {
                    if (r.getIdlocalidad() == rdto.getIdlocalidad()) {
                        encontre = true;
                    }
                }
                if (!encontre) {
                    localidadViewModel.delete(rdto);

                }

            }
        }

    }

    private void getLocalidadesRest() {

        Call<List<LocalidadDTO>> call = localidadApi.getLocalidadesTodos();
        call.enqueue(new Callback<List<LocalidadDTO>>() {
            @Override
            public void onResponse(Call<List<LocalidadDTO>> call, Response<List<LocalidadDTO>> response) {
                if(response.isSuccessful()) {
                    List<LocalidadDTO> misLocalidades = response.body();
                    if(misLocalidades!=null) {
                        localidadesRest.setValue(misLocalidades);
                    }

                }
            }
            @Override
            public void onFailure(Call<List<LocalidadDTO>> call, Throwable t) {

            }
        });
    }

    private void actualizarFormularios() {
        formularioViewModel = new FormularioViewModel(getApplication());
        List<FormularioDTO> formulariosBD = formularioViewModel.getFormularios();
        getFormulariosRest();


        if (formulariosRest.getValue()!=null) {
            //Si existe actualizo y sino agrego
            for (FormularioDTO r : formulariosRest.getValue()) {
                boolean existe = false;
                for (FormularioDTO rdto : formulariosBD) {
                    if (r.getIdformulario() == rdto.getIdformulario()) {
                        existe = true;
                    }
                }
                if (existe) {
                    formularioViewModel.update(r);

                } else {
                    formularioViewModel.insert(r);

                }
            }


            //Si esta en la bd y no en el rest borro
            for (FormularioDTO rdto : formulariosBD) {
                boolean encontre = false;
                for (FormularioDTO r : formulariosRest.getValue()) {
                    if (r.getIdformulario() == rdto.getIdformulario()) {
                        encontre = true;
                    }
                }
                if (!encontre) {
                    formularioViewModel.delete(rdto);

                }

            }
        }

    }

    private void getFormulariosRest() {

        Call<List<FormularioDTO>> call = formularioApi.getFormularios();
        call.enqueue(new Callback<List<FormularioDTO>>() {
            @Override
            public void onResponse(Call<List<FormularioDTO>> call, Response<List<FormularioDTO>> response) {
                if(response.isSuccessful()) {
                    List<FormularioDTO> misFormularios = response.body();
                    if(misFormularios!=null) {
                        formulariosRest.setValue(misFormularios);
                    }

                }
            }
            @Override
            public void onFailure(Call<List<FormularioDTO>> call, Throwable t) {

            }
        });
    }

}
