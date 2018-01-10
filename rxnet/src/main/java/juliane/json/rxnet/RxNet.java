package juliane.json.rxnet;

import android.content.Context;
import android.util.Log;

import juliane.json.rxnet.component.AppComponent;
import juliane.json.rxnet.component.DaggerAppComponent;
import juliane.json.rxnet.module.ContextModule;
import juliane.json.rxnet.service.ApiService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jsonjuliane on 10/01/2018.
 */

public class RxNet {

    ApiService apiService;
    Boolean result;

    AppComponent appComponent;

    Context context;

    public RxNet(Context context) {
        this.context = context;
        appComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(context))
                .build();

        apiService = appComponent.getApiService();
    }

    public void checkLimitedAccess(final LimitedAccessCallback limitedAccessCallback) {

        apiService.checkLimitedAccess()
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        limitedAccessCallback.onReturn(response.code() == 204);


                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        limitedAccessCallback.onReturn(false);

                    }
                });

    }

    public interface LimitedAccessCallback{

        void onReturn(Boolean isConnected);

    }

}
