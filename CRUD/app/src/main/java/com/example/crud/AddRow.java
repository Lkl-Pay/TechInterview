package com.example.crud;

import static com.example.crud.R.id.etAge;
import static com.example.crud.R.id.etAmount;
import static com.example.crud.R.id.etName;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crud.api.Customer;
import com.example.crud.api.CustomerResponse;
import com.example.crud.api.SendCustomer;
import com.example.crud.api.ServiceAdapter;
import com.example.crud.api.Services;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRow extends AppCompatActivity {

    public static final int add = 1;
    public static final int update = 2;
    private EditText EtName;
    private EditText EtAge;
    private EditText EtCard;
    private EditText EtAmount;
    private Button btnCancel;
    private Button btnOk;
    private TextView tvTitle;
    private Services services;
    private int type;
    private Customer customerReceived;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_row);

        services = ServiceAdapter.buildRetrofit().create(Services.class);

        EtName = findViewById(etName);
        EtAge = findViewById(etAge);
        EtCard = findViewById(R.id.etCard);
        EtAmount = findViewById(etAmount);
        tvTitle = findViewById(R.id.tvTitle);
        btnOk = findViewById(R.id.btnOk);
        btnCancel = findViewById(R.id.btnCancel);
        type = add;

        customerReceived = getIntent().getParcelableExtra("customer");
        if (customerReceived != null) {
            EtName.setText(customerReceived.getName());
            EtAge.setText(customerReceived.getAge());
            EtCard.setText(customerReceived.getCard());
            EtAmount.setText(customerReceived.getAmount());
            tvTitle.setText("Modificar Registro de Cliente");
            type = update;
        }

        btnCancel.setOnClickListener(v -> {
            finish();
        });

        btnOk.setOnClickListener(v -> {
            if (type == add)
                addRow();
            if (type == update)
                updateRow();
        });

    }

    private void addRow () {
        List<SendCustomer> customers = new ArrayList<>();
        SendCustomer customer = new SendCustomer();
        try {
            customer.setName(EtName.getText().toString());
            customer.setAge(Integer.parseInt(EtAge.getText().toString()));
            customer.setCard(EtCard.getText().toString());
            customer.setAmount(Double.parseDouble(EtAmount.getText().toString()));
            customers.add(customer);
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        Call<CustomerResponse> call = services.addRow("Cliente", customers);
        call.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(@NonNull Call<CustomerResponse> call, @NonNull Response<CustomerResponse> response) {
                if (response.isSuccessful())
                {
                    CustomerResponse customerResponse = response.body();
                    if(customerResponse != null)
                    {
                        Toast.makeText(getApplicationContext(), "Cliente registrado con exito", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else
                    { Toast.makeText(getApplicationContext(), "CustomerResponse nulo", Toast.LENGTH_LONG).show(); }
                }
                else { Toast.makeText(getApplicationContext(), "Error en la solicitud. Codigo " + response.code(), Toast.LENGTH_LONG).show(); }
            }
            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {


            }
        });
    }

    private void updateRow () {
        SendCustomer customer = new SendCustomer();
        try {
            customer.setName(EtName.getText().toString());
            customer.setAge(Integer.parseInt(EtAge.getText().toString()));
            customer.setCard(EtCard.getText().toString());
            customer.setAmount(Double.parseDouble(EtAmount.getText().toString()));
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        Call<Customer> call = services.updateSingleDataByDataType(customerReceived.getDataType(), customerReceived.getUuid(), customer);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(@NonNull Call<Customer> call, @NonNull Response<Customer> response) {
                if (response.isSuccessful())
                {
                    Customer customerResponse = response.body();
                    if(customerResponse != null)
                    {
                        Toast.makeText(getApplicationContext(), "Cliente modificado con exito", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else
                    { Toast.makeText(getApplicationContext(), "CustomerResponse nulo", Toast.LENGTH_LONG).show(); }
                }
                else { Toast.makeText(getApplicationContext(), "Error en la solicitud. Codigo " + response.code(), Toast.LENGTH_LONG).show(); }
            }
            @Override
            public void onFailure(Call<Customer> call, Throwable t) {


            }
        });
    }
}
