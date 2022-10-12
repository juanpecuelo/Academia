package clases;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.juanpecuelo.academia.LoginActivity;
import com.juanpecuelo.academia.MenuPrincipalActivity;
import com.juanpecuelo.academia.R;
import com.juanpecuelo.academia.SintomasActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InformationManager {

    private static final String URL_LOGIN = Constantes.IP + "/login/login.php";
    private static final String URL_ID = Constantes.IP + "/login/getId.php";
    private Activity activity;
    private Utiles utiles;
    private SessionManager sm;


    public InformationManager(Activity activity, Utiles utiles){
        this.activity = activity;
        this.utiles = utiles;
    }
    public InformationManager(Activity activity, Utiles utiles, SessionManager sm){
        this.activity = activity;
        this.utiles = utiles;
        this.sm = sm;
    }

    public void enviarLogin(String email, String contrasena, boolean recuerdame){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int newAccount = 0;
                String[] respuesta = response.split("@");
                // System.out.println(response + "<--- respuesta login");
                System.out.println(email);
                if (respuesta[0].equals("success")) {
                    if (recuerdame) {
                        sm.setLogin(true);
                        sm.guardarRecuerdame(email, contrasena, recuerdame);
                    } else {
                        sm.borrarRecuerdame();
                    }
                    try {
                        JSONArray array = new JSONArray(respuesta[1]);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            newAccount = object.getInt("is_new_account");
                            // System.out.println(newAccount + " arriba");
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    establecerId(email);
                    Intent intent;
                    if (newAccount == 1) {
                        intent = new Intent(activity, SintomasActivity.class);
                    } else {
                        intent = new Intent(activity, MenuPrincipalActivity.class);
                    }
                    activity.startActivity(intent);
                    activity.finish();
                } else if (respuesta[0].equals("failure")) {
                    utiles.toast(activity.getResources().getString(R.string.email_contra_incorrectos));
                } else {
                    utiles.toast(activity.getResources().getString(R.string.campos_vacios));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                utiles.toast(error.toString().trim());
                System.out.println(error.toString().trim());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("email", email);
                data.put("password", contrasena);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void establecerId(String email) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //                      System.out.println(response + "<--- respuesta id");
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                Log.i("tagconvertstr", "[" + response + "]");
                                JSONObject object = array.getJSONObject(i);
                                int id = object.getInt("id");
                                System.out.println(id);
                                sm.setId(id);
//                                System.out.println(sp.getInt("user_id", -1));
                            }
                        } catch (Exception e) {
                            System.out.println(e + " excepción");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                utiles.toast(error.toString().trim());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }

}
