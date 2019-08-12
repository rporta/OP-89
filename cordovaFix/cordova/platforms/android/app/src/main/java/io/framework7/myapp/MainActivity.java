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

        //intento f7 -> App(Java)
        final BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String event = intent.getAction();
                String item = intent.getExtras().getString("item");
                LOG.d(TAG, nameofCurrMethod + ", event : " + event + ", item : " + item);

                String pepe2 = intent.getExtras().toString();
                LOG.d(TAG, nameofCurrMethod + ", event : " + event + ", pepe : " + pepe2);





                //intento App(java) -> f7
                final Intent intent2 = new Intent("onDataModuleJava");

                //set data

                JSONObject data = new JSONObject();
                try {
                    data.put("test", "probando App(java) -> f7");
                    LOG.d(TAG, nameofCurrMethod + ", data : " + data.toString());
                } catch (JSONException e) {
//                    e.printStackTrace();
                    LOG.d(TAG, nameofCurrMethod + ", catch(), data : " + data.toString());

                }

                Bundle b = new Bundle();
                b.putString("dataType", "data");
                b.putString("event", "onData");
                b.putString("data", data.toString());
                intent2.putExtras(b);
                LOG.d(TAG, nameofCurrMethod + ", event : " + b.getString("event") + ", data : " + b.getString("data"));

                LocalBroadcastManager.getInstance(self).sendBroadcastSync(intent2);
            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(receiver, new IntentFilter("test.event"));
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
            Integer w = this.appView.getView().getWidth();
            Integer h = this.appView.getView().getHeight();
            LOG.d(TAG, nameofCurrMethod + ", appView getWidth : " + w + ", appView getHeight : " + h);

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
    /*
     * RAMIRO PORTAS
     * ESTE METHOD SE INVOCA CUANDO FLUJO WEB ENVIA DATA AL MODULO APP(JAVA)
     * */
    @Override
    public void onDataFW(String dataFW) {
        String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
        MainActivity self = this;

        try {
            self.dataFW = new JSONObject(dataFW);
            LOG.d(TAG, nameofCurrMethod + ", dataFW : " +  self.dataFW);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            self.dataFW = new JSONObject(dataFW);
            String type = self.dataFW.getString("type");
            String event = self.dataFW.getString("event");
            JSONObject data = self.dataFW.getJSONObject("data");

            if(!self.socketOn){
                String host = self.dataFW.getJSONObject("data").getString("host");
                String port = self.dataFW.getJSONObject("data").getString("port");
                socketConection s = new socketConection(host, Integer.parseInt(port));
                this.setSocket(s);
                this.getSocket().init();

                self.socketOn = !self.socketOn;
            }
            runOnUiThread(new Runnable() {
                public void run() {
                    self.getSocket().getSocket().on("init", new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            JSONObject data = (JSONObject) args[0];
                            LOG.d(TAG, "init" + data);

                            final Intent intent = new Intent("onDataModuleJava");
                            Bundle b = new Bundle();
                            b.putString("type", "socket");
                            b.putString("event", "init");
                            b.putString("data", data.toString());
                            intent.putExtras(b);
                            LocalBroadcastManager.getInstance(self).sendBroadcastSync(intent);
                        }
                    });
                    self.getSocket().getSocket().on("disconnectSocket", new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            JSONObject data = (JSONObject) args[0];
                            LOG.d(TAG, "disconnectSocket" + data);

                            final Intent intent = new Intent("onDataModuleJava");
                            Bundle b = new Bundle();
                            b.putString("type", "socket");
                            b.putString("event", "disconnectSocket");
                            b.putString("data", data.toString());
                            intent.putExtras( b);
                            LocalBroadcastManager.getInstance(self).sendBroadcastSync(intent);
                        }
                    });
                    self.getSocket().getSocket().on("getClients", new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            JSONObject data = (JSONObject) args[0];
                            LOG.d(TAG, "getClients" + data);

                            final Intent intent = new Intent("onDataModuleJava");
                            Bundle b = new Bundle();
                            b.putString("type", "socket");
                            b.putString("event", "getClients");
                            b.putString("data", data.toString());
                            intent.putExtras( b);
                            LocalBroadcastManager.getInstance(self).sendBroadcastSync(intent);
                        }
                    });
                    self.getSocket().getSocket().on("typingMessage", new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            JSONObject data = (JSONObject) args[0];
                            LOG.d(TAG, "typingMessage" + data);

                            final Intent intent = new Intent("onDataModuleJava");
                            Bundle b = new Bundle();
                            b.putString("type", "socket");
                            b.putString("event", "typingMessage");
                            b.putString("data", data.toString());
                            intent.putExtras( b);
                            LocalBroadcastManager.getInstance(self).sendBroadcastSync(intent);
                        }
                    });
                    self.getSocket().getSocket().on("offTypingMessage", new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            JSONObject data = (JSONObject) args[0];
                            LOG.d(TAG, "offTypingMessage" + data);

                            final Intent intent = new Intent("onDataModuleJava");
                            Bundle b = new Bundle();
                            b.putString("type", "socket");
                            b.putString("event", "offTypingMessage");
                            b.putString("data", data.toString());
                            intent.putExtras( b);
                            LocalBroadcastManager.getInstance(self).sendBroadcastSync(intent);
                        }
                    });
                    self.getSocket().getSocket().on("message", new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            JSONObject data = (JSONObject) args[0];
                            LOG.d(TAG, "message" + data);

                            final Intent intent = new Intent("onDataModuleJava");
                            Bundle b = new Bundle();
                            b.putString("type", "socket");
                            b.putString("event", "offTypingMessage");
                            b.putString("data", data.toString());
                            intent.putExtras( b);
                            LocalBroadcastManager.getInstance(self).sendBroadcastSync(intent);
                        }
                    });
                }
            });


            if(type.indexOf("socket") != -1) {
                switch (event){
                    case "init":
                        LOG.d(TAG, nameofCurrMethod + ", type ( "+ type +" ), event( "+event+" ), dataFull : " +  self.dataFW.getJSONObject("data"));
                        this.getSocket().sendEvent(event, data);
                        break;
                    case "disconnectSocket":
                        LOG.d(TAG, nameofCurrMethod + ", type ( "+ type +" ), event( "+event+" ), dataFull : " +  self.dataFW.getJSONObject("data"));
                        this.getSocket().sendEvent(event, data);
                        break;
                    case "getClients":
                        LOG.d(TAG, nameofCurrMethod + ", type ( "+ type +" ), event( "+event+" ), dataFull : " +  self.dataFW.getJSONObject("data"));
                        this.getSocket().sendEvent(event, data);
                        break;
                    case "typingMessage":
                        LOG.d(TAG, nameofCurrMethod + ", type ( "+ type +" ), event( "+event+" ), dataFull : " +  self.dataFW.getJSONObject("data"));
                        this.getSocket().sendEvent(event, data);
                        break;
                    case "offTypingMessage":
                        LOG.d(TAG, nameofCurrMethod + ", type ( "+ type +" ), event( "+event+" ), dataFull : " +  self.dataFW.getJSONObject("data"));
                        this.getSocket().sendEvent(event, data);
                        break;
                    case "message":
                        LOG.d(TAG, nameofCurrMethod + ", type ( "+ type +" ), event( "+event+" ), dataFull : " +  self.dataFW.getJSONObject("data"));
                        this.getSocket().sendEvent(event, data);
                        break;
                }
            }
            else if(type.indexOf("onImg") != -1){
                switch (event){
                    case "data":
                        LOG.d(TAG, nameofCurrMethod + ", type ( "+ type +" ), event( "+event+" ), dataFull : " +  self.dataFW.getJSONObject("data"));
                        break;
                }
            }
            else if(type.indexOf("data") != -1){
                switch (event){
                    case "onData":
                        LOG.d(TAG, nameofCurrMethod + ", type ( "+ type +" ), event( "+event+" ), dataFull : " +  self.dataFW.getJSONObject("data"));
                        break;
                }
            }
        } catch (JSONException e) {
            LOG.d(TAG, nameofCurrMethod + ", JSONException");
        }
    }
}

