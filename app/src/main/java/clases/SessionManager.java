package clases;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String SESSION_MANAGER="sesion";
    private static final String KEY_LOGIN="key_login";
    private static final String KEY_ID="key_id";
    private static final String KEY_EMAIL = "key_email";
    private static final String KEY_CONTRASENA = "key_contrasena";
    private static final String KEY_CHECKBOX = "key_recuerdame";
    private static final String KEY_ULTIMA_CATEGORIA = "key_ultima_categoria";


    private final SharedPreferences sp;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context){
        sp = context.getSharedPreferences(SESSION_MANAGER,0);
        editor = sp.edit();
        editor.apply();
    }
    public void guardarRecuerdame(String email, String contrasena, boolean isChecked) {
        setEmail(email);
        setContrasena(contrasena);
        setCheckedRecuerdame(isChecked);
    }
    public void borrarRecuerdame() {
        setEmail("");
        setContrasena("");
        setCheckedRecuerdame(false);
    }


    public void setLogin(boolean login){
        editor.putBoolean(KEY_LOGIN, login);
        editor.commit();
    }
    public boolean getLogin(){
        return sp.getBoolean(KEY_LOGIN,false);
    }
    public void setId(int id){
        editor.putInt(KEY_ID, id);
        editor.commit();
    }
    public int getId(){
        return sp.getInt(KEY_ID, -1);
    }
    public void setEmail(String email){
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }
    public String getEmail(){
        return sp.getString(KEY_EMAIL,"");
    }
    public void setContrasena(String contrasena){
        editor.putString(KEY_CONTRASENA, contrasena);
        editor.commit();
    }
    public String getContrasena(){
        return sp.getString(KEY_CONTRASENA, "");
    }
    public void setCheckedRecuerdame(boolean checkBox){
        editor.putBoolean(KEY_CHECKBOX, checkBox);
        editor.commit();
    }
    public boolean getCheckedRecuerdame() {
        return sp.getBoolean(KEY_CHECKBOX,false);
    }
    public int getUltimaCategoria(){
        return sp.getInt(KEY_ULTIMA_CATEGORIA, -1);
    }
    public void setUltimaCategoria(int ultimaCategoria){
        editor.putInt(KEY_ULTIMA_CATEGORIA, ultimaCategoria);
        editor.commit();
    }
}
