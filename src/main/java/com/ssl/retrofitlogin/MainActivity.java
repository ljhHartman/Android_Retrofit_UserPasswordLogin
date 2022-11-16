package com.ssl.retrofitlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    TextInputEditText username, password;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Layout
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.edUsername);
        password = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Button Action
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If input is empty
                if(TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(MainActivity.this,"Username / Password Required", Toast.LENGTH_LONG).show();
                }else{
                    //proceed to login
                    login();
                }

            }
        });
    }


    public void login(){
        // Fill in Model
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username.getText().toString());
        loginRequest.setPassword(password.getText().toString());

        // Send Credentials out the Endpoint
        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);

        // Response (Callback)
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                //if(response.isSuccessful()){
                if(response.code()==404){
                    Toast.makeText(MainActivity.this,"Login Successful", Toast.LENGTH_LONG).show();
                    LoginResponse loginResponse = response.body();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            //startActivity(new Intent(MainActivity.this,DashboardActivity.class).putExtra("data",loginResponse.getUsername()));

                            //startActivity(new Intent(getActivity().getApplicationContext(), DashboardActivity.class));
                        }
                    },700);

                }else{
                    Toast.makeText(MainActivity.this,"Login Failed", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }
}

/*
Application Flow

1.  Execute App
    MainActivity > onCreate
    initialize your activity, with a layout resource defining your UI

2.  User input credentials
    MainActivity > onCreate > onClick
    When user click on Login Button, we run the login() method

3.  Draft model
    MainActivity > login > Instance: LoginRequest
    Draft model with the user input credentials

4.  Build Request
    MainActivity > login > Call<LoginResponse>

5.  Send Request (Call)
    MainActivity > login > Call<LoginResponse> > INSTANCE: ApiClient.getUserService().userLogin(loginRequest)
    Send Request thought the Endpoint

6.  Get Response (Callback)
    MainActivity > login > onResponse
    If request response is 200 (OK), show popup message "Successful"

7.  Move on to new page
    MainActivity > login > onResponse > run
    After successful login show protected page details





 */