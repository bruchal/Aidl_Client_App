package com.example.aidl_client_app.model;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.aidl_service_app.IMyAidlInterface;


//MyAppService is used because
// The service and client must be in the same application so that the client can cast the returned object and properly call its APIs.
// The service and client must also be in the same process,
// because this technique doesn't perform any marshalling across processes.
public class MyAppService extends Service {
    String TAG= MyAppService.class.getCanonicalName();
    private final  MyAppServiceBinder binder= new MyAppServiceBinder();
    IMyAidlInterface iMyAidlInterface= null;


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iMyAidlInterface= IMyAidlInterface.Stub.asInterface(iBinder);
            Log.d(TAG, "service connected "+iMyAidlInterface);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "service disconnected ");

        }
    };


    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
   public class MyAppServiceBinder extends Binder    {

        public MyAppService getService() {
            Log.d(TAG, "MyAppService.this "+MyAppService.this);

            return MyAppService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate of service");
        Intent intent= new Intent("MyAidlService");
        intent.setPackage("com.example.aidl_service_app");
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    public int getColor()
    {
        try {
            return iMyAidlInterface.getColor();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}