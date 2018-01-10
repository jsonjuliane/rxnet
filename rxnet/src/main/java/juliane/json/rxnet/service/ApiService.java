package juliane.json.rxnet.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by jsonjuliane on 10/01/2018.
 */

public interface ApiService {

    @GET("generate_204")
    Call<ResponseBody> checkLimitedAccess();

}
