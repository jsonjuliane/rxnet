package juliane.json.rxnet.module;

/**
 * Created by jsonjuliane on 10/01/2018.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GsonModule {

    @Provides
    @Singleton
    public Gson getGson() {

        return new GsonBuilder()
                .serializeNulls()
                .setDateFormat(DateFormat.LONG)
                .setPrettyPrinting()
                .create();

    }

}