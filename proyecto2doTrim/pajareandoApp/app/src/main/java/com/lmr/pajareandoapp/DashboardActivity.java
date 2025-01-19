package com.lmr.pajareandoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * Actividad principal del dashboard donde se muestran los datos de una observación
 * y se permite al usuario cerrar sesión.
 */
public class DashboardActivity extends AppCompatActivity {
    private TextView titleTextView, descriptionTextView;
    private Button logoutButton;
    private ImageView imageView;

    /**
     * Método que se llama cuando la actividad es creada.
     * Inicializa los elementos de la interfaz y obtiene los datos desde Firebase.
     *
     * @param savedInstanceState Estado guardado de la actividad.
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);  // Habilita el modo EdgeToEdge para la actividad.
        setContentView(R.layout.activity_dashboard);

        // Referencias a los elementos de la interfaz.
        titleTextView = findViewById(R.id.text_title);
        descriptionTextView = findViewById(R.id.text_description);
        logoutButton = findViewById(R.id.button_logout);
        imageView = findViewById(R.id.image_view);

        // Accede a la base de datos de Firebase y obtiene los datos de la observación con ID 1.
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("observations/1");

        // Obtiene los datos de Firebase y actualiza la interfaz de usuario.
        databaseRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot dataSnapshot = task.getResult();
                // Extrae los valores del snapshot de Firebase.
                String title = dataSnapshot.child("scientific_name").getValue(String.class);
                String location = dataSnapshot.child("location").getValue(String.class);
                String date = dataSnapshot.child("date").getValue(String.class);
                String time = dataSnapshot.child("time").getValue(String.class);
                String imageUrl = dataSnapshot.child("url_photo").getValue(String.class);

                // Actualiza la interfaz de usuario con los datos obtenidos.
                titleTextView.setText(title);
                descriptionTextView.setText("Observado en " + location + " el " + date + " a las " + time);
                Picasso.get().load(imageUrl).into(imageView);
            } else {
                // Muestra un mensaje en caso de que no se pueda obtener los datos de Firebase.
                Toast.makeText(DashboardActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });

        // Configura el botón de logout para cerrar sesión.
        logoutButton.setOnClickListener(v -> {
            // Redirige a la actividad de Login.
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Finaliza la actividad actual.
        });
    }
}
