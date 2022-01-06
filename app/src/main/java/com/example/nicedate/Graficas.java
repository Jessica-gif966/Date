package com.example.nicedate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Graficas extends AppCompatActivity {

    View semana;
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        barChart = (BarChart) findViewById(R.id.barchar);
        cargarGrafica();
    }

    public void cargarGrafica() {

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, 3));
        barEntries.add(new BarEntry(2, 5));
        barEntries.add(new BarEntry(3, 8));
        barEntries.add(new BarEntry(4, 4));
        barEntries.add(new BarEntry(5, 3));
        barEntries.add(new BarEntry(6, 2));


        BarDataSet barDataSet = new BarDataSet(barEntries, "Citas de la semana");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.animateY(2000);

        final ArrayList<String> formatter = new ArrayList<>();
        formatter.add("Lunes");
        formatter.add("Martes");
        formatter.add("Miercoles");
        formatter.add("Jueves");
        formatter.add("Viernes");
        formatter.add("Sabado");

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(formatter));

        XAxis x = barChart.getXAxis();
        x.setAxisMaximum(6);
        x.setAxisMinimum(0);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}