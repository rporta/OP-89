/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package io.framework7.myapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import org.apache.cordova.*;
import org.json.*;
import java.lang.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import com.emulate.ProcessKey;
import com.github.nkzawa.emitter.Emitter;
import com.socketImplement.socketConection;
import android.support.v4.content.LocalBroadcastManager;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;

public class MainActivity extends CordovaActivity
{
    public static String TAG = "MainActivity";
    public String URL = "";
    public List URLList = new ArrayList();
    public String PageStatus = "";
    public boolean startFinishLoadPag = false;
    public JSONObject dataFW;
    public Integer resolveCase;
    public String dimensionFw;
    public Integer countLocal = 0;
    public Integer countRemote = 0;
    public socketConection socket;
    public boolean socketOn = false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();

        super.onCreate(savedInstanceState);
        // enable Cordova apps to be started in the background
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean("cdvStartInBackground", false)) {
            moveTaskToBack(true);
        }

        MainActivity self = this;

        loadUrl(launchUrl);

        // f7 -> App(Java) : initSocket
        BroadcastReceiver initSocket = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String data = intent.getExtras().getString("data");
                try {
                    JSONObject dataJson  = new JSONObject(data);
                    String host = dataJson.getString("host");
                    String port = dataJson.getString("port");
                    String type = dataJson.getString("type");
                    String wifi = dataJson.getString("wifi");
                    String driver = dataJson.getJSONObject("driver").toString();

                    LOG.d(TAG, nameofCurrMethod +
                            ", f7 -> App(Java) : initSocket, host : " + host +
                            ", port : " + port +
                            ", type : " + type +
                            ", wifi : " + wifi +
                            ", driver : " + driver
                    );
                    // set socket init
                    if(!self.socketOn){
                        socketConection s = new socketConection(host, Integer.parseInt(port));
                        self.setSocket(s);
                        self.getSocket().init();
                        self.socketOn = !self.socketOn;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(initSocket, new IntentFilter("initSocket"));

        // f7 -> App(Java) : configProcessUrlSocket
        BroadcastReceiver configProcessUrlSocket = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(configProcessUrlSocket, new IntentFilter("configProcessUrlSocket"));

        // f7 -> App(Java) : configProcessUrlSms
        BroadcastReceiver configProcessUrlSms = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(configProcessUrlSms, new IntentFilter("configProcessUrlSms"));

        // f7 -> App(Java) : getClient
        BroadcastReceiver getClient = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(getClient, new IntentFilter("getClient"));

        // f7 -> App(Java) : getDisconnect
        BroadcastReceiver getDisconnect = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(getDisconnect, new IntentFilter("getDisconnect"));

        // f7 -> App(Java) : getClients
        BroadcastReceiver getClients = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(getClients, new IntentFilter("getClients"));

        // f7 -> App(Java) : typingMessage
        BroadcastReceiver typingMessage = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(typingMessage, new IntentFilter("typingMessage"));

        // f7 -> App(Java) : offTypingMessage
        BroadcastReceiver offTypingMessage = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(offTypingMessage, new IntentFilter("offTypingMessage"));

        // f7 -> App(Java) : message
        BroadcastReceiver message = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(message, new IntentFilter("message"));

        // f7 -> App(Java) : init
        BroadcastReceiver init = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(init, new IntentFilter("init"));
    }

    public socketConection getSocket() {
        return socket;
    }

    public void setSocket(socketConection socket) {
        String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
        this.socket = socket;
    }

    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder(input.length());
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }
    /*
     * RAMIRO PORTAS
     * ESTE METHOD SE LLAMA CUANDO SE DISPARA EL EVENTO AL CARGAR UNA NUEVA PAGINA
     * SE LLEGA A ESTE METHOD REALIZANDO Override DE CordovaActivity.onPageFinishedLoading()
     * EN CordovaWebViewImpl SE CREA EL CAMPO contextCordovaActivity
     * CUANDO SE EJECUTA CordovaWebViewImpl.init() - (@Override) se instancia CordovaWebViewImpl.EngineClient()
     * EN EL MISMO METHOD luego de la instancia CordovaWebViewImpl.EngineClient(), se ejecuta CordovaWebViewImpl.EngineClient.addContextCordovaActivity()
     * Y LE PASA EL CONTEXTO CordovaActivity.
     *
     * LOS CAMBIOS REALIZADOS SON
     * +CordovaWebViewImpl.EngineClient.addContextCordovaActivity() (NUEVO)
     * +CordovaWebViewImpl.EngineClient.contextCordovaActivity:CordovaActivity (NUEVO)
     * +CordovaWebViewImpl.contextCordovaActivity:CordovaActivity (NUEVO)
     * +CordovaWebViewImpl.CordovaWebViewImpl(CordovaActivity context, CordovaWebViewEngine cordovaWebViewEngine) (SE MODIFICO)
     * +CordovaActivity.makeWebView() (SE MODIFICO), //aca recien se pasa el contexto CordovaActivity
     * +CordovaActivity.onPageFinishedLoading() (NUEVO)
     * +MainActivity.onPageFinishedLoading() (NUEVO)
     *
     *
     * */
    @Override
    public void onPageFinishedLoading(String url) {
        String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();

        this.URL = url;

        LOG.d(TAG, nameofCurrMethod + ", url " + this.URL );

        if(this.startFinishLoadPag == false){
            //finalizo la carga url local, por primera vez
            countLocal++;

            this.URLList.add(url);
            this.startFinishLoadPag = true;
            this.PageStatus = "finalizo la carga local";

        }else {
            if(url.indexOf("file") != -1){
                //finalizo la carga url local
                if (this.PageStatus == "finalizo la carga remote"){//<-vengo de remote
                    countLocal++;
                    this.PageStatus = "finalizo la carga local";
                }
            }else{
                //finalizo la carga url remota
                this.URLList.add(url);
                if(this.PageStatus == "finalizo la carga local"){//<-vengo de local
                    countRemote++;
                    this.PageStatus = "finalizo la carga remote";
                }
            }
        }
    }
}

