package com.github.iamniklas.smartIEcore.hub.network;

import com.github.iamniklas.smartIEcore.hub.devices.Device;
import com.github.iamniklas.smartIEcore.hub.devices.InputDevice;
import com.github.iamniklas.smartIEcore.hub.devices.OutputDevice;
import com.github.iamniklas.smartIEcore.hub.network.javalin.controller.implementation.TestsController;
import com.github.iamniklas.smartIEcore.hub.rules.Rule;
import io.javalin.http.HttpStatus;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ISmartIEAPI {
    @GET
    void index();

    //region Rule

    @GET
    void getRuleByID(String id);

    @GET
    void getAllRules();

    @POST
    Call<HttpStatus> newRule(@Body Rule rule);

    @PUT
    void updateRuleByID(String id);

    @DELETE
    void deleteRuleByID(String id);

    //endregion

    //region Device

    @GET
    void getAllDevices();

    @GET("/device/INPUT/scan")
    Call<List<InputDevice>> scanForInputDevices();

    @GET("/device/OUTPUT/scan")
    Call<List<OutputDevice>> scanForOutputDevices();

    @GET("/device/INPUT/{device_id}")
    Call<InputDevice> getInputDeviceByID(@Path("device_id") String id);

    @GET("/device/OUTPUT/{device_id}")
    Call<OutputDevice> getOutputDeviceByID(@Path("device_id") String id);

    @GET
    void getDeviceEcho();

    @POST("/device")
    Call<Device> newDevice(@Body Device device);

    @PUT("/device/{id}")
    Call<Device> updateDeviceByID(@Path("id") String id, @Body Device device);

    @PUT("/device/{device_id}/{sensor_id}")
    Call<Device> updateDeviceSensorByID(@Path("device_id") String deviceId, @Path("sensor_id") String sensorId, @Body Object object);

    @DELETE("/device/{id}")
    Call<Device> deleteDeviceByID(@Path("id") String id);

    //endregion

    //region Execution History

    @GET
    void getAllRuleExecutions();

    @GET
    void getAllDeviceExecutions();

    @GET
    void getRuleExecutionsById(String id);

    @GET
    void getDeviceExecutionsById(String id);

    //endregion

    //region Tests

    @GET("tests/rule/count")
    Call<TestsController.CountObject> getTotalRuleCount();

    @GET("tests/device/count")
    Call<TestsController.CountObject> getTotalDeviceCount();

    @DELETE("/tests/rule/all")
    Call<TestsController.CountObject> removeAllRules();

    @DELETE("/tests/device/all")
    Call<TestsController.CountObject> removeAllDevices();

    //endregion
}
