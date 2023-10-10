package com.example.tp3_lab3.request;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.tp3_lab3.model.Usuario;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ApiClient {

    private static File archivo, foto;
    private static File conectar(File dir){
        if(archivo == null){
            archivo = new File(dir,"usuario.dat");
        }
        return archivo;
    }
    private static File conectarFoto(File dir) {
        if (foto == null) {
            foto = new File(dir, "foto_perfil.jpg");
        }
        return foto;
    }
    public static void guardar(Context context, Usuario usuario){
        File archivo = conectar(context.getFilesDir());
        try{
            FileOutputStream fos=new FileOutputStream(archivo);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(usuario);
            oos.close();
            fos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static Usuario leer(Context context){
        File archivo = conectar(context.getFilesDir());
        try{
            FileInputStream fis = new FileInputStream(archivo);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Usuario miUsuario = (Usuario)ois.readObject();
            ois.close();

            return miUsuario;
        } catch (IOException | ClassNotFoundException e){
            return null;
        }
    }

    public static Bitmap guardarFoto(Context context, Bitmap bitmap){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte [] b = baos.toByteArray();
        File archivo = conectarFoto(context.getFilesDir());
        try{
            FileOutputStream fos = new FileOutputStream(archivo);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(b);
            bos.flush();
            bos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return bitmap;
    }


    public static Bitmap leerFoto(Context context){
        File archivo = conectarFoto(context.getFilesDir());
        return BitmapFactory.decodeFile(archivo.getAbsolutePath());
    }
    public static Usuario login(Context context, String mail, String pass){
        File archivo = conectar(context.getFilesDir());
        try{
            FileInputStream fis = new FileInputStream(archivo);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Usuario miUsuario = (Usuario) ois.readObject();
            ois.close();

            if(miUsuario.getMail().equals(mail) && miUsuario.getPass().equals(pass)){
                return miUsuario;
            }
            return null;
        } catch (IOException | ClassNotFoundException e){
            return null;
        }
    }
}