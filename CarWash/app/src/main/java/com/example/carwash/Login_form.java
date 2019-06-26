package com.example.carwash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carwash.entities.AccessToken;
import com.example.carwash.network.ApiService;
import com.example.carwash.network.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_form extends AppCompatActivity {


    private static final String TAG = "Login_form";
    
    ApiService service;
    Call<AccessToken> call;

    TokenManager tokenManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        service = RetrofitBuilder.createService(ApiService.class);
        tokenManager = TokenManager.getInstance(getSharedPreferences("preferences",  MODE_PRIVATE));
    }
    
    public void login(View view){
        EditText mail = (EditText)findViewById(R.id.email);
        EditText pass = (EditText)findViewById(R.id.password);



        String email = mail.getText().toString();
        String password = pass.getText().toString();



        call = service.login(email, password);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {

                Log.w(TAG, "onResponse: "+response);
                if(response.isSuccessful()){
                    Log.w(TAG, "onResponse: "+response.body());
                    tokenManager.saveToken(response.body());
                    startActivity(new Intent(Login_form.this, MainActivity.class));
                    finish();
                } else {

                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Log.w(TAG, "onFailure: "+t.getMessage());
            }
        });

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(call != null) {
            call.cancel();
            call = null;

        }
    }
}
