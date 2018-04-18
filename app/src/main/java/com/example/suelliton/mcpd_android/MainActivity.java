package com.example.suelliton.mcpd_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andexert.library.RippleView;

public class MainActivity extends AppCompatActivity {
    RippleView mBt01,mBt02,mBt04,mBt05;
    Button btn_teste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        setListeners();
    }
    //Método de carregamento e configuração das Views
    public void bindViews(){
        mBt01 = (RippleView) findViewById(R.id.bt01);mBt01.setRippleDuration(200);
        mBt02 = (RippleView) findViewById(R.id.bt02);mBt02.setRippleDuration(200);
        mBt04 = (RippleView) findViewById(R.id.bt04);mBt04.setRippleDuration(200);
        mBt05 = (RippleView) findViewById(R.id.bt05);mBt05.setRippleDuration(200);
        btn_teste = (Button) findViewById(R.id.btn_teste);
    }
    //Método para inserir os setListeners das Views
    private void setListeners() {
        mBt01.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {   //Splash
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent = new Intent(MainActivity.this, Splash.class);
                startActivity(intent);
                finish();
            }
        });
        mBt02.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {   //Login
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mBt04.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {   //Coleta
            @Override
            public void onComplete(RippleView rippleView) {
                Toast.makeText(MainActivity.this, "Ainda não começou a ser implementada", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, ColetaActivity.class);
//                startActivity(intent);
            }
        });
        mBt05.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent = new Intent(MainActivity.this,GeoPositionActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_teste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
