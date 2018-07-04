package com.example.tads.mcpd_android.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tads.mcpd_android.R;
import com.example.tads.mcpd_android.adapters.CulturaAdapter;
import com.example.tads.mcpd_android.adapters.PermissionUtils;
import com.example.tads.mcpd_android.adapters.PragaAdapter;
import com.example.tads.mcpd_android.model.Cultura;
import com.example.tads.mcpd_android.model.Galeria;
import com.example.tads.mcpd_android.model.Praga;
import com.example.tads.mcpd_android.model.Registro;
import com.example.tads.mcpd_android.utils.MCPDUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.tads.mcpd_android.activity.ConsultaRegistrosActivity.REGISTRO_PARA_EDITAR;
import static com.example.tads.mcpd_android.activity.ConsultaRegistrosActivity.RESULT_EDIT;
import static com.example.tads.mcpd_android.activity.ConsultaRegistrosActivity.RESULT_EXIT;


public class RegistroActivity extends AppCompatActivity {
    //Captura de Tela
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imgTakePhoto;
    private Bitmap imageBitmap;

    private FirebaseDatabase database;
    private DatabaseReference culturaReference;
    private DatabaseReference pragaReference;
    private ChildEventListener childEventRegistro;
    private DatabaseReference registroReference;

    private StorageReference storageReference;
    private String Storage_Path = "ImgsRegistros";

    // Image request code for onActivityResult().
    int Image_Request_Code = 7;

    private ProgressDialog progressDialog;


    private ValueEventListener childValueCultura;
    private ValueEventListener childValuePraga;

    public static List<Cultura> listaCulturas;
    public static List<Praga> listaPragas;

    private EditText observacao;
    private CulturaAdapter adaptador_cultura;
    private PragaAdapter adaptador_praga;
    private Registro registroAtual;
    private EditText tratamento;
    private RadioGroup radioGroup_escala, radioGroup_tratamento;
    private RadioButton simRB, naoRB, escala1;
    private final static int REQUEST_CONSULT = 1;
    private Button btnConsulta, btn_Gravar, btn_Sair, btn_Apontamentos, btn_Cancelar, btn_Excluir;
    private Spinner spinner_cultura, spinner_praga;
    private int ID_EDIT;
    private Long ID_PROPRIEDADE;
    private int contGps = 0;

    int culturaClicada = 2;
    int pragaClicada = 0;
    GPSTracker gps = new GPSTracker(RegistroActivity.this);
    final String[] permissoes = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        database = FirebaseDatabase.getInstance();
        culturaReference = database.getReference().child("Cultura");
        pragaReference = database.getReference().child("Praga");
        registroReference = database.getReference().child("Registro");
        registroAtual = new Registro();

        bindViews();
        setListeners();
        showTratamentoField();
        hideTratamentoField();
        takePhoto();

    }

    private void bindViews() {

        imgTakePhoto = (ImageView) findViewById(R.id.img_amostra);

        spinner_cultura = (Spinner) findViewById(R.id.spinner_cultura);
        spinner_praga = (Spinner) findViewById(R.id.spinner_praga_dano);
        btn_Gravar = (Button) findViewById(R.id.btn_gravar);
        radioGroup_escala = (RadioGroup) findViewById(R.id.radioGroup_escala);


        radioGroup_tratamento = (RadioGroup) findViewById(R.id.radioGroup_tratamento);
        simRB = (RadioButton) findViewById(R.id.registro_sim);
        simRB.setChecked(true);

        naoRB = (RadioButton) findViewById(R.id.registro_nao);

        escala1 = (RadioButton) findViewById(R.id.sev_01);
        escala1.setChecked(true);

        tratamento = (EditText) findViewById(R.id.descricao_tratamento);
        tratamento.setVisibility(View.VISIBLE);
        tratamento.setText(null);

        observacao = (EditText) findViewById(R.id.observacoes);
        observacao.setText(null);
    }

    private void showTratamentoField() {
        simRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tratamento.setVisibility(View.VISIBLE);
            }
        });
    }

    private void hideTratamentoField() {
        naoRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tratamento.setVisibility(View.GONE);
            }
        });
    }

    private void takePhoto() {
        imgTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
    }

    private void setListeners() {
        defineSpinners();
        saveRegistroBtnClick();
    }

    public void defineSpinners() {
        listaCulturas = new ArrayList<>();
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

        listaPragas = new ArrayList<>();
        adaptador_praga = new PragaAdapter(this, listaPragas);
        spinner_praga.setAdapter(adaptador_praga);
        spinner_cultura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
                // long idCultura = listaCulturas.get(i).getId();
                registroAtual.setCulturaId(i);
                culturaClicada = i;
                Log.i("clicou", "na cultura " + i);

                childValuePraga = pragaReference.child("Praga").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        listaPragas.removeAll(listaPragas);
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Praga praga = snapshot.getValue(Praga.class);//pega o objeto do firebase
                            if (praga.getIdCultura() == culturaClicada) {
                                listaPragas.add(praga);//adiciona na lista que vai para o adapter
                                Log.i("c", praga.getNome() + "");
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
        });

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
    }

    public void saveRegistroBtnClick() {
        btn_Gravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarRegistro();
            }
        });
    }


    public void recebePropriedade() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            ID_PROPRIEDADE = bundle.getLong("idPropriedade");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();
        PermissionUtils.validate(this, 0, permissoes);
    }

    public boolean validaDados() {

        if (observacao.getText().toString().matches("") || imageBitmap == null ||
                (simRB.isChecked() && tratamento.getText().toString().matches("")))
            return false;
        else
            return true;

    }

    public void salvarRegistro() {
        boolean var = validaDados();
        if (validaDados()) {
            switch (radioGroup_escala.getCheckedRadioButtonId()) {
                case R.id.sev_01:
                    registroAtual.setEscala(1);
                    Log.i("clicou", "no um");
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

            registroAtual.setObs(observacao.getText().toString());

            registroAtual.setTipo(tratamento.getText().toString());
            registroAtual.setDataRegistro(MCPDUtils.getDataAtual());
            registroAtual.setDataTratamento(MCPDUtils.getDataAtual());
            registroAtual.setLatitude(MCPDUtils.getLatitude(this));
            registroAtual.setLongitude(MCPDUtils.getLongitude(this));

            if(imageBitmap != null)
                registroAtual.setImageURL(encodeBitmapAndSaveToFirebase(imageBitmap));

            registroAtual.setUsuarioId(1);
            if (REGISTRO_PARA_EDITAR == null) {
                registroReference.push().setValue(registroAtual);

                observacao.setText("");
                tratamento.setText("");

                Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                startActivity(intent);

                Snackbar.make((View) observacao.getParent(), "Dados salvos com sucesso", Snackbar.LENGTH_LONG).show();
                finish();

            } else {
                registroReference.child(registroAtual.getKey()).setValue(registroAtual);

                Snackbar.make((View) observacao.getParent(), "Dados atualizados", Snackbar.LENGTH_LONG).show();

                REGISTRO_PARA_EDITAR = null;
            }
            observacao.setText("");
            tratamento.setText("");
        } else {
            LinearLayout layoutError = findViewById(R.id.msg_error);
            layoutError.setVisibility(View.VISIBLE);
        }
    }

    //Métodos captura de imagem
    //Captura de imagem
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public String encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

        return imageEncoded;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imgTakePhoto.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imgTakePhoto.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                alertAndFinish();
                return;
            }
        }
    }

    private void alertAndFinish() {
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
