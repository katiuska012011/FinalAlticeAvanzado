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
import com.google.firebase.auth.FirebaseUser;

public class registro extends AppCompatActivity  {

	private EditText editEmail, editpass, editNombre;
	private Button btnAceder, btnregresar;
	ProgressDialog progressDialog;

	FirebaseAuth.AuthStateListener mAuthListener;
	FirebaseAuth mAuth;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);

		progressDialog = new ProgressDialog(this);
		mAuth = FirebaseAuth.getInstance();

		editNombre = (EditText) findViewById(R.id.editnombreReg);
		editEmail = (EditText) findViewById(R.id.editemailReg);
		editpass = (EditText) findViewById(R.id.editpassReg);
		btnAceder = (Button) findViewById(R.id.btnAcess);
		btnregresar = (Button) findViewById(R.id.regresar);


		btnregresar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				regresarView();
			}
		});

		btnAceder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				registrarUsuario();
			}
		});

		mAuthListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				if(firebaseAuth.getCurrentUser()!=null){
					startActivity(new Intent(registro.this,Home.class));
				}else{
					//Toast.makeText(Login.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
				}
			}
		};
	}

	@Override
	protected void onStart() {
		super.onStart();
		mAuth.addAuthStateListener(mAuthListener);
	}

	private void registrarUsuario() {

		final String nombre = editNombre.getText().toString();
		String email = editEmail.getText().toString();
		String password = editpass.getText().toString();

		if(TextUtils.isEmpty(email))
		{ Toast.makeText(this, "Se debe ingregar un email",Toast.LENGTH_LONG).show();
		return;
		}
		if(TextUtils.isEmpty(password)){
			Toast.makeText(this, "Falta ingregar Contrase;a", Toast.LENGTH_LONG).show();
			return;
		}
		if(TextUtils.isEmpty(nombre)){
			Toast.makeText(this, "Ingrese un nombre", Toast.LENGTH_LONG).show();
			return;
		}
		progressDialog.setMessage("Registrando usuario");
		progressDialog.show();

			mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
				@Override
				public void onComplete(@NonNull Task<AuthResult> task) {
					if (task.isSuccessful()) {
						Toast.makeText(registro.this, "Se ha registrado" + " " + nombre + " con exito", Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(registro.this, "Error al registrarse", Toast.LENGTH_LONG).show();
					}
					progressDialog.dismiss();
				}
			});
		}

	public void regresarView(){
		Intent i = new Intent(this, Login.class);
		startActivity(i);
		finish();
	}
}


