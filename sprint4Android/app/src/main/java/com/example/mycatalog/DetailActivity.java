package com.example.mycatalog;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

// Clase DetailActivity.java
public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Llamada al método onCreate de la superclase
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageView = findViewById(R.id.image_1);

        // Cargar la imagen con borde redondeado y borde externo
        Glide.with(this)
                .load(R.drawable.tigrito) // Cambia si usas una URL u otra fuente
                .transform(
                        new RoundedCorners(50), // Esquinas redondeadas (ajusta el radio según necesites)
                        new CropCircleWithBorderTransformation(5, Color.GREEN) // Borde rojo con ancho de 10px
                )
                .into(imageView);
    }
}
