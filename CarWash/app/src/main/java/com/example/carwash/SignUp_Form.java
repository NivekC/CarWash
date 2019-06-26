package com.example.carwash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.carwash.entities.AccessToken;
import com.example.carwash.entities.ApiError;
import com.example.carwash.network.ApiService;
import com.example.carwash.network.RetrofitBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class SignUp_Form extends AppCompatActivity {

    private static final String TAG = "SignUp_Form";

    ApiService service;
    Call<AccessToken> call;

    TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__form);

        service = RetrofitBuilder.createService(ApiService.class);
        tokenManager = TokenManager.getInstance(getSharedPreferences("preferences",  MODE_PRIVATE));
    }

    public void register(View view){
        EditText uname = (EditText)findViewById(R.id.username);
        EditText pass = (EditText)findViewById(R.id.password);
        EditText cPass = (EditText)findViewById(R.id.cPassword);
        EditText mail = (EditText)findViewById(R.id.email);
        EditText phone = (EditText)findViewById(R.id.phone);

        String name = uname.getText().toString();
        String email = mail.getText().toString();
        String password = pass.getText().toString();
        String phoneNo = phone.getText().toString();



            call = service.register(name, email, password, phoneNo);
            call.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {

                    Log.w(TAG, "onResponse: "+response);
                    if(response.isSuccessful()){
                        Log.w(TAG, "onResponse: "+response.body());
                        tokenManager.saveToken(response.body());
                        startActivity(new Intent(SignUp_Form.this, Login_form.class));
                        finish();

                    } else {

                    }
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Log.w(TAG, "onFailure: "+t.getMessage());
                }
            });
            Toast toast = Toast.makeText(getApplicationContext(),name, Toast.LENGTH_LONG);
            toast.show();
    }

    /*private void handleErrors(ResponseBody response){
        ApiError apiError = Utils.convertErrors(response);

        for(Map.Entry<String, List<String>> error : apiError.getErrors().entrySet()){
            if(error.getKey().equals("name")){
                username.se
            }
        }
    }*/

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(call != null) {
            call.cancel();
            call = null;

        }
    }
}
