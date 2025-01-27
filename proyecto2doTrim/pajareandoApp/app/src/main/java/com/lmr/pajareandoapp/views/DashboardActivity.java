package com.lmr.pajareandoapp.views;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lmr.pajareandoapp.R;
import com.lmr.pajareandoapp.adapters.BirdAdapter;
import com.lmr.pajareandoapp.databinding.ActivityDashboardBinding;
import com.lmr.pajareandoapp.viewmodels.DashboardViewModel;

import java.util.ArrayList;

/**
 * Actividad principal del dashboard donde se muestra la lista de aves.
 */
public class DashboardActivity extends AppCompatActivity {
    private DashboardViewModel dashboardViewModel;
    private BirdAdapter birdAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDashboardBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        birdAdapter = new BirdAdapter(new ArrayList<>());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(birdAdapter);

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        dashboardViewModel.getBirdsLiveData().observe(this, birds -> birdAdapter.setBirds(birds));

    }
}
