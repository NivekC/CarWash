package com.example.carwash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.carwash.entities.AccessToken;
import com.example.carwash.network.ApiService;
import com.example.carwash.network.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
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

        Button btnMember = (Button)findViewById(R.id.btn_member);
        btnMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp_Form.this,Login_form.class);
                startActivity(intent);
            }
        });

        if(tokenManager.getToken().getAccessToken()!= null){
            startActivity(new Intent(SignUp_Form.this, CarwashHome.class));
            finish();
        }
    }

    public void register(View view){
        EditText uname = (EditText)findViewById(R.id.username);
        EditText pass = (EditText)findViewById(R.id.password);
        EditText cPass = (EditText)findViewById(R.id.cPassword);
        EditText mail = (EditText)findViewById(R.id.email);
        EditText phoneNO = (EditText)findViewById(R.id.phone);

        String name = uname.getText().toString();
        String email = mail.getText().toString();
        String password = pass.getText().toString();
        String phone = phoneNO.getText().toString();



            call = service.register(name, email, password, phone);
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
