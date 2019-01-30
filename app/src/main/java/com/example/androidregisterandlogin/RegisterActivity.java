package com.example.androidregisterandlogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {
    private EditText et_name, et_email, et_password, et_c_password;
    private String name,email,password,c_password;
    private Button btn_regist;
    private ProgressBar loading;
    private static String URL_REGIST = "http://192.168.10.102:8888/mentordai/register.php";
    //private static String URL_REGIST = "https://avashadhikari.com.np/mentordai/register.php";
    //private static String URL_REGIST = "http://ceetlehero.com/mentordai/register.php";

    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loading = findViewById(R.id.loading);
        et_name = findViewById(R.id.name);
        et_email = findViewById(R.id.email);
        et_password = findViewById(R.id.password);
        et_c_password = findViewById(R.id.c_password);
        btn_regist = findViewById(R.id.btn_regist);

        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_validation()){
                     Regist();
                }else{
                    Toast.makeText(RegisterActivity.this,"Validation failed ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean check_validation(){
        boolean validate = true;
        email = this.et_email.getText().toString().trim();
        name = this.et_name.getText().toString().trim();
        if(!validateEmail(email)){
            validate = false;
            et_email.setError("Invalid email");
        }
        if(!validateName(name)){
            validate = false;
            et_name.setError("Invalid name");
        }
        return validate;
    }
    private boolean validateName(String name){
        boolean validate = true;
        if(name.isEmpty() || name.length() > 32){
            validate = false;
        }
        return validate;
    }

    private boolean validateEmail(String vemail){
        email = vemail;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }


    private void Regist(){
        loading.setVisibility(View.GONE);
        btn_regist.setVisibility(View.GONE);

        final String name = this.name;
        final String email = this.email;
        final String password = this.et_password.getText().toString().trim();
        final String c_password = this.et_c_password.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            Log.d("TAG",response);
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                Toast.makeText(RegisterActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
                                btn_regist.setVisibility(View.VISIBLE);
                            }else if(success.equals("2")){
                                Toast.makeText(RegisterActivity.this, "Please verify your password", Toast.LENGTH_SHORT).show();
                                btn_regist.setVisibility(View.VISIBLE);
                            }
//
//                            else if(success.equals("3")){
//                                Toast.makeText(RegisterActivity.this, "unique already exist", Toast.LENGTH_SHORT).show();
//                                btn_regist.setVisibility(View.VISIBLE);
//                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btn_regist.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Register Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btn_regist.setVisibility(View.VISIBLE);
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("c_password", c_password);
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
}
