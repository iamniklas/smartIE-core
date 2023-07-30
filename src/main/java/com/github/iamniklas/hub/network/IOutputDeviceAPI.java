package com.github.iamniklas.hub.network;

import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IOutputDeviceAPI {
    @GET
    void scan();

    @POST
    void act();
}
