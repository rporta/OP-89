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
import com.github.nkzawa.socketio.client.Socket;
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



        // creo un delay, para socket On
        TimerTask task = new TimerTask() {
            public void run() {
                // Test socket On
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            // socketServer -> App(java) : sendClient
                            self.getSocket().getSocket().on("sendClient", new Emitter.Listener() {
                                @Override
                                public void call(Object... args) {
                                    JSONObject data = (JSONObject)args[0];
                                    LOG.d(TAG, nameofCurrMethod +
                                            ", socketServer -> App(java) : sendClient"
                                    );

                                    // App(java) -> f7 : sendClient
                                    Bundle b = new Bundle();
                                    b.putString("dataType", "socket");
                                    b.putString("event", "sendClient");
                                    b.putString("data", data.toString());
                                    final Intent onDataModuleJava = new Intent("onDataModuleJava");
                                    onDataModuleJava.putExtras(b);
                                    LocalBroadcastManager.getInstance(self).sendBroadcastSync(onDataModuleJava);
                                    LOG.d(TAG, nameofCurrMethod +
                                            ", App(java) -> f7 : sendClient"
                                    );
                                }
                            });

                            // socketServer -> App(java) : disconnect
                            self.getSocket().getSocket().on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                                @Override
                                public void call(Object... args) {
                                    LOG.d(TAG, nameofCurrMethod +
                                            ", socketServer -> App(java) : disconnect"
                                    );

                                    // App(java) -> f7 : disconnect
                                    Bundle b = new Bundle();
                                    b.putString("dataType", "socket");
                                    b.putString("event", "disconnect");
                                    b.putString("data", "");
                                    final Intent onDataModuleJava = new Intent("onDataModuleJava");
                                    onDataModuleJava.putExtras(b);
                                    LocalBroadcastManager.getInstance(self).sendBroadcastSync(onDataModuleJava);


                                    LOG.d(TAG, nameofCurrMethod +
                                            ", App(java) -> f7 : disconnect"
                                    );
                                }
                            });

                            // socketServer -> App(java) : sendMessage
                            self.getSocket().getSocket().on("sendMessage", new Emitter.Listener() {
                                @Override
                                public void call(Object... args) {
                                    JSONArray data = (JSONArray)args[0];
                                    LOG.d(TAG, nameofCurrMethod +
                                            ", socketServer -> App(java) : sendMessage"
                                    );

                                    // App(java) -> f7 : sendMessage
                                    Bundle b = new Bundle();
                                    b.putString("dataType", "socket");
                                    b.putString("event", "sendMessage");
                                    b.putString("data", data.toString());
                                    final Intent onDataModuleJava = new Intent("onDataModuleJava");
                                    onDataModuleJava.putExtras(b);
                                    LocalBroadcastManager.getInstance(self).sendBroadcastSync(onDataModuleJava);
                                    LOG.d(TAG, nameofCurrMethod +
                                            ", App(java) -> f7 : sendMessage " + data.toString()
                                    );
                                }
                            });

                            // socketServer -> App(java) : sendClients
                            self.getSocket().getSocket().on("sendClients", new Emitter.Listener() {
                                @Override
                                public void call(Object... args) {
                                    JSONArray data = (JSONArray)args[0];
                                    LOG.d(TAG, nameofCurrMethod +
                                            ", socketServer -> App(java) : sendClients"
                                    );

                                    // App(java) -> f7 : sendClients
                                    Bundle b = new Bundle();
                                    b.putString("dataType", "socket");
                                    b.putString("event", "sendClients");
                                    b.putString("data", data.toString());
                                    final Intent onDataModuleJava = new Intent("onDataModuleJava");
                                    onDataModuleJava.putExtras(b);
                                    LocalBroadcastManager.getInstance(self).sendBroadcastSync(onDataModuleJava);
                                    LOG.d(TAG, nameofCurrMethod +
                                            ", App(java) -> f7 : sendClients"
                                    );
                                }
                            });

                            // socketServer -> App(java) : sendTypingMessage
                            self.getSocket().getSocket().on("sendTypingMessage", new Emitter.Listener() {
                                @Override
                                public void call(Object... args) {
                                    JSONObject data = (JSONObject)args[0];
                                    LOG.d(TAG, nameofCurrMethod +
                                            ", socketServer -> App(java) : sendTypingMessage"
                                    );

                                    // App(java) -> f7 : sendTypingMessage
                                    Bundle b = new Bundle();
                                    b.putString("dataType", "socket");
                                    b.putString("event", "sendTypingMessage");
                                    b.putString("data", data.toString());
                                    final Intent onDataModuleJava = new Intent("onDataModuleJava");
                                    onDataModuleJava.putExtras(b);
                                    LocalBroadcastManager.getInstance(self).sendBroadcastSync(onDataModuleJava);
                                    LOG.d(TAG, nameofCurrMethod +
                                            ", App(java) -> f7 : sendTypingMessage"
                                    );
                                }
                            });
                        }catch (Exception e){
                            LOG.d(TAG, nameofCurrMethod +
                                    ", catch socket.on(disconnect, sendMessage) : " + e
                            );
                        }

                    }
                });
                // Fin ^ Test socket On
            }
        };
        long delay = 2500L;
        Timer timer = new Timer("socketOn");
        timer.schedule(task, delay);

        task = null;
        timer = null;

        // fin ^ delay


        // f7 -> App(Java) : initSocket
        BroadcastReceiver initSocket = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String data = intent.getExtras().getString("data");
                LOG.d(TAG, nameofCurrMethod +
                        ", f7 -> App(Java) : initSocket"
                );
                try {
                    self.dataFW = new JSONObject(data);
                    String host = self.dataFW.getString("host");
                    String port = self.dataFW.getString("port");
                    String type = self.dataFW.getString("type");
                    String wifi = self.dataFW.getString("wifi");
                    String driver = self.dataFW.getJSONObject("driver").toString();
                    // set socket init
                    if(!self.socketOn){
                        socketConection s = new socketConection(host, Integer.parseInt(port));
                        self.setSocket(s);

                        // App(java) -> socketServer : (conection)
                        self.getSocket().init();
                        self.socketOn = !self.socketOn;

                        LOG.d(TAG, nameofCurrMethod +
                                ", App(java) -> socketServer : (conection)"
                        );
                    }
                    try {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                try {
                                    // socketServer -> App(java) : connect
                                    self.getSocket().getSocket().on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                                        @Override
                                        public void call(Object... args) {
                                            LOG.d(TAG, nameofCurrMethod +
                                                    ", socketServer -> App(java) : connect"
                                            );

                                            // App(java) -> f7 : connect
                                            Bundle b = new Bundle();
                                            b.putString("dataType", "socket");
                                            b.putString("event", "connect");
                                            b.putString("data", self.getSocket().getSocket().id());
                                            final Intent onDataModuleJava = new Intent("onDataModuleJava");
                                            onDataModuleJava.putExtras(b);
                                            LocalBroadcastManager.getInstance(self).sendBroadcastSync(onDataModuleJava);

                                            LOG.d(TAG, nameofCurrMethod +
                                                    ", App(java) -> f7 : connect"
                                            );

                                            // App(java) -> socketServer : init
                                            String event = "init";
                                            self.getSocket().sendEvent(event, self.dataFW);

                                            LOG.d(TAG, nameofCurrMethod +
                                                    ", App(java) -> socketServer : init"
                                            );
                                        }
                                    });
                                }catch (Exception e){
                                    LOG.d(TAG, nameofCurrMethod +
                                            ", catch socket.on(connect, disconnect, sendMessage)"
                                    );
                                }
                            }
                        });
                    }catch (Exception e){
                        LOG.d(TAG, nameofCurrMethod +
                                ", catch socket.on(connect, disconnect)"
                        );
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
                LOG.d(TAG, nameofCurrMethod +
                        ", f7 -> App(Java) : configProcessUrlSocket"
                );
            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(configProcessUrlSocket, new IntentFilter("configProcessUrlSocket"));

        // f7 -> App(Java) : configProcessUrlSms
        BroadcastReceiver configProcessUrlSms = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                LOG.d(TAG, nameofCurrMethod +
                        ", f7 -> App(Java) : configProcessUrlSms"
                );
            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(configProcessUrlSms, new IntentFilter("configProcessUrlSms"));

        // f7 -> App(Java) : getClient
        BroadcastReceiver getClient = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String data = intent.getExtras().getString("data");
                try {
                    self.dataFW = new JSONObject(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                LOG.d(TAG, nameofCurrMethod +
                        ", f7 -> App(Java) : getClient"
                );
                // App(java) -> socketServer : getClient
                String event = "getClient";
                self.getSocket().sendEvent(event, self.dataFW);
                LOG.d(TAG, nameofCurrMethod +
                        ", App(java) -> socketServer : getClient"
                );
            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(getClient, new IntentFilter("getClient"));

        // f7 -> App(Java) : getDisconnect
        BroadcastReceiver getDisconnect = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                LOG.d(TAG, nameofCurrMethod +
                        ", f7 -> App(Java) : getDisconnect"
                );
            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(getDisconnect, new IntentFilter("getDisconnect"));

        // f7 -> App(Java) : getClients
        BroadcastReceiver getClients = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                LOG.d(TAG, nameofCurrMethod +
                        ", f7 -> App(Java) : getClients"
                );
                // App(java) -> socketServer : getClients
                String event = "getClients";
                self.getSocket().sendEvent(event, self.dataFW);
                LOG.d(TAG, nameofCurrMethod +
                        ", App(java) -> socketServer : getClients"
                );
            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(getClients, new IntentFilter("getClients"));

        // f7 -> App(Java) : typingMessage
        BroadcastReceiver typingMessage = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String data = intent.getExtras().getString("data");
                try {
                    self.dataFW = new JSONObject(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                LOG.d(TAG, nameofCurrMethod +
                        ", f7 -> App(Java) : typingMessage"
                );
                // App(java) -> socketServer : typingMessage
                String event = "typingMessage";
                self.getSocket().sendEvent(event, self.dataFW);
                LOG.d(TAG, nameofCurrMethod +
                        ", App(java) -> socketServer : typingMessage"
                );
            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(typingMessage, new IntentFilter("typingMessage"));

        // f7 -> App(Java) : offTypingMessage
        BroadcastReceiver offTypingMessage = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                LOG.d(TAG, nameofCurrMethod +
                        ", f7 -> App(Java) : offTypingMessage"
                );
            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(offTypingMessage, new IntentFilter("offTypingMessage"));

        // f7 -> App(Java) : message
        BroadcastReceiver message = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String data = intent.getExtras().getString("data");
                try {
                    JSONArray dataFW = new JSONArray(data);
                    LOG.d(TAG, nameofCurrMethod +
                            ", f7 -> App(Java) : message"
                    );
                    // App(java) -> socketServer : message
                    String event = "message";
                    self.getSocket().sendEvent(event, dataFW);
                    LOG.d(TAG, nameofCurrMethod +
                            ", App(java) -> socketServer : message"
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(message, new IntentFilter("message"));

        // f7 -> App(Java) : init
        BroadcastReceiver init = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                LOG.d(TAG, nameofCurrMethod +
                        ", f7 -> App(Java) : init"
                );
            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(init, new IntentFilter("init"));

        // f7 -> App(Java) : connect
        BroadcastReceiver connect = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                LOG.d(TAG, nameofCurrMethod +
                        ", f7 -> App(Java) : connect"
                );
            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(connect, new IntentFilter("connect"));

        // f7 -> App(Java) : disconnect
        BroadcastReceiver disconnect = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                LOG.d(TAG, nameofCurrMethod +
                        ", f7 -> App(Java) : disconnect"
                );
            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(disconnect, new IntentFilter("disconnect"));
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

