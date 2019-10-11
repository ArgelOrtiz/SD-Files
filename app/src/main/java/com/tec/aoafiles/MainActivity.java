package com.tec.aoafiles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    TextInputEditText codeTextInputEditTExt;
    TextInputEditText nameTextInputEditText;
    TextInputEditText phoneTextInputEditText;
    TextInputEditText balanceInputEditText;
    TextView listTextView;

    String cadena   = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    protected void initComponents(){
        codeTextInputEditTExt   = findViewById(R.id.codeMainTextInputEditTExt);
        nameTextInputEditText   = findViewById(R.id.nameMainTextInputEditText);
        phoneTextInputEditText  = findViewById(R.id.phoneMainTextInputEditText);
        balanceInputEditText    = findViewById(R.id.balanceMainInputEditText);
        listTextView            = findViewById(R.id.listMainTextView);
        Button addButton        = findViewById(R.id.addMainButton);
        Button deleteButton     = findViewById(R.id.deleteMainButton);
        Button showButton       = findViewById(R.id.showMainButton);
        Button clearButton      = findViewById(R.id.clearMainButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listTextView.setVisibility(View.GONE);
                String file ;

                String code     = codeTextInputEditTExt.getText().toString();
                String name     = nameTextInputEditText.getText().toString();
                String phone    = phoneTextInputEditText.getText().toString();
                double balance  = Double.parseDouble(balanceInputEditText.getText().toString());


                if (code.isEmpty()){
                    Snackbar.make(v,"Ingrese la clave",Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (name.isEmpty()){
                    Snackbar.make(v,"Ingrese el nombre",Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (phone.isEmpty()){
                    Snackbar.make(v,"Ingrese el telefono",Snackbar.LENGTH_LONG).show();
                    return;
                }

                file = "Clave: "+code+" Nombre: "+name+" Telefono: "+phone+" Saldo: "+balance+",";

                try {

                    crear(file);
                    clearForm();

                    Snackbar.make(v,"Se registro con exito ",Snackbar.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.e("Create",e.getMessage());
                    Snackbar.make(v,"Ocurrio un error al crear el registro: "+e.getMessage(),Snackbar.LENGTH_LONG).show();
                }


            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listTextView.setVisibility(View.GONE);
                try {
                    delete();
                } catch (Exception e) {
                    Log.e("Delete",e.getMessage());
                    Snackbar.make(v,"Ocurrio un error al borrar los registros : "+e.getMessage(),Snackbar.LENGTH_LONG).show();
                }

            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listTextView.setVisibility(View.GONE);

                try {
                    leer();

                    if (cadena.isEmpty())
                        Snackbar.make(v,"No hay ningun registro",Snackbar.LENGTH_LONG).show();
                    else {
                        listTextView.setVisibility(View.VISIBLE);
                        listTextView.setText(cadena);
                    }
                } catch (IOException e) {
                    Log.e("Read",e.getMessage());
                    Snackbar.make(v,"Ocurrio un error al obtener los registros : "+e.getMessage(),Snackbar.LENGTH_LONG).show();
                }


            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void crear(String regpro) throws Exception {

        //Comprobamos el estado de la memoria externa (tarjeta SD)
         String estado = Environment.getExternalStorageState();

         if (estado.equals(Environment.MEDIA_MOUNTED)) {

             leer();
             cadena += regpro;
                 File ruta_sd = Environment.getExternalStorageDirectory();
                 File f = new File(ruta_sd.getAbsolutePath(), "Productos.txt");
                 OutputStreamWriter fout = new OutputStreamWriter(new FileOutputStream(f));
                 fout.write(cadena);
                 fout.close();


         }
    }

    public void leer() throws IOException {
        cadena = "";
        String s;

            File ruta_sd = Environment.getExternalStorageDirectory();
            File f = new File(ruta_sd.getAbsolutePath(), "Productos.txt");
            BufferedReader fin = new BufferedReader(new InputStreamReader(new FileInputStream(f)));

            while((s = fin.readLine())!=null) {
                cadena+=s+"\n";
            }
            fin.close();


    }

    public void delete() throws Exception {
        cadena = "";

        File ruta_sd = Environment.getExternalStorageDirectory();
        File f = new File(ruta_sd.getAbsolutePath(), "Productos.txt");
        OutputStreamWriter fout = new OutputStreamWriter(new FileOutputStream(f));
        fout.write(cadena);
        fout.close();

    }

    protected void clearForm(){
        codeTextInputEditTExt.setText("");
        nameTextInputEditText.setText("");
        phoneTextInputEditText.setText("");
        balanceInputEditText.setText("0");

    }
}
