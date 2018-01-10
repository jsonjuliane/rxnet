package juliane.json.rxnet.module;

/**
 * Created by jsonjuliane on 10/01/2018.
 */

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

@Module(includes = ContextModule.class)
public class NetworkModule {

    @Provides
    @Singleton
    public File getCacheFile(Context context) {
        return new File(context.getCacheDir(), "okhttp_cache");
    }

    @Provides
    @Singleton
    public Cache getCache(File cacheFile) {
        return new Cache(cacheFile, 10 * 1000 * 1000); //10 MB Cache
    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor getHttpLoggingInterceptor() {

        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i(NetworkModule.class.getName(), message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BASIC);

    }

    @Provides
    @Singleton
    public Interceptor getHeaderInterceptor() {

        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request().newBuilder()
                        .addHeader("User-Agent", "Test")
                        .addHeader("Connection", "close")
                        .build());
            }

        };

    }

    @Provides
    @Singleton
    public OkHttpClient getOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor, Interceptor headerInterceptor, Cache cache) {

        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(headerInterceptor)
                .cache(cache)
                .connectTimeout(500, TimeUnit.MILLISECONDS)
                .readTimeout(500, TimeUnit.MILLISECONDS)
                .build();

    }

}