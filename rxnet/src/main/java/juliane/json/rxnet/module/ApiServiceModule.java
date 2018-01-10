package juliane.json.rxnet.module;

/**
 * Created by jsonjuliane on 10/01/2018.
 */

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import juliane.json.rxnet.service.ApiService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {NetworkModule.class, GsonModule.class})
public class ApiServiceModule {

    @Provides
    @Singleton
    public ApiService getApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    public Retrofit getRetrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl("http://clients3.google.com/")
                .build();
    }


}