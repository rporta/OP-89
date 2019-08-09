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
import com.socketImplement.socketConection;
import android.support.v4.content.LocalBroadcastManager;

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
    public Integer countLocal;
    public Integer countRemote;
    public socketConection socket;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // enable Cordova apps to be started in the background
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean("cdvStartInBackground", false)) {
            moveTaskToBack(true);
        }

        loadUrl(launchUrl);

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
            countLocal = 1;
            Integer w = this.appView.getView().getWidth();
            Integer h = this.appView.getView().getHeight();
            LOG.d(TAG, nameofCurrMethod + ", appView getWidth : " + w + ", appView getHeight : " + h);

            this.URLList.add(url);
            this.startFinishLoadPag = true;
            this.PageStatus = "finalizo la carga local";

        }else {
            if(url.indexOf("file") != -1){            
                if (this.PageStatus == "finalizo la carga remote"){//<-vengo de remote
                    countLocal++;
                    this.PageStatus = "finalizo la carga local";
                }
            }else{
                //finalizo la carga url remota
                this.URLList.add(url);
                if(this.PageStatus == "finalizo la carga local"){//<-vengo de local
                    countRemote = 1;
                    //finalizo la carga url remota por primera vez, realizamos captura de URL, realizamos captura, volvemos a cargar el recurso local
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
            String type = self.dataFW.getString("type");
            String event = self.dataFW.getString("event");
            if(type.indexOf("socket") != -1) {
                switch (event){
                    case "init":
                        LOG.d(TAG, nameofCurrMethod + ", type ( "+ type +" ), event( "+event+" ), dataFull : " +  dataFW);
                        break;
                    case "disconnectSocket":
                        LOG.d(TAG, nameofCurrMethod + ", type ( "+ type +" ), event( "+event+" ), dataFull : " +  dataFW);
                        break;
                    case "getClients":
                        LOG.d(TAG, nameofCurrMethod + ", type ( "+ type +" ), event( "+event+" ), dataFull : " +  dataFW);
                        break;
                    case "typingMessage":
                        LOG.d(TAG, nameofCurrMethod + ", type ( "+ type +" ), event( "+event+" ), dataFull : " +  dataFW);
                        break;
                    case "offTypingMessage":
                        LOG.d(TAG, nameofCurrMethod + ", type ( "+ type +" ), event( "+event+" ), dataFull : " +  dataFW);
                        break;
                    case "message":
                        LOG.d(TAG, nameofCurrMethod + ", type ( "+ type +" ), event( "+event+" ), dataFull : " +  dataFW);
                        break;
                }
            }
            else if(type.indexOf("onImg") != -1){
                switch (event){
                    case "data":
                        LOG.d(TAG, nameofCurrMethod + ", type ( "+ type +" ), event( "+event+" ), dataFull : " +  dataFW);
                        break;
                }
            }
            else if(type.indexOf("data") != -1){
                switch (event){
                    case "onData":
                        LOG.d(TAG, nameofCurrMethod + ", type ( "+ type +" ), event( "+event+" ), dataFull : " +  dataFW);
                        break;
                }
            }
        } catch (JSONException e) {
            LOG.d(TAG, nameofCurrMethod + ", JSONException");
        }
    }
}

