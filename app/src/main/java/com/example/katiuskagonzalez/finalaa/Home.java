package com.example.katiuskagonzalez.finalaa;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {


	private Button salir;
	Button nuevo;
	private static  final int Gallery_Intent = 1;
	EditText editEmail;
	EditText editPass;
	FirebaseAuth mAuth;
	ProgressDialog progressDialog;

	FirebaseAuth.AuthStateListener mAuthListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		progressDialog = new ProgressDialog(this);
		editEmail = (EditText) findViewById(R.id.editemailReg) ;


		mAuth = FirebaseAuth.getInstance();
		mAuthListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				FirebaseUser user = mAuth.getCurrentUser();
				if(user == null){
					abriLogin();
				}
				else {
					editEmail.setText(user.getEmail());
				}
			}
		};

		findViewById(R.id.nuevo).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(Home.this,Agregar.class);
				startActivity(i);
			}
		});


            //Evento para cerrar Secion

		salir = (Button)findViewById(R.id.salir);

		salir.setOnClickListener(new View.OnClickListener() {

			@Override

			public void onClick(View view) {
				FirebaseAuth.getInstance().signOut();
				startActivity(new Intent(Home.this, Login.class));
				progressDialog.dismiss();
			}
		});
		}

		public void abriLogin(){
		Intent i = new Intent(Home.this, Login.class);
		startActivity(i);
		finish();
		}



	}

