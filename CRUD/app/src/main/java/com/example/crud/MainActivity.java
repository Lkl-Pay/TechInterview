package com.example.crud;

import static com.example.crud.R.id.btnAdd;
import static com.example.crud.R.id.rvTable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.crud.api.CommunicationProbe;
import com.example.crud.api.Customer;
import com.example.crud.api.CustomerResponse;
import com.example.crud.api.ServiceAdapter;
import com.example.crud.api.Services;
import com.example.crud.views.CellAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Services services;
    private RecyclerView recyclerView;
    private CellAdapter cellAdapter;
    private List<Customer> customerList = new ArrayList<>();
    private Button btnAdd;
    private Button btnUpdate;
    private Button btnDelete;
    private Customer selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        services = ServiceAdapter.buildRetrofit().create(Services.class);
        recyclerView = findViewById(rvTable);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddRow.class);
            startActivity(intent);
        });

        btnUpdate.setOnClickListener(v -> {
            if (cellAdapter.getSelected() != RecyclerView.NO_POSITION) {
                selected = customerList.get(cellAdapter.getSelected());
                Intent intent = new Intent(MainActivity.this, AddRow.class);
                intent.putExtra("customer", selected);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "Seleccione un cliente a modificar", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(v -> {
            if (cellAdapter.getSelected() != RecyclerView.NO_POSITION) {
                selected = customerList.get(cellAdapter.getSelected());
                delete(selected.getDataType(), selected.getUuid());
            }
            else {
                Toast.makeText(this, "Seleccione un cliente a borrar", Toast.LENGTH_SHORT).show();
            }
        });

        getDataByDataType();
        //test();
        //testCreate();
    }
    private void test() {
        Call<CommunicationProbe> call = services.probeCommunication();
        call.enqueue(new Callback<CommunicationProbe>() {
            @Override
            public void onResponse(@NonNull Call<CommunicationProbe> call,@NonNull Response<CommunicationProbe> response) {
                if (response.isSuccessful())
                {
                    CommunicationProbe communicationProbe = response.body();
                    if(communicationProbe != null)
                    {
                        // Aqui mostrar la fecha en formato correcto
                        System.out.println("Prueba de comunicacion " + response.body().getTime());
                    }
                    else
                    { System.out.println("communicationProbe nulo"); }
                }
                else { System.out.println("Error en la solicitud. Codigo " + response.code()); }
            }
            @Override
            public void onFailure(Call<CommunicationProbe> call, Throwable t) {
                System.out.println("Error " + t.getMessage());

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void getDataByDataType() {
        Call<CustomerResponse> call = services.getMultipleDataByDataType("Cliente");
        call.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(@NonNull Call<CustomerResponse> call,@NonNull Response<CustomerResponse> response) {
                if (response.isSuccessful())
                {
                    CustomerResponse customerResponse = response.body();
                    if(customerResponse != null && customerResponse.getSize() > 0)
                    {
                        System.out.println("getDataByDataType " + response.body().getItems().get(0).getName());
                        customerList = customerResponse.getItems();
                        cellAdapter = new CellAdapter(customerList);
                        recyclerView.setAdapter(cellAdapter);
                    }
                    else
                    { System.out.println("customerResponse nulo"); }
                }
                else { System.out.println("Error en la solicitud. Codigo " + response.code()); }
            }
            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
                System.out.println("Error " + t.getMessage());
            }
        });
    }

    private void delete(String dataType, String uuid) {
        Call<Customer> call = services.deleteSingleDataByDataType( dataType, uuid);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(@NonNull Call<Customer> call,@NonNull Response<Customer> response) {
                if (response.isSuccessful())
                {
                    Customer customer = response.body();
                    if(customer != null)
                    {
                        if (customer.isDeleted())
                            Toast.makeText(getApplicationContext(), "Cliente borrado con exito", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getApplicationContext(), "Cliente no pudo ser borrado", Toast.LENGTH_LONG).show();
                        refresh();
                    }
                    else
                    { System.out.println("customerResponse nulo"); }
                }
                else { System.out.println("Error en la solicitud. Codigo " + response.code()); }
            }
            @Override
            public void onFailure(Call<Customer> call, Throwable t) {


            }
        });
    }

    private void refresh() {
        getDataByDataType();
    }
}