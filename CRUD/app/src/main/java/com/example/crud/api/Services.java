package com.example.crud.api;


import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Services {
    String base = "/api/v1/";
    String authorization = "Authorization: Bearer " + Constant.TOKEN;

    //Probar comunicacion con la api
    @Headers(authorization)
    @GET(base + "probe")
    Call<CommunicationProbe> probeCommunication();

    //Crear registro
    @Headers(authorization)
    @POST(base + "{data_type}")
    Call<CustomerResponse> addRow(@Path("data_type") String dataType, @Body List<SendCustomer> body);

    //Obtener varios registros por dataType
    @Headers(authorization)
    @GET(base + "{data_type}")
    Call<CustomerResponse> getMultipleDataByDataType(@Path("data_type") String dataType);

    //Actualizar varios registros por dataType
    @Headers(authorization)
    @PUT(base + "{data_type}")
    Call<CustomerResponse> updateMultipleDataByDataType(@Path("data_type") String dataType, @Body List<Customer> body);

    //Borrar varios registros por dataType
    @Headers(authorization)
    @DELETE(base + "{data_type}")
    Call<CustomerResponse> deleteMultipleDataByDataType(@Path("data_type") String dataType, @Body List<Customer> body);

    //Obtener unico registro por dataType
    @Headers(authorization)
    @GET(base + "{data_type}/{uuid}")
    Call<Customer> getSingleDataByDataType(@Path("data_type") String dataType, @Path("uuid") String uuid);

    //Actualizar unico registro por dataType
    @Headers(authorization)
    @PUT(base + "{data_type}/{uuid}")
    Call<Customer> updateSingleDataByDataType(@Path("data_type") String dataType, @Path("uuid") String uuid, @Body SendCustomer body);

    //Borrar unico registro por dataType
    @Headers(authorization)
    @DELETE(base + "{data_type}/{uuid}")
    Call<Customer> deleteSingleDataByDataType(@Path("data_type") String dataType, @Path("uuid") String uuid);

}
