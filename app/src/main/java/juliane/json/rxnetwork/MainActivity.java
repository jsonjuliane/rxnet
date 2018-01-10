package juliane.json.rxnetwork;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import juliane.json.rxnet.RxNet;
import juliane.json.rxnet.receiver.ConnectionReceiver;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RxNet rxNet = new RxNet(this);
        rxNet.checkLimitedAccess(new RxNet.LimitedAccessCallback() {
            @Override
            public void onResponse(Boolean isConnected) {

                Toast.makeText(getApplicationContext(), String.valueOf(isConnected), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
