package com.example.aidl_client_app.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.aidl_client_app.R;
import com.example.aidl_client_app.ViewModel.MyAppViewModel;
import com.example.aidl_client_app.model.MyAppService;
import com.example.aidl_service_app.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {
   String TAG= MainActivity.class.getCanonicalName();
        IMyAidlInterface.Stub myAidlService;
        MyAppViewModel myAppViewModel;

//        ServiceConnection connection = new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//                myAidlService= (IMyAidlInterface.Stub) IMyAidlInterface.Stub.asInterface(iBinder);
//                Log.d(TAG, "service connected ");
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName componentName) {
//                Log.d(TAG, "service disconnected ");
//
//            }
//        };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent= new Intent("MyAidlService");
//        intent.setPackage("com.example.aidl_service_app");
//           bindService(intent,connection,BIND_AUTO_CREATE);

        Intent intent= new Intent(this, MyAppService.class);
        startService(intent);

       myAppViewModel=  new ViewModelProvider(this).get(MyAppViewModel.class);
        bindService(intent, myAppViewModel.getServiceConnection(), BIND_AUTO_CREATE);

        Button button= findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    button.setBackgroundColor(myAppViewModel.getColor());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService( myAppViewModel.getServiceConnection());

    }
}