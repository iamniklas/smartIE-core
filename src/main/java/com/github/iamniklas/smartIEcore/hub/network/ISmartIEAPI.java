package com.github.iamniklas.smartIEcore.hub.network;

import com.github.iamniklas.smartIEcore.hub.devices.Device;
import com.github.iamniklas.smartIEcore.hub.devices.InputDevice;
import com.github.iamniklas.smartIEcore.hub.devices.OutputDevice;
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

    @GET
    void getDeviceByID(String id);

    @GET
    void getDeviceEcho();

    @POST("/device")
    Call<HttpStatus> newDevice(@Body Device device);

    @PUT
    void updateDeviceByID(String id);

    @PUT
    void updateInputByDevice(String id);

    @DELETE
    void deleteDeviceByID(String id);

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
    Call<Integer> getTotalRuleCount();

    @GET("tests/device/count")
    Call<Integer> getTotalDeviceCount();

    @DELETE
    void deleteAllRules();

    @DELETE
    void deleteAllDevice();

    //endregion
}
