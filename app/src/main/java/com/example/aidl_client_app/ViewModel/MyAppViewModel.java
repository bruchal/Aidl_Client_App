package com.example.aidl_client_app.ViewModel;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aidl_client_app.model.MyAppService;

public class MyAppViewModel extends ViewModel {

    MutableLiveData<MyAppService.MyAppServiceBinder> ibinder = new MutableLiveData<>();

    ServiceConnection myAppServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyAppService.MyAppServiceBinder binder = (MyAppService.MyAppServiceBinder) iBinder;
            Log.d("MyAppViewModel", "binder"+binder);

            ibinder.postValue(binder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            ibinder.postValue(null);
        }
    };

  public   int getColor() {
        try {
            Log.d("TAG", "ibinder.getValue()="+ibinder.getValue());
            return ibinder.getValue().getService().getColor();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    public ServiceConnection getServiceConnection() {
      return  myAppServiceConnection;
    }
}
