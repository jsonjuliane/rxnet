# rxnet
Reactive limited access network validation using time-bound http request.
 * Validation is a level higher than ConnectivityManager and Network Info.
 * Connectivity validation with two simple steps: Initialization and method call.
 * Uses latest available networking library for efficient and optimized validation. (Retrofit, Gson, Okhttp3)

__Special Thanks: Code is heavily influenced by this guy: https://stackoverflow.com/users/741249/thelper__

```java
 RxNet rxNet = new RxNet(this);
        rxNet.checkLimitedAccess(new RxNet.LimitedAccessCallback() {
            @Override
            public void onResponse(Boolean isConnected) {
                Toast.makeText(getApplicationContext(), String.valueOf(isConnected), Toast.LENGTH_SHORT).show();
            }
        });
```
Download
--------

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
