package com.example.tp3_lab3.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.tp3_lab3.model.Usuario;
import com.example.tp3_lab3.request.ApiClient;
import com.example.tp3_lab3.ui.register.RegistroActivity;

public class MainActivityViewModel extends AndroidViewModel {

    private Context context;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void registrar() {
        Intent intent = new Intent(context, RegistroActivity.class);
        intent.putExtra("logeado", false);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void login(String mail, String pass) {
        Usuario usuario = ApiClient.login(context, mail, pass);
        if (usuario == null) {
            Toast.makeText(context, "Datos incorrectos", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(context, RegistroActivity.class);
        intent.putExtra("logeado", true);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
