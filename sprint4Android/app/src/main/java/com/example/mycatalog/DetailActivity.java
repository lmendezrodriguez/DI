package com.example.mycatalog;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

/**
 * Actividad que muestra los detalles de un elemento, incluyendo una imagen con efectos de estilo.
 */
public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Llamada al método onCreate de la superclase para inicializar la actividad
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Referencia al ImageView definido en el layout
        ImageView imageView = findViewById(R.id.image_1);

        // Cargar una imagen con Glide, aplicando esquinas redondeadas y un borde circular
        Glide.with(this)
                .load(R.drawable.tigrito) // Imagen de recurso; cámbialo por URL si es necesario
                .transform(
                        new RoundedCorners(50), // Transformación de esquinas redondeadas
                        new CropCircleWithBorderTransformation(5, Color.GREEN) // Transformación circular con borde verde
                )
                .into(imageView); // Establece la imagen procesada en el ImageView
    }
}