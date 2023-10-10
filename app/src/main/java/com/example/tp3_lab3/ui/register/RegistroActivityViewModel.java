package com.example.tp3_lab3.ui.register;

import static android.app.Activity.RESULT_OK;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tp3_lab3.model.Usuario;
import com.example.tp3_lab3.request.ApiClient;
import com.example.tp3_lab3.ui.login.MainActivity;

public class RegistroActivityViewModel extends AndroidViewModel {

    private Context context;

    MutableLiveData<Usuario> usuario;
    private MutableLiveData<Bitmap> foto;

    public RegistroActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Usuario> getUsuario() {
        if (usuario == null) {
            usuario = new MutableLiveData<>();
        }
        return usuario;
    }

    public LiveData<Bitmap> getFoto() {
        if (foto == null) {
            foto = new MutableLiveData<>();
        }
        return foto;
    }

    public void obtenerDatos(Intent intent) {
        if (intent == null) return;

        if (intent.getBooleanExtra("logeado", false)) {
            Usuario usuario = ApiClient.leer(context);
            this.usuario.setValue(usuario);
            obtenerFoto();
        }
    }
    public void obtenerFoto() {
        Bitmap bitmap = ApiClient.leerFoto(context);
        if (bitmap != null) foto.setValue(bitmap);
    }
    public void registrar(String dni, String nombre, String apellido, String mail, String pass) {
        Usuario usuario = new Usuario(dni, nombre, apellido, mail, pass);
        ApiClient.guardar(context, usuario);
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public void respuestaDeCamara(int requestCode, int resultCode, @Nullable Intent data, int REQUEST_IMAGE_CAPTURE) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            foto.setValue(ApiClient.guardarFoto(context, imageBitmap));
        }
    }

}
