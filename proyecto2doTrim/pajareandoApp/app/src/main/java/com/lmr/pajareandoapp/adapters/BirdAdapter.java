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

public class BirdAdapter extends RecyclerView.Adapter<BirdAdapter.BirdViewHolder> {
    private List<Bird> birds;
    private static OnItemClickListener listener = null;

    public BirdAdapter(List<Bird> birds) {
        this.birds = birds;
        this.listener = listener;
    }

    public void setBirds(List<Bird> birds) {
        this.birds = birds;
        notifyDataSetChanged();
    }

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

    @Override
    public void onBindViewHolder(@NonNull BirdViewHolder holder, int position) {
        Bird bird = birds.get(position);
        holder.bind(bird);
    }

    @Override
    public int getItemCount() {
        return birds != null ? birds.size() : 0;
    }

    public interface OnItemClickListener {
        void onItemClick(Bird bird);
    }

    static class BirdViewHolder extends RecyclerView.ViewHolder {
        private final ItemBirdBinding binding;

        public BirdViewHolder(@NonNull ItemBirdBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Bird bird) {
            binding.setBird(bird);
            binding.executePendingBindings();
            // Configura el listener de clic en el item
            binding.getRoot().setOnClickListener(v ->
                    listener.onItemClick(bird));
        }
    }
}

