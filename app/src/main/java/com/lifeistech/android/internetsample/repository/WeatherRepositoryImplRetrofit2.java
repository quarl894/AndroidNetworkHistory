package com.lifeistech.android.internetsample.repository;

import android.net.Uri;
import android.util.Log;

import com.lifeistech.android.internetsample.entities.Weather;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofitでの実装
 */
public class WeatherRepositoryImplRetrofit2 implements WeatherRepository {
    public static final String TAG = WeatherRepositoryImplRetrofit2.class.getSimpleName();

    private final WeatherService service;

    public WeatherRepositoryImplRetrofit2() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Uri.Builder().scheme(SCHEME).authority(AUTHORITY).build().toString())
                .client(new OkHttpClient())
                .build();
        service = retrofit.create(WeatherService.class);
    }

    @Override
    public void getWeather(final RequestCallback callback) {
        service.getWeather(130010, new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Log.d(TAG, "result: " + response.body().toString());
                callback.success(response.body());
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable error) {
                callback.error(error);
            }
        });
    }

    private interface WeatherService {
        @GET(PATH)
        void getWeather(@Query("city") int city, Callback<Weather> callback);
    }

}
