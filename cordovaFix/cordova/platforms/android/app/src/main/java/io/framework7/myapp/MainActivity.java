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

public class MainActivity extends CordovaActivity
{
    public static String TAG = "MainActivity";
    public String URL = "";
    public List URLList = new ArrayList();
    public String PageStatus = "";
    public boolean startFinishLoadPag = false;
    public String dataFW;
    public Integer resolveCase;
    public String dimensionFw;
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

        // Set by <content src="index.html" /> in config.xml
        loadUrl(launchUrl);//aca ejecuta CordovaActivity.init(),
        //CordovaActivity.init() ejecuta makeWebView() y al final CordovaWebViewImpl.ini(cordovaInterface, pluginEntries, preferences)
        //makeWebView() retorna la instancia de CordovaWebViewImpl() que la almacena en CordovaActivity.appView
        //para construir la instancia de CordovaWebViewImpl(), se pasa como argumento la instancia de CordovaWebViewImpl.createEngine(), es decir
        //CordovaWebViewImpl(CordovaWebViewImpl.createEngine(context, ...))
        //
        //a donde va el contexto de CordovaActivity?
        //
        //el method CordovaWebViewImpl.createEngine(), le pasa el contexto de CordovaActivity  y retorna una instancia de SystemWebViewEngine,
        //el constructor de la clase SystemWebViewEngine, en base al contexto 'CordovaActivity' crea la instancia de SystemWebView,
        //SystemWebViewEngine realiza pasajes de parametros entre sus constructores, y finalmente en SystemWebViewEngine.webView se almacena la instancia de SystemWebView
        //al crear la instancia de SystemWebView, en el constructor, le pasa el conteto al super WebView y ejecuta el contructor de WebView
        //WebView extiende de MockView, al ejecutar el constructor de WebView, le pasa el conteto al super MockView (aca creo que es posible realizar recursion CordovaActivity <-> MockView),
        //la clase MockView viene del package

//        socketConection s = new socketConection("hera.opratel.com", 10001);

//        this.setSocket(s);
//        this.getSocket().init();
    }


    public socketConection getSocket() {
        return socket;
    }

    public void setSocket(socketConection socket) {
        this.socket = socket;
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
            //finalizo la carga url local, iniciamos por primera vez, aun no inicia el flujo web

            Integer w = this.appView.getView().getWidth();
            Integer h = this.appView.getView().getHeight();
            LOG.d(TAG, nameofCurrMethod + ", appView getWidth : " + w + ", appView getHeight : " + h);

            this.URLList.add(url);
            this.startFinishLoadPag = true;
            this.PageStatus = "local";

        }else {
            if(url.indexOf("file") != -1){            
                if (this.PageStatus == "remote"){//<-vengo de remote
                    this.PageStatus = "local";
                }
            }else{
                //finalizo la carga url remota
                this.URLList.add(url);
                if(this.PageStatus == "local"){//<-vengo de local
                    //finalizo la carga url remota por primera vez, realizamos captura de URL, realizamos captura, volvemos a cargar el recurso local
                    this.PageStatus = "remote";


                    //creo un delay, para para lanzar la captura
                    TimerTask task = new TimerTask() {
                        public void run() {
                            LOG.d(TAG, nameofCurrMethod + ", saveScreenshot");
                            cordovaInterface.pluginManager.exec("Screenshot", "saveScreenshot", "", "[\"jpg\",50,\"opraTestScreenShot\"]");
                            LOG.d(TAG, nameofCurrMethod + ", loadUrl local");
                            loadUrl((String) URLList.get(0));
                        }
                    };
                    long delay = 1000L;
                    Timer timer = new Timer("Screenshot");
                    timer.schedule(task, delay);

                    task = null;
                    timer = null;
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
        this.dataFW = dataFW;

        JSONObject obj = null;
        try {
            obj = new JSONObject(dataFW);
            String pageUrl = obj.getString("url");
//          String pageUrl = obj.getJSONObject("url").getString("pageName");
            LOG.d(TAG, nameofCurrMethod + ", pageUrl : " + pageUrl);


            //creo un delay, para para loadUrl
            TimerTask task = new TimerTask() {
                public void run() {
                    loadUrl(pageUrl);
                }
            };
            long delay = 1000L;
            Timer timer = new Timer("loadUrl");
            timer.schedule(task, delay);

            task = null;
            timer = null;


        } catch (JSONException e) {
            LOG.d(TAG, nameofCurrMethod + ", no vino pageUrl");
        }

        try{
            obj = new JSONObject(dataFW);
            Boolean cordovaInit = obj.getBoolean("cordovaInit");
            LOG.d(TAG, nameofCurrMethod + ", cordovaInit:" + cordovaInit);

            //finalizo la carga url local
            String json = "{'mensaje' : '"+ this.PageStatus +"' }";
            String js = "javascript:alert(123)";
            this.appView.loadUrl(js);
        } catch (JSONException e) {
            LOG.d(TAG, nameofCurrMethod + ", no vino cordovaInit");
        }
    }

}

