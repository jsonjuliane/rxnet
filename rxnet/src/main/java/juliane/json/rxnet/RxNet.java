package juliane.json.rxnet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import juliane.json.rxnet.callback.LimitedAccessCallback;
import juliane.json.rxnet.callback.SubscriptionCallback;
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
    AppComponent appComponent;
    Context context;

    public RxNet(Context context, Boolean subscribe) {
        this.context = context;
        appComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(context))
                .build();

        apiService = appComponent.getApiService();

        subscribeConnection(subscribe);

    }

    public void checkLimitedAccess(final LimitedAccessCallback limitedAccessCallback) {

        if (isNetworkAvailable() == true) {

            apiService.checkLimitedAccess()
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            Boolean contentLengthEmpty;

                            try {
                                response.body().contentLength();
                                contentLengthEmpty = false;
                            } catch (NullPointerException err) {
                                contentLengthEmpty = true;
                            }

                            limitedAccessCallback.onResponse(response.code() == 204 && contentLengthEmpty == true);

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                            limitedAccessCallback.onResponse(false);

                        }
                    });

        } else
            showNetworkPrompt().show();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private Dialog showNetworkPrompt() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Network Request");
        builder.setMessage("You need to turn or your wifi or mobile data.");
        builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
        });
        builder.setNegativeButton("Cancel", null);
        return builder.create();

    }

    private void subscribeConnection(Boolean subscribe) {

        if(subscribe) {

            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                NetworkRequest.Builder builder = new NetworkRequest.Builder();
                connectivityManager.registerNetworkCallback(
                        builder.build(),
                        new ConnectivityManager.NetworkCallback() {
                            @Override
                            public void onAvailable(Network network) {


                            }

                            @Override
                            public void onLost(Network network) {

                                Toast.makeText(context, "No connection.", Toast.LENGTH_SHORT).show();

                            }
                        }

                );

            }
        }


    }

    public void subscribeConnection(final SubscriptionCallback subscriptionCallback) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            connectivityManager.registerNetworkCallback(
                    builder.build(),
                    new ConnectivityManager.NetworkCallback() {
                        @Override
                        public void onAvailable(Network network) {

                            checkLimitedAccess(new LimitedAccessCallback() {
                                @Override
                                public void onResponse(Boolean isConnected) {

                                    subscriptionCallback.onSubscribe(isConnected);

                                }
                            });

                        }

                        @Override
                        public void onLost(Network network) {

                            subscriptionCallback.onSubscribe(false);

                        }
                    }

            );

        }
    }

}
