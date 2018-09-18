package com.example.katiuskagonzalez.finalaa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity  {


	private static final String TAG = "Kat";
	private EditText editTexEmail, editTextpass;
	private Button btnlogin;
	Button registro;
	ProgressDialog progressDialog;
	FirebaseAuth.AuthStateListener mAuthListener;
	FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);

		mAuth = FirebaseAuth.getInstance();

		editTexEmail = (EditText) findViewById(R.id.editEmail);
		editTextpass = (EditText) findViewById(R.id.editPass);
		btnlogin =       (Button) findViewById(R.id.btnlogin);
		registro = (Button) findViewById(R.id.btnregistro);
		registro.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				abrirRegistro();
			}
		});

		mAuthListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

				if(firebaseAuth.getCurrentUser()!=null){
					startActivity(new Intent(Login.this,Home.class));
				}else{
					//Toast.makeText(Login.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
				}
			}
		};

		btnlogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				LoginUsuario();

			}
		});
    }


	@Override
	protected void onStart() {
		super.onStart();
		mAuth.addAuthStateListener(mAuthListener);
	}

	private void LoginUsuario(){
		String email = editTexEmail.getText().toString();
		String pass = editTextpass.getText().toString();


        if(TextUtils.isEmpty(email))
        { Toast.makeText(this, "Ingrese  email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Ingregar Contrase;a", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Iniciando secion");
        progressDialog.show();
			mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

				@Override
				public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Login.this, "Secion iniciada", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Login.this, "Error al iniciar Secion", Toast.LENGTH_LONG).show();
                    }
					progressDialog.dismiss();
				}

			});
		}


	private void abrirRegistro(){
    	Intent i = new Intent(this, registro.class);
    	startActivity(i);
    	finish();
	}

}
