package juliane.json.rxnet.component;

import javax.inject.Singleton;

import dagger.Component;
import juliane.json.rxnet.module.ApiServiceModule;
import juliane.json.rxnet.service.ApiService;

/**
 * Created by jsonjuliane on 10/01/2018.
 */

@Singleton
@Component(modules = ApiServiceModule.class)
public interface AppComponent {

    ApiService getApiService();

}
