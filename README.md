# rxnet
Reactive limited access network validation using time-bound http request.
 * Validation is a level higher than ConnectivityManager and Network Info.
 * Connectivity validation with two simple steps: Initialization and method call.
 * Uses latest available networking library for efficient and optimized validation. (Retrofit, Gson, Okhttp3)

__Special Thanks: Code is heavily influenced by this guy: https://stackoverflow.com/users/741249/thelper__

-----

__Initialization, Context and Boolean (true = connected, false = not connected -> Toast a 'No Connection' warning)__

```java

public RxNet(Context context, Boolean subscribe) {
    this.context = context;
    appComponent = DaggerAppComponent.builder()
            .contextModule(new ContextModule(context))
            .build();

    apiService = appComponent.getApiService();

    subscribeConnection(subscribe);

}

```

__For manually checking connection__

```java
 RxNet rxNet = new RxNet(this);
        rxNet.checkLimitedAccess(new RxNet.LimitedAccessCallback() {
            @Override
            public void onResponse(Boolean isConnected) {
                Toast.makeText(getApplicationContext(), String.valueOf(isConnected), Toast.LENGTH_SHORT).show();
            }
        });
```

__Subscribes to Connection Changes ->  You have to implement the SubscriptionCallback class to handle return__

```java

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

```


Download
--------

Add this to your build.gradle : app level
```groovy
dependencies {
  compile 'juliane.json:rxnet:1.0.0'
}
```

License
-------

    Copyright 2018 Json Juliane

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



 [1]: http://square.github.com/dagger/
 [2]: https://search.maven.org/remote_content?g=com.jakewharton&a=butterknife&v=LATEST
 [3]: http://jakewharton.github.com/butterknife/
 [snap]: https://oss.sonatype.org/content/repositories/snapshots/
