package com.lmr.pajareandoapp.adapters;
import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.lmr.pajareandoapp.R;
import com.squareup.picasso.Picasso;

/**
 * Clase de enlace de datos para cargar imágenes desde URLs utilizando Picasso.
 */
public class BindingAdapters {
    /**
     * Carga una imagen desde una URL en un ImageView utilizando Picasso.
     * @param view ImageView en la que se cargará la imagen.
     * @param url URL de la imagen a cargar.
     */
    @BindingAdapter("urlPhoto")
    public static void loadImage(ImageView view, String url) {
        if (url != null && !url.isEmpty()) {
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.pajareandologo)
                    .error(R.drawable.pajareandologo)
                    .fit()
                    .centerInside()
                    .into(view);
        }
    }
}

