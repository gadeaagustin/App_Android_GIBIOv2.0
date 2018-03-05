package com.gibio.bt_graph;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



import java.io.File;
import java.io.OutputStreamWriter;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.IntegerComparisonTerm;

/**
 * Created by aguus on 22/3/2017.
 */

public class AnamnesisActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private String MailDestinatario = "gibio.pacientes@gmail.com";
    private String MailAsunto = "Nuevo Paciente";
    private String DatosPersonales, Antecedentes, SignosVitales, Electro, Antropo, MailCuerpo;

    //Datos personales
    private EditText etNombre, etApellido, etEdad, etEmail;
    private RadioButton btHombre, btMujer;
    private Spinner spPaises, spProvincias, spOcupacion;
    private Button bENVIAR, bCalIMC;

    //Antecedentes Medicos
    private CheckBox chkBebidasAlcoholicas, chkUsoDrogas, chkTabaquismo, chkSedentarismo, chkMedicaciones;
    private CheckBox chkTos, chkExpectoracion, chkHemoptisis, chkAsma, chkNeumonía;
    private CheckBox chkDbt, chkHta, chkTbc, chkCol, chkHiper;
    private CheckBox chkDbtFam, chkHtaFam, chkTbcFam;
    private EditText  etOtrasEnfermedades, etFamOtrasEnf;

    //Signos Vitales
    private EditText etFC, etFR, etTaxilar, etPesoAct, etPesoHab;
    private EditText etTalla, etPreSis, etPreDia, etDeltax;
    private TextView txtIMC;

    //Mediciones Antropomedicas
    private EditText etCarotidaCuello, etCuelloHombro, etHombroBraquial, etHombroRadial, etCarotidaFemoral;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anamnesis);
        ObjetosInit();
    }

    void ObjetosInit(){

        //Cargo los editText de Datos personales
        etNombre=(EditText)findViewById(R.id.etNombre);
        etApellido=(EditText)findViewById(R.id.etApellido);
        etEmail=(EditText)findViewById(R.id.etEmail);
        etEdad=(EditText)findViewById(R.id.etEdad);

        btHombre=(RadioButton)findViewById(R.id.btHombre);
        btMujer=(RadioButton)findViewById(R.id.btMujer);


        //Cargo lista de paises y provincias
        spPaises = (Spinner)findViewById(R.id.spPaises);
        spProvincias = (Spinner)findViewById(R.id.spProvincias);

        ArrayAdapter<CharSequence> adaptadorPaises = ArrayAdapter.createFromResource(this,
                                R.array.array_Paises, android.R.layout.simple_spinner_item);
        adaptadorPaises.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spPaises.setAdapter(new HintSpinnerAdapter(
                adaptadorPaises, R.layout.hint_paises, this));
        //Se aplica listener para saber que item ha sido seleccionado
        //y poder usarlo en el método "onItemSelected"
        spPaises.setOnItemSelectedListener(this);

        spOcupacion = (Spinner)findViewById(R.id.spOcupacion);
        ArrayAdapter<CharSequence> adaptadorOcupacion = ArrayAdapter.createFromResource(this,
                                R.array.array_Ocupacion, android.R.layout.simple_spinner_item);
        adaptadorOcupacion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOcupacion.setAdapter(new HintSpinnerAdapter(
                adaptadorOcupacion, R.layout.hint_ocupacion, this));

        //Cargo Antecedentes
        chkBebidasAlcoholicas = (CheckBox)findViewById(R.id.chkBebidasAlcoholicas);
        chkUsoDrogas = (CheckBox)findViewById(R.id.chkUsoDrogas);
        chkTabaquismo = (CheckBox)findViewById(R.id.chkTabaquismo);
        chkSedentarismo = (CheckBox)findViewById(R.id.chkSedentarsismo);
        chkMedicaciones = (CheckBox)findViewById(R.id.chkMedicaciones);

        chkTos = (CheckBox)findViewById(R.id.chkTos);
        chkExpectoracion = (CheckBox)findViewById(R.id.chkExpectoracion);
        chkHemoptisis = (CheckBox)findViewById(R.id.chkHemoptisis);
        chkAsma = (CheckBox)findViewById(R.id.chkAsma);
        chkNeumonía = (CheckBox)findViewById(R.id.chkNeumonía);
        chkDbt = (CheckBox)findViewById(R.id.chkDbt);
        chkHta = (CheckBox)findViewById(R.id.chkHta);
        chkTbc = (CheckBox)findViewById(R.id.chkTbc);
        chkCol = (CheckBox)findViewById(R.id.chkCol);
        chkHiper = (CheckBox)findViewById(R.id.chkHiper);

        chkDbtFam = (CheckBox)findViewById(R.id.chkDbtFam);
        chkHtaFam = (CheckBox)findViewById(R.id.chkHtaFam);
        chkTbcFam = (CheckBox)findViewById(R.id.chkTbcFam);

        etOtrasEnfermedades=(EditText)findViewById(R.id.etOtrasEnfermedades);
        etFamOtrasEnf=(EditText)findViewById(R.id.etFamOtrasEnf);

        //Cargo los editText de Signos Vitales
        etFC=(EditText)findViewById(R.id.etFC);

        etFR=(EditText)findViewById(R.id.etFR);

        etTaxilar=(EditText)findViewById(R.id.etTaxilar);

        etPesoHab=(EditText)findViewById(R.id.etPesoHab);
        etPesoAct=(EditText)findViewById(R.id.etPesoAct);
        etTalla=(EditText)findViewById(R.id.etTalla);
        txtIMC=(TextView) findViewById(R.id.txtIMC);

        etPreSis=(EditText)findViewById(R.id.PreSis);
        etPreDia=(EditText)findViewById(R.id.etPreDia);

        //Cargo los editText de Mediciones Antropomedicas
        etCarotidaCuello=(EditText)findViewById(R.id.etCarotidaCuello);
        etCuelloHombro=(EditText)findViewById(R.id.etCuelloHombro);
        etHombroBraquial=(EditText)findViewById(R.id.etHombroBraquial);
        etHombroRadial=(EditText)findViewById(R.id.etHombroRadial);
        etCarotidaFemoral=(EditText)findViewById(R.id.etCarotidaFemoral);


        //Boton para enviar datos
        bENVIAR = (Button) findViewById(R.id.bENVIAR);
        bENVIAR.setOnClickListener(this);

        //Boton para calcular IMC
        bCalIMC = (Button) findViewById(R.id.bCalIMC);
        bCalIMC.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.bCalIMC:

                String txPeso= etPesoAct.getText().toString();
                String txAltura= etTalla.getText().toString();
                int peso = Integer.parseInt(txPeso);
                int altura = Integer.parseInt(txAltura);
                if (peso <=0 || peso>200)   peso=70;
                if (altura <=0 || altura>250)   altura=170;
                int imc =(10000*peso)/(altura*altura);
                String tximc = String.valueOf(imc);
                txtIMC.setText(tximc);

                break;


            case R.id.bENVIAR:
                Cargar_Datos();


                //Compruebo que se completo los datos personales
                if(etNombre.getText().toString().equals("") || etApellido.getText().toString().equals("") ||
                        etEmail.getText().toString().equals("") || etEdad.getText().toString().equals(""))
                {
                    String txtEnviado = "Complete datos personales";
                    Toast.makeText(this, txtEnviado, Toast.LENGTH_LONG).show();
                    etNombre.requestFocus();
                }else {

                /*
                    // es necesario un intent que levante la actividad deseada
                    Intent itSend = new Intent(android.content.Intent.ACTION_SEND);
                    // vamos a enviar texto plano a menos que el checkbox esté marcado
                    itSend.setType("plain/text");

                    //colocamos los datos para el envío
                    itSend.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{MailDestinatario.toString()});
                    itSend.putExtra(android.content.Intent.EXTRA_SUBJECT, MailAsunto.toString());
                    itSend.putExtra(android.content.Intent.EXTRA_TEXT, MailCuerpo.toString());

                    startActivity(itSend);
                    */
                    try
                    {
                        String archivo = "Anamnesis_"+etApellido.getText()+"_"+etNombre.getText();
                        OutputStreamWriter fout=
                                new OutputStreamWriter(
                                        openFileOutput(archivo, Context.MODE_WORLD_READABLE));

                        fout.write(MailCuerpo);
                        fout.close();
                    }
                    catch (Exception ex)
                    {
                        // Log.e("Ficheros", "Error al escribir fichero a memoria interna");
                    }



                    try {
                        sendEmail();
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                   startActivity(new Intent(AnamnesisActivity.this,MainMenuActivity.class));

                }
                break;
        }
    }

    void Cargar_Datos(){
        //Creo el Asunto del mail con el nombre y apellido del paciente
        MailAsunto = MailAsunto +" "+ etNombre.getText() +" "+ etApellido.getText();
        //Creo el Cuerpo del mensaje
        String sexo =" ";
        if (btHombre.isChecked()) sexo = "Hombre";
        else if(btMujer.isChecked()) sexo = "Mujer";

        DatosPersonales ="Datos Personales, "+"\n"+ "Nombre,"+etNombre.getText()+"\n" + "Apellido, "+etApellido.getText()+"\n"+
                    "Edad, "+ etEdad.getText()+"\n"+ "Email, "+etEmail.getText()+"\n"+ "Sexo, "+sexo.toString()+"\n"+
                    "Pais, "+spPaises.getSelectedItem()+"\n" + "Provincia, " +spProvincias.getSelectedItem()+"\n"+
                    "Ocupación, "+ spOcupacion.getSelectedItem()+"\n";

        Antecedentes ="Habitos Toxicos"+"\n"+ "Bebidas Alcóholicas, "+ chkBebidasAlcoholicas.isChecked()+"\n"+
                    "Uso de drogas de abuso, "+chkUsoDrogas.isChecked()+"\n"+ "Tabaquismo, "+ chkTabaquismo.isChecked()+"\n"+
                    "Sedentarismo, "+ chkSedentarismo.isChecked()+"\n"+ "Medicaciones, "+ chkMedicaciones.isChecked()+"\n"+
                    "Patologias, "+"\n" + "Enfermedades Respiratorias, "+"\n" +"Tos,"+chkTos.isChecked()+"\n"+
                    "Expectoración, "+chkExpectoracion.isChecked()+"\n"+ "Hemoptisis, "+ chkHemoptisis.isChecked()+"\n"+
                    "Asma, "+chkAsma.isChecked()+"\n"+ "Neumonía, "+chkNeumonía.isChecked()+"\n"+
                    "Enfermedades Cardiovasculares, "+"\n"+ "Diabetes, "+chkDbt.isChecked()+"\n"+
                    "Hipertension Arterial, "+chkHta.isChecked()+"\n"+ "Tuberculosis, "+chkTbc.isChecked()+"\n"+
                    "Colesterol, "+chkCol.isChecked()+"\n"+ "Hiperglicemia, "+chkHiper.isChecked()+"\n"
                    +"Otras Enfermedades, "+etOtrasEnfermedades.getText()+"\n" +
                    "Antecedentes Familiares, "+"\n"+ "Diabetes Familiar, "+chkDbtFam.isChecked()+"\n"+
                    "Hipertension Arterial Familiar, "+chkHtaFam.isChecked()+"\n"+
                    "Tuberculosis Familiar, "+chkTbcFam.isChecked()+"\n"+
                    "Otras Enfermedades Familiares, "+etFamOtrasEnf.getText()+"\n";

        SignosVitales = "Signos Vitales: "+"\n"+ "Peso Habitual, "+etPesoHab.getText()+"\n"+
                     "Peso Actual, "+etPesoAct.getText()+"\n"+ "Altura, "+etTalla.getText()+"\n"+
                    "IMC, "+ txtIMC.getText() +"\n"+
                    "Frecuencia Cardiaca[ppm], "+etFC.getText()+"\n"+
                    "Frecuencia Respiratoria[rpm], "+etFR.getText()+"\n"+ "T° Axilar, "+etTaxilar.getText()+"\n"+
                    "Presión Sistótica, "+etPreSis.getText()+"\n"+
                    "Presión Diastólica, "+etPreDia.getText()+"\n";


        Antropo = "Mediciones Antropomedicas: "+"\n"+ "Carótida - Cuello,"+etCarotidaCuello.getText()+"\n"+
                "Cuello - Hombro,"+etCuelloHombro.getText()+"\n"+ "Hombro Braquial,"+etHombroBraquial.getText()+"\n"+
                "Hombro - Radial,"+etHombroRadial.getText()+"\n"+ "Carótida - Femoral,"+etCarotidaFemoral.getText()+"\n";


        MailCuerpo ="-------------------------" +"\n"+ DatosPersonales +"-------------------------"
                    +"\n"+ Antecedentes + "-------------------------" +"\n"+ SignosVitales
                    + "-------------------------"  +"\n" +"\n"+ Antropo ;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Se guarda en array de enteros los arrays de las provincias
        int[] localidades = { R.array.array_Argentina, R.array.array_Brasil, R.array.array_Paraguay, R.array.array_Uruguay};

        if (position>0)
        {
            position=position-1;
        }
        //Construcción del "adaptador" para el segundo Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                localidades[position],//En función del pais, se carga el array que corresponda del XML
                android.R.layout.simple_spinner_item);

        //Se carga el tipo de vista para el adaptador
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Se aplica el adaptador al Spinner de localidades
       // spProvincias.setAdapter(adapter);
        spProvincias.setAdapter(new HintSpinnerAdapter(
                adapter, R.layout.hint_provincias, this));

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void sendEmail() throws MessagingException {
        //Getting content for email
        String email = "gibio.pacientes@gmail.com";
        String subject = "Nuevo Paciente: " +etApellido.getText()+" "+etNombre.getText();

        BodyPart texto = new MimeBodyPart();
        texto.setText(MailCuerpo);

        MimeMultipart multiParte = new MimeMultipart();
        multiParte.addBodyPart(texto);

        BodyPart adjunto = new MimeBodyPart();
        String archivo = "Anamnesis_"+etApellido.getText()+"_"+etNombre.getText();
        String filename = getApplicationContext().getFilesDir()+archivo;
        
        DataSource source = new FileDataSource(filename);


        adjunto.setDataHandler(new DataHandler(source));
        adjunto.setFileName(archivo);

        multiParte.addBodyPart(adjunto);



        //

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, multiParte);

        //Executing sendmail to send email
        sm.execute();
    }


}
