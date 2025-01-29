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
 * Adaptador para la lista de aves en el RecyclerView.
 */
public class BirdAdapter extends RecyclerView.Adapter<BirdAdapter.BirdViewHolder> {
    private List<Bird> birds;
    private static OnItemClickListener listener = null;

    /**
     * Constructor que recibe la lista de aves y el listener para el clic en un elemento.
     */
    public BirdAdapter(List<Bird> birds, OnItemClickListener listener) {
        this.birds = birds;
        BirdAdapter.listener = listener;
    }

    public void setBirds(List<Bird> birds) {
        this.birds = birds;
        notifyDataSetChanged();
    }

    /**
     * Método que se llama cuando se necesita crear un nuevo ViewHolder.
     *
     * @param parent   ViewGroup en el que se creará el ViewHolder.
     * @param viewType Tipo de vista para el ViewHolder.
     */
    @NonNull
    @Override
    public BirdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBirdBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_bird,
                parent,
                false);
        return new BirdViewHolder(binding);
    }

    /**
     * Método que se llama para enlazar los datos de una ave con un ViewHolder.
     */
    @Override
    public void onBindViewHolder(@NonNull BirdViewHolder holder, int position) {
        Bird bird = birds.get(position);
        holder.bind(bird);
    }

    /**
     * Método que devuelve el número total de elementos en la lista.
     * return Birds.size();
     */
    @Override
    public int getItemCount() {
        return birds != null ? birds.size() : 0;
    }

    /**
     * Interfaz para el clic en un elemento de la lista.
     */
    public interface OnItemClickListener {
        void onItemClick(String BirdId);
    }

    /**
     * ViewHolder para cada elemento de la lista.
     */
    static class BirdViewHolder extends RecyclerView.ViewHolder {
        private final ItemBirdBinding binding;

        /**
         * Constructor que recibe el binding del elemento de la lista.
         *
         * @param binding Binding del elemento de la lista.
         */
        public BirdViewHolder(@NonNull ItemBirdBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Método para enlazar los datos de una ave con el ViewHolder.
         *
         * @param bird Ave a enlazar.
         */
        public void bind(Bird bird) {
            binding.setBird(bird);
            binding.executePendingBindings();
            binding.getRoot().setOnClickListener(v ->
                    listener.onItemClick(bird.getBirdId()));
        }
    }
}

