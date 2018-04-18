package com.example.suelliton.mcpd_android;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.suelliton.mcpd_android.adapters.CulturaAdapter;
import com.example.suelliton.mcpd_android.adapters.PermissionUtils;
import com.example.suelliton.mcpd_android.adapters.PragaAdapter;
import com.example.suelliton.mcpd_android.model.Cultura;
import com.example.suelliton.mcpd_android.model.Galeria;
import com.example.suelliton.mcpd_android.model.Praga;
import com.example.suelliton.mcpd_android.model.Registro;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.suelliton.mcpd_android.ConsultaRegistrosActivity.REGISTRO_PARA_EDITAR;
import static com.example.suelliton.mcpd_android.ConsultaRegistrosActivity.RESULT_EDIT;
import static com.example.suelliton.mcpd_android.ConsultaRegistrosActivity.RESULT_EXIT;


public class RegistroActivity extends AppCompatActivity {
    //Captura de Tela
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageButton btTakeaaPhoto;
    private ImageView mImgExemplo;

    private FirebaseDatabase database ;
    private DatabaseReference culturaReference;
    private DatabaseReference pragaReference;
    private ChildEventListener childEventRegistro;
    private DatabaseReference registroReference;

    private ValueEventListener childValueCultura;
    private ValueEventListener childValuePraga;

    public static List<Cultura> listaCulturas;
    public static List<Praga> listaPragas;

    EditText obs ;
    CulturaAdapter adaptador_cultura;
    PragaAdapter adaptador_praga;
    Registro registroAtual;
    private EditText tratamento;
    private RadioGroup radioGroup_escala,radioGroup_tratamento;
    private final static int REQUEST_CONSULT = 1;
    private Button btnConsulta, btn_Gravar, btn_Sair, btn_Apontamentos, btn_Cancelar,btn_Excluir;
    private Spinner spinner_cultura, spinner_praga;
    private int ID_EDIT;
    private Long ID_PROPRIEDADE;
    private int contGps=0;

    int culturaClicada = 2;
    int pragaClicada = 0;
    GPSTracker  gps = new GPSTracker(RegistroActivity.this);
    final String[] permissoes = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        recebePropriedade();
        REGISTRO_PARA_EDITAR = null;
        database = FirebaseDatabase.getInstance();
        culturaReference = database.getReference().child("Cultura");
        pragaReference = database.getReference().child("Praga");
        registroReference = database.getReference().child("Registro");
        registroAtual = new Registro();

        bindViews();
        setListeners();
        controlaBotoes(false);

        //Captura de Imagem
        btTakeaaPhoto = (ImageButton) findViewById(R.id.img_amostra);


        btTakeaaPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }


    public void recebePropriedade() {
        Bundle bundle = getIntent().getExtras();
        ID_PROPRIEDADE = bundle.getLong("idPropriedade");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void bindViews() {
        btnConsulta = (Button) findViewById(R.id.btn_consultar);
        btn_Gravar = (Button) findViewById(R.id.btn_gravar);
        btn_Apontamentos = (Button) findViewById(R.id.btn_apontamentos);
        btn_Sair = (Button) findViewById(R.id.btn_sair);
        btn_Cancelar = (Button) findViewById(R.id.btn_cancelar);
        btn_Excluir = (Button) findViewById(R.id.btn_excluir);
        spinner_cultura = (Spinner) findViewById(R.id.spinner_cultura);
        spinner_praga = (Spinner) findViewById(R.id.spinner_praga_dano);
        radioGroup_escala = (RadioGroup) findViewById(R.id.radioGroup_escala);
        radioGroup_tratamento= (RadioGroup) findViewById(R.id.radioGroup_tratamento);
        tratamento = (EditText) findViewById(R.id.tratamento);
        obs = (EditText) findViewById(R.id.observacoes);

        //radiobuttons escala
        final Galeria galeria = new Galeria("", this);
        mImgExemplo= (ImageView) findViewById(R.id.img_exemplo);

        RadioButton radSev_01 = (RadioButton) findViewById(R.id.sev_01);
        radSev_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImgExemplo.setImageBitmap(galeria.getImage(1,
                        listaCulturas.get(culturaClicada).getNome(),
                        listaPragas.get(culturaClicada).getNome()));
            }
        });
        RadioButton radSev_02 = (RadioButton) findViewById(R.id.sev_02);
        radSev_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImgExemplo.setImageBitmap(galeria.getImage(2,
                        listaCulturas.get(culturaClicada).getNome(),
                        listaPragas.get(culturaClicada).getNome()));
            }
        });
        RadioButton radSev_03 = (RadioButton) findViewById(R.id.sev_03);
        radSev_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImgExemplo.setImageBitmap(galeria.getImage(3,
                        listaCulturas.get(culturaClicada).getNome(),
                        listaPragas.get(culturaClicada).getNome()));
            }
        });
        RadioButton radSev_04 = (RadioButton) findViewById(R.id.sev_04);
        radSev_04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImgExemplo.setImageBitmap(galeria.getImage(4,
                        listaCulturas.get(culturaClicada).getNome(),
                        listaPragas.get(culturaClicada).getNome()));
            }
        });
        RadioButton radSev_05 = (RadioButton) findViewById(R.id.sev_05);
        radSev_05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImgExemplo.setImageBitmap(galeria.getImage(5,
                        listaCulturas.get(culturaClicada).getNome(),
                        listaPragas.get(culturaClicada).getNome()));
            }
        });
    }

    public void setListeners() {

         listaCulturas= new ArrayList<>();
         adaptador_cultura = new CulturaAdapter(this, listaCulturas);
        spinner_cultura.setAdapter(adaptador_cultura);

        childValueCultura = culturaReference.child("/Cultura").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    listaCulturas.removeAll(listaCulturas);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Cultura cultura = snapshot.getValue(Cultura.class);//pega o objeto do firebase
                        listaCulturas.add(cultura);//adiciona na lista que vai para o adapter
                        Log.i("c", cultura.getNome() + "");
                    }
                    adaptador_cultura.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        culturaReference.addValueEventListener(childValueCultura);




        listaPragas= new ArrayList<>();
        adaptador_praga = new PragaAdapter(this, listaPragas);
        spinner_praga.setAdapter(adaptador_praga);
        spinner_cultura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
               // long idCultura = listaCulturas.get(i).getId();
                registroAtual.setCulturaId( i);
                culturaClicada = i;
                Log.i("clicou","na cultura "+ i);

                childValuePraga = pragaReference.child("Praga").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        listaPragas.removeAll(listaPragas);
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                            Praga praga = snapshot.getValue(Praga.class);//pega o objeto do firebase
                            if(praga.getIdCultura() == culturaClicada){
                                listaPragas.add(praga);//adiciona na lista que vai para o adapter
                                Log.i("c",praga.getNome()+"");
                            }
                        }
                        adaptador_praga.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                pragaReference.addValueEventListener(childValuePraga);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }) ;

   //trato clique em spinner praga
        spinner_praga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //long idPraga = listaPragas.get(i).getId();
                registroAtual.setPragaId(i);
                pragaClicada = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //radiobuttons tratamento
        RadioButton radio_tratamento_sim = (RadioButton) findViewById(R.id.registro_sim);
        radio_tratamento_sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tratamento.setVisibility(View.VISIBLE);
                registroAtual.setTratamento(true);
            }
        });
        RadioButton radio_tratamento_nao = (RadioButton) findViewById(R.id.registro_nao);
        radio_tratamento_nao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tratamento.setVisibility(View.GONE);
                registroAtual.setTratamento(false);
            }
        });
        //define estado inicial edit escondido e botao nao setado
        registroAtual.setTratamento(false);
        tratamento.setVisibility(View.GONE);
        radio_tratamento_nao.toggle();

        RadioButton radioButton3 = (RadioButton) findViewById(R.id.sev_03);
        radioButton3.toggle();


        btnConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(RegistroActivity.this, ConsultaRegistrosActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_CONSULT);
            }
        });

        btn_Gravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarRegistro();
            }
        });
        btn_Apontamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               recreate();
            }
        });
        btn_Sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        btn_Excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registroReference.child(registroAtual.getKey()).removeValue();
                recreate();
            }
        });


    }

    public void controlaBotoes(boolean estado) {
        Button btn_excluir = (Button) findViewById(R.id.btn_excluir);
        if (!estado) {
            btn_excluir.setVisibility(View.GONE);
        } else {
            btn_excluir.setVisibility(View.VISIBLE);
        }
    }

    public void setaRegistro(){

        registroAtual = REGISTRO_PARA_EDITAR;
        tratamento.setText(registroAtual.getTipo());
        obs.setText(registroAtual.getObs());
        RadioButton radio;
        switch (registroAtual.getEscala()){

            case 1:
                radio = (RadioButton) findViewById(R.id.sev_01);
                radio.toggle();
                break;
            case 2:
                radio = (RadioButton) findViewById(R.id.sev_02);
                radio.toggle();
                break;
            case 3:
                radio = (RadioButton) findViewById(R.id.sev_03);
                radio.toggle();
                break;
            case 4:
                radio = (RadioButton) findViewById(R.id.sev_04);
                radio.toggle();
                break;
            case 5:
                radio = (RadioButton) findViewById(R.id.sev_05);
                radio.toggle();
                break;
            default:
                Toast.makeText(this, "Escala inexistente", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            btTakeaaPhoto.setScaleType(ImageView.ScaleType.FIT_CENTER);
            btTakeaaPhoto.setImageBitmap(imageBitmap);
        }
        if (requestCode == REQUEST_CONSULT) {
            if (resultCode == RESULT_EDIT) {
                Bundle bundle = data.getExtras();
                int id_edit = bundle.getInt("id_edit",0);
                Log.i("id_edit",id_edit+"");
                setaRegistro();
                controlaBotoes(true);
            } else if (resultCode == RESULT_EXIT) {
                finish();
            }else if(resultCode == RESULT_CANCELED){

            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        PermissionUtils.validate(this, 0, permissoes);
        //setupGps();
    }


   public String getDataAtual(){
       SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss\ndd-MM-yy");
       // OU
       SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm:ss");

       Date data = new Date();

       Calendar cal = Calendar.getInstance();
       cal.setTime(data);
       Date data_atual = cal.getTime();

       String data_completa = dateFormat.format(data_atual);

       String hora_atual = dateFormat_hora.format(data_atual);

       Log.i("data_completa", data_completa);
       Log.i("data_atual", data_atual.toString());
       Log.i("hora_atual", hora_atual); // Esse é o que você quer

       return data_completa;
   }
    public double getLatitude(){
        GPSTracker gps = new GPSTracker(this);
        return gps.getLatitude();
    }
    public double getLongitude(){
        GPSTracker gps = new GPSTracker(this);
        Log.i("latitude",gps.getLatitude()+"");
        return gps.getLongitude();
    }


   public void  salvarRegistro(){
       switch (radioGroup_escala.getCheckedRadioButtonId()){
           case R.id.sev_01:
               registroAtual.setEscala(1);
               Log.i("clicou","no um");
               break;
           case R.id.sev_02:
               registroAtual.setEscala(2);
               break;
           case R.id.sev_03:
               registroAtual.setEscala(3);
               break;
           case R.id.sev_04:
               registroAtual.setEscala(4);
               break;
           case R.id.sev_05:
               registroAtual.setEscala(5);
               break;
           default:
       }

       registroAtual.setObs(obs.getText().toString());

       registroAtual.setTipo(tratamento.getText().toString());
       registroAtual.setDataRegistro(getDataAtual());
       registroAtual.setDataTratamento(getDataAtual());
       registroAtual.setLatitude(getLatitude());
       registroAtual.setLongitude(getLongitude());
       registroAtual.setUsuarioId(1);
       if(REGISTRO_PARA_EDITAR == null){
           registroReference.push().setValue(registroAtual);
           Snackbar.make((View)obs.getParent(), "Dados salvos com sucesso", Snackbar.LENGTH_LONG).show();

           obs.setText("");
           tratamento.setText("");
       }else{
        registroReference.child(registroAtual.getKey()).setValue(registroAtual);

           Snackbar.make((View)obs.getParent(), "Dados atualizados", Snackbar.LENGTH_LONG).show();

           REGISTRO_PARA_EDITAR = null;
       }
       obs.setText("");
       tratamento.setText("");
   }


   //Métodos captura de imagem
   //Captura de imagem
   private void dispatchTakePictureIntent() {
       Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
           startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
       }
   }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int result : grantResults){
            if(result == PackageManager.PERMISSION_DENIED){
                alertAndFinish();
                return;
            }
        }
    }

    public void setupGps(){
        gps.getLocation();
        if(!gps.isGPSEnabled){
            if(contGps == 2){
                finish();
            }else {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                Toast.makeText(RegistroActivity.this, "Ative o GPS do dispositivo", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                contGps ++;
            }
        }


    }



    private void alertAndFinish(){
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name).setMessage("Para utilizar este aplicativo, você precisa aceitar as permissões.");
            // Add the buttons
            builder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.setPositiveButton("Permitir", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }


   /* public String[] converteCultura(List<Cultura> lista){
        String[] listaConvertida = new String[lista.size()];
        int i=0;
        for (Cultura c: lista){
            listaConvertida[i] = c.getNome();
            i++;
        }

       return listaConvertida;
    }
    public String[] convertePraga(List<Praga> lista){
        String[] listaConvertida = new String[lista.size()];
        int i=0;
        for (Praga p: lista){
            listaConvertida[i] = p.getNome();
            i++;
        }

        return listaConvertida;
    }*/
}
