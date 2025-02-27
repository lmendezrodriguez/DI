package com.lmr.pajareandoapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import com.lmr.pajareandoapp.R;
import com.lmr.pajareandoapp.databinding.ItemBirdBinding;
import com.lmr.pajareandoapp.models.Bird;
import java.util.List;

/**
 * Adaptador que actúa como puente entre los datos (List<Bird>) y la vista (RecyclerView).
 * Utiliza DataBinding para vincular automáticamente los datos con el layout XML.
 */
public class BirdAdapter extends RecyclerView.Adapter<BirdAdapter.BirdViewHolder> {
    // Lista de aves que se mostrarán en el RecyclerView
    private List<Bird> birds;
    // Listener estático para manejar los clics en los items
    private static OnItemClickListener listener = null;

    /**
     * Constructor que inicializa el adaptador con los datos y el manejador de clics.
     * @param birds Lista de aves a mostrar
     * @param listener Interfaz que maneja los clics en los items
     */
    public BirdAdapter(List<Bird> birds, OnItemClickListener listener) {
        this.birds = birds;
        BirdAdapter.listener = listener;
    }

    /**
     * Actualiza la lista de aves y notifica al RecyclerView para que se actualice.
     * Este método es parte del patrón Observer implementado por el adaptador.
     */
    public void setBirds(List<Bird> birds) {
        this.birds = birds;
        notifyDataSetChanged(); // Notifica que todos los datos han cambiado
    }

    /**
     * Crea nuevas instancias de ViewHolder cuando el RecyclerView las necesita.
     * Aquí es donde se infla el layout XML y se crea el binding.
     */
    @NonNull
    @Override
    public BirdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el layout y crea el binding utilizando DataBindingUtil
        ItemBirdBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_bird,  // Layout XML para cada item
                parent,
                false
        );
        return new BirdViewHolder(binding);
    }

    /**
     * Vincula los datos de un ave específica con un ViewHolder.
     * Este método se llama cada vez que se debe mostrar un item en pantalla.
     */
    @Override
    public void onBindViewHolder(@NonNull BirdViewHolder holder, int position) {
        Bird bird = birds.get(position);
        holder.bind(bird); // Delega la vinculación al ViewHolder
    }

    /**
     * Retorna el número total de items en el adaptador.
     * Incluye verificación de nulidad para evitar excepciones.
     */
    @Override
    public int getItemCount() {
        return birds != null ? birds.size() : 0;
    }

    /**
     * Interfaz que define el contrato para manejar clics en los items.
     * Permite la comunicación entre el adaptador y la actividad/fragmento.
     */
    public interface OnItemClickListener {
        void onItemClick(String BirdId);
    }

    /**
     * ViewHolder que contiene y maneja la vista de cada item individual.
     * Utiliza DataBinding para vincular los datos con la vista.
     */
    static class BirdViewHolder extends RecyclerView.ViewHolder {
        // Objeto de binding generado automáticamente para el layout item_bird.xml
        private final ItemBirdBinding binding;

        /**
         * Constructor del ViewHolder que recibe el binding ya inflado.
         */
        public BirdViewHolder(@NonNull ItemBirdBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Vincula un objeto Bird con la vista usando DataBinding.
         * Este método implementa la lógica real de vinculación de datos.
         */
        public void bind(Bird bird) {
            // Asigna el objeto Bird a la variable definida en el layout XML
            binding.setBird(bird);
            // Ejecuta inmediatamente cualquier binding pendiente
            binding.executePendingBindings();
            // Configura el listener de clics para todo el item
            binding.getRoot().setOnClickListener(v ->
                    listener.onItemClick(bird.getBirdId()));
        }
    }
}