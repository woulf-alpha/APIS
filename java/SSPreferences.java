
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Toni Hernandez on 30/10/2016.
 *
 */

public class SSPreferences {

    private String Ruta;
    private ArrayList<String> List_files;
    private Context ctxt;
    private String f_out;
    SharedPreferences.Editor editor;
    /* CONSTRUCTORES
    */
    public SSPreferences(Context ctxt) {
        Ruta = "/data/data/" + ctxt.getPackageName() + "/shared_prefs";
        this.ctxt = ctxt;
        if(findFiles()){
            f_out = getList_files().get(0);
        } else {
            f_out = "data";
        }
        this.editor =  ctxt.getSharedPreferences(f_out, Context.MODE_PRIVATE).edit();
    }
    public SSPreferences(Context ctxt, String file){
        Ruta = "/data/data/" + ctxt.getPackageName() + "/shared_prefs";
        this.ctxt = ctxt;
        findFiles();
        this.f_out = file;
        this.editor = ctxt.getSharedPreferences(f_out, Context.MODE_PRIVATE).edit();
    }

    // LECTURA DE DATOS
    public Map<String, ?> read(){
        return ctxt.getSharedPreferences(f_out, Context.MODE_PRIVATE).getAll();
    }

    public Map<String, ?> read(String f_out){
        return ctxt.getSharedPreferences(f_out, Context.MODE_PRIVATE).getAll();
    }

    // ESCRITURA DE DATOS
    public void write(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }

    public  void write(String f_out, String key, String value){
        editor =  ctxt.getSharedPreferences(f_out, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void writeObject(Object i, String key){
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(i);
            so.flush();
            editor.putString(key, Base64.encodeToString(bo.toByteArray(), Base64.DEFAULT));
            editor.commit();
        } catch (Exception e) {
        }
    }

    public void writeObject(Object i, String f_out, String key){
        editor = ctxt.getSharedPreferences(f_out, Context.MODE_PRIVATE).edit();
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(i);
            so.flush();
            editor.putString(key, Base64.encodeToString(bo.toByteArray(), Base64.DEFAULT));
            editor.commit();
        } catch (Exception e) {
        }
    }

    // ELIMINAR DATOS
    public  void remove(String key){
        editor.remove(key);
        editor.commit();
    }

    public void remove(String f_out, String key){
        SharedPreferences.Editor editor = ctxt.getSharedPreferences(f_out, Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.commit();
    }

    public void removeAll(){
        editor.clear();
        editor.commit();
    }

    public void copy(String f_in, String f_out) {

        InputStream in = null;
        OutputStream out = null;
        try {
            in = ctxt.getAssets().open(f_in);
            out = new FileOutputStream(Ruta +"/"+f_out+".xml");
            copyFile(in, out);
            in.close();
            out.close();
        } catch (IOException e) {
            Log.e("kk", "Fallo en la copia del archivo del asset", e);
        }
        findFiles();
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    // FUNCION QUE NOS DIRA LOS ARCHIVOS QUE EXISTEN EN EL DIRECTORIO
    private boolean findFiles(){

        this.List_files = new ArrayList<>();

        File Directorio = new File(Ruta);
        if(Directorio.listFiles() != null){
            File[] files = Directorio.listFiles();
            for (int i = 0; i < files.length; i++)
            {
                this.List_files.add(files[i].getName());
            }
            return true;
        }
        return false;
    }

    public ArrayList<String> getList_files() {
        return List_files;
    }

    public Object StringtoObject(String s){
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(Base64.decode(s, Base64.DEFAULT));
            ObjectInputStream ois = new ObjectInputStream(bis);
            return  ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
