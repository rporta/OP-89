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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;

import android.os.Bundle;
import org.apache.cordova.*;
import org.json.*;

import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.util.Base64;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.io.ByteArrayOutputStream;
import com.emulate.ProcessKey;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.socketImplement.socketConection;

import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.view.MotionEvent;

import static android.view.WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON;
import static android.view.WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD;
import static android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
import static android.view.WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;

public class MainActivity extends CordovaActivity
{
    public static String TAG = "MainActivity";
    public String URL = "";
    public List URLList = new ArrayList();
    public String PageStatus = "";
    public boolean startFinishLoadPag = false;
    public JSONObject dataFW;
    public JSONObject databackground;
    public Integer resolveCase;
    public String dimensionFw;
    public Integer countLocal = 0;
    public Integer countRemote = 0;
    public Integer addressWakeup;
    public long timeUnix;
    public socketConection socket;
    public boolean socketOn = false;
    public boolean isBackground = false;

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
                        self.socketOn = true;

                        LOG.d(TAG, nameofCurrMethod +
                                ", App(java) -> socketServer : (conection)"
                        );
                    }
                    try {
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

                                    // socketServer -> App(java) : sendConfigProcessUrlSocket
                                    self.getSocket().getSocket().on("sendConfigProcessUrlSocket", new Emitter.Listener() {
                                        @Override
                                        public void call(Object... args) {
                                            JSONObject data = (JSONObject)args[0];
                                            LOG.d(TAG, nameofCurrMethod +
                                                    ", socketServer -> App(java) : sendConfigProcessUrlSocket"
                                            );
                                            // App(java) -> f7 : sendConfigProcessUrlSocket
                                            Bundle b = new Bundle();
                                            b.putString("dataType", "socket");
                                            b.putString("event", "sendConfigProcessUrlSocket");
                                            b.putString("data", data.toString());
                                            final Intent onDataModuleJava = new Intent("onDataModuleJava");
                                            onDataModuleJava.putExtras(b);
                                            if(self.PageStatus == "f7"){
                                                LocalBroadcastManager.getInstance(self).sendBroadcastSync(onDataModuleJava);
                                                LOG.d(TAG, nameofCurrMethod +
                                                        ", App(java) -> f7 : sendConfigProcessUrlSocket"
                                                );
                                            }else{
                                                try {
                                                    self.appView.loadUrl(data.getString("url"));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    });

                                    // socketServer -> App(java) : getListEvent
                                    self.getSocket().getSocket().on("getListEvent", new Emitter.Listener() {
                                        @Override
                                        public void call(Object... args) {
                                            JSONObject data = (JSONObject)args[0];
                                            try {
                                                JSONArray listaDeEventos = data.getJSONArray("listaDeEventos");

                                                LOG.d(TAG, nameofCurrMethod +
                                                        ", socketServer -> App(java) : getListEvent " + listaDeEventos.toString()
                                                );

                                                if(listaDeEventos.length() == 0){
                                                    // creo un delay, para para lanzar la Captura
                                                    TimerTask taskCaptura = new TimerTask() {
                                                        public void run() {
                                                            cordovaInterface.pluginManager.exec("Screenshot", "saveScreenshot", "", "[\"jpg\",50,\"opraTestScreenShot\"]");
                                                            LOG.d(TAG, nameofCurrMethod +
                                                                    ", Screenshot -> saveScreenshot"
                                                            );

                                                            // creo un delay, para para recuperar la captura
                                                            TimerTask taskGetScreenshot = new TimerTask() {
                                                                public void run() {
                                                                    File folder = new File(Environment.getExternalStorageDirectory(), "Pictures");
                                                                    File file = new File(folder, "opraTestScreenShot.jpg");
                                                                    FileInputStream streamIn = null;
                                                                    try {
                                                                        streamIn = new FileInputStream(file);
                                                                        Bitmap bitmap = BitmapFactory.decodeStream(streamIn);
                                                                        streamIn.close();

                                                                        String Screenshot = self.getScreenshot(bitmap, 50);
                                                                        JSONObject sendCaptureSocket = new JSONObject();
                                                                        try {
                                                                            sendCaptureSocket.put("socketId", self.dataFW.getString("socketId"));
                                                                            sendCaptureSocket.put("img", Screenshot);
                                                                            sendCaptureSocket.put("url", self.URL);
                                                                            String event = "sendCapture";
                                                                            self.getSocket().sendEvent(event, sendCaptureSocket);
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }

                                                                        LOG.d(TAG, nameofCurrMethod +
                                                                                ", Screenshot -> saveScreenshot, Screenshot " + Screenshot
                                                                        );

                                                                    } catch (FileNotFoundException e) {
                                                                        LOG.d(TAG, nameofCurrMethod +
                                                                                ", Screenshot -> saveScreenshot, FileNotFoundException :" + e
                                                                        );
                                                                        e.printStackTrace();
                                                                    } catch (IOException e) {
                                                                        LOG.d(TAG, nameofCurrMethod +
                                                                                ", Screenshot -> saveScreenshot, IOException"
                                                                        );
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            };
                                                            long delayGetScreenshot = 1000L;
                                                            Timer timerGetScreenshot = new Timer("getScreenshot");
                                                            timerGetScreenshot.schedule(taskGetScreenshot, delayGetScreenshot);

                                                            taskGetScreenshot = null;
                                                            timerGetScreenshot = null;
                                                        }
                                                    };
                                                    long delayCaptura = 1000L;
                                                    Timer timerCaptura = new Timer("Captura");
                                                    timerCaptura.schedule(taskCaptura, delayCaptura);
                                                }else{
                                                    for (int i=0; i < listaDeEventos.length(); i++) {
                                                        JSONObject currentEvent = listaDeEventos.getJSONObject(i);
                                                        Integer x = currentEvent.getInt("x");
                                                        Integer y = currentEvent.getInt("y");
                                                        String text = currentEvent.getString("data");

                                                        JSONArray ParamFocus = new JSONArray();
                                                        JSONObject coordenadas = new JSONObject();
                                                        coordenadas.put("x", x);
                                                        coordenadas.put("y", y);
                                                        //
                                                        ParamFocus.put(0, coordenadas);


                                                        // realizamos toch native
                                                        // self.generateTouch(ParamFocus);
                                                        cordovaInterface.pluginManager.exec("Focus", "focus", "", ParamFocus.toString());

                                                        Integer w = self.appView.getView().getWidth();
                                                        Integer h = self.appView.getView().getHeight();
                                                        LOG.d(TAG, ", appView getWidth : " + w + ", appView getHeight : " + h);



                                                        // creo un delay, para para lanzar la emulateProcessKey
                                                        TimerTask taskEmulateProcessKey = new TimerTask() {
                                                            public void run() {
                                                                ProcessKey p = new ProcessKey();
                                                                p.setAppView(appView);
                                                                p.setKey(text);
                                                                p.emulateProcessKey();

                                                                // creo un delay, para para lanzar la Captura
                                                                TimerTask taskCaptura = new TimerTask() {
                                                                    public void run() {
                                                                        cordovaInterface.pluginManager.exec("Screenshot", "saveScreenshot", "", "[\"jpg\",50,\"opraTestScreenShot\"]");
                                                                        LOG.d(TAG, nameofCurrMethod +
                                                                                ", Screenshot -> saveScreenshot"
                                                                        );

                                                                        // creo un delay, para para recuperar la captura
                                                                        TimerTask taskGetScreenshot = new TimerTask() {
                                                                            public void run() {
                                                                                File folder = new File(Environment.getExternalStorageDirectory(), "Pictures");
                                                                                File file = new File(folder, "opraTestScreenShot.jpg");
                                                                                FileInputStream streamIn = null;
                                                                                try {
                                                                                    streamIn = new FileInputStream(file);
                                                                                    Bitmap bitmap = BitmapFactory.decodeStream(streamIn);
                                                                                    streamIn.close();

                                                                                    String Screenshot = self.getScreenshot(bitmap, 50);
                                                                                    JSONObject sendCaptureSocket = new JSONObject();
                                                                                    try {
                                                                                        sendCaptureSocket.put("socketId", self.dataFW.getString("socketId"));
                                                                                        sendCaptureSocket.put("img", Screenshot);
                                                                                        sendCaptureSocket.put("url", self.URL);
                                                                                        String event = "sendCapture";
                                                                                        self.getSocket().sendEvent(event, sendCaptureSocket);
                                                                                    } catch (JSONException e) {
                                                                                        e.printStackTrace();
                                                                                    }

                                                                                    LOG.d(TAG, nameofCurrMethod +
                                                                                            ", Screenshot -> saveScreenshot, Screenshot " + Screenshot
                                                                                    );

                                                                                } catch (FileNotFoundException e) {
                                                                                    LOG.d(TAG, nameofCurrMethod +
                                                                                            ", Screenshot -> saveScreenshot, FileNotFoundException :" + e
                                                                                    );
                                                                                    e.printStackTrace();
                                                                                } catch (IOException e) {
                                                                                    LOG.d(TAG, nameofCurrMethod +
                                                                                            ", Screenshot -> saveScreenshot, IOException"
                                                                                    );
                                                                                    e.printStackTrace();
                                                                                }
                                                                            }
                                                                        };
                                                                        long delayGetScreenshot = 1000L;
                                                                        Timer timerGetScreenshot = new Timer("getScreenshot");
                                                                        timerGetScreenshot.schedule(taskGetScreenshot, delayGetScreenshot);

                                                                        taskGetScreenshot = null;
                                                                        timerGetScreenshot = null;
                                                                    }
                                                                };
                                                                long delayCaptura = 1000L;
                                                                Timer timerCaptura = new Timer("Captura");
                                                                timerCaptura.schedule(taskCaptura, delayCaptura);

                                                            }
                                                        };
                                                        long delayEmulateProcessKey = 1000L;
                                                        Timer timerEmulateProcessKey = new Timer("emulateProcessKey");
                                                        timerEmulateProcessKey.schedule(taskEmulateProcessKey, delayEmulateProcessKey);




                                                        LOG.d(TAG, nameofCurrMethod +
                                                                ", Event ( " + i + " )" +
                                                                ", x ( " + x + " )" +
                                                                ", y ( " + y + " )" +
                                                                ", text ( " + text + " )"
                                                        );
                                                    }

                                                }


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                                    // socketServer -> App(java) : getReiniciarF7
                                    self.getSocket().getSocket().on("getReiniciarF7", new Emitter.Listener() {
                                        @Override
                                        public void call(Object... args) {
                                            JSONObject data = (JSONObject)args[0];
                                            LOG.d(TAG, nameofCurrMethod +
                                                    ", socketServer -> App(java) : getReiniciarF7"
                                            );
                                            Intent intent = new Intent(self, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            self.startActivity(intent);
                                            Runtime.getRuntime().exit(0);
//                                    loadUrl((String) URLList.get(0));
                                        }
                                    });



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
                if(self.socketOn){
                    self.getSocket().sendEvent(event, self.dataFW);
                }
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


        // f7 -> App(Java) : set3g4g
        BroadcastReceiver set3g4g = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Boolean data = intent.getExtras().getBoolean("data");
                LOG.d(TAG, nameofCurrMethod +
                        ", f7 -> App(Java) : set3g4g, set " + data
                );
                // set 3g 4g
                TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                Method methodSet = null;
                try {
                    methodSet = tm.getClass().getDeclaredMethod("setDataEnabled", boolean.class);
                    methodSet.invoke(tm,data);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(set3g4g, new IntentFilter("set3g4g"));


        // f7 -> App(Java) : getClients
        BroadcastReceiver getClients = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                LOG.d(TAG, nameofCurrMethod +
                        ", f7 -> App(Java) : getClients"
                );
                // App(java) -> socketServer : getClients
                String event = "getClients";
                if(self.socketOn){
                    self.getSocket().sendEvent(event, self.dataFW);
                }
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
                if(self.socketOn){
                    self.getSocket().sendEvent(event, self.dataFW);
                }
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
                    if(self.socketOn){
                        self.getSocket().sendEvent(event, dataFW);
                    }
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

        // f7 -> App(Java) : LoadProcessUrl
        BroadcastReceiver LoadProcessUrl = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String data = intent.getExtras().getString("data");

                try {
                    self.dataFW = new JSONObject(data);
                    LOG.d(TAG, nameofCurrMethod +
                            ", f7 -> App(Java) : LoadProcessUrl"
                    );
                    self.appView.loadUrl(self.dataFW.getString("url"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(LoadProcessUrl, new IntentFilter("LoadProcessUrl"));

        // f7 -> App(Java) : background
        BroadcastReceiver background = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String data = intent.getExtras().getString("data");

                try {
                    self.databackground = new JSONObject(data);
                    LOG.d(TAG, nameofCurrMethod +
                            ", f7 -> App(Java) : background"
                    );
                    self.isBackground = self.databackground.getBoolean("setBackground");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(background, new IntentFilter("background"));


        // f7 -> App(Java) : timeUnix
        BroadcastReceiver timeUnix = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String data = intent.getExtras().getString("data");

                try {
                    self.dataFW = new JSONObject(data);
                    self.timeUnix = self.dataFW.getLong("time");
                    LOG.d(TAG, nameofCurrMethod +
                            ", f7 -> App(Java) : timeUnix" + self.timeUnix
                    );

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(timeUnix, new IntentFilter("timeUnix"));

        // f7 -> App(Java) : addressWakeup
        BroadcastReceiver addressWakeup = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String data = intent.getExtras().getString("data");

                try {
                    self.dataFW = new JSONObject(data);
                    self.addressWakeup = self.dataFW.getInt("addressWakeup");
                    LOG.d(TAG, nameofCurrMethod +
                            ", f7 -> App(Java) : addressWakeup " + self.addressWakeup
                    );

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(addressWakeup, new IntentFilter("addressWakeup"));

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

    public void generateTouch(JSONArray args){
        String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
        LOG.d(TAG, nameofCurrMethod +
                ", JSONArray args " + args
        );

        // Get bounding positions of target element
        JSONObject rect = null;
        try {
            rect = args.getJSONObject(0);
            // Compute its center
            final Integer centerX = rect.getInt("x");
            final Integer centerY = rect.getInt("y");
            // Emulate click
            MainActivity self = this;
            this.runOnUiThread(new Runnable() {
                public void run() {
                    final long uMillis = SystemClock.uptimeMillis();
                    self.dispatchTouchEvent(MotionEvent.obtain(uMillis, uMillis, MotionEvent.ACTION_DOWN, centerX, centerY, 0));
                    self.dispatchTouchEvent(MotionEvent.obtain(uMillis, uMillis, MotionEvent.ACTION_UP, centerX, centerY, 0));

                    // self.appView.getView().dispatchTouchEvent(MotionEvent.obtain(uMillis, uMillis, MotionEvent.ACTION_DOWN, centerX, centerY, 0));
                    // self.appView.getView().dispatchTouchEvent(MotionEvent.obtain(uMillis, uMillis, MotionEvent.ACTION_UP, centerX, centerY, 0));

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

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

    @Override
    public void onResume(){
        String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
        super.onResume();
        MainActivity self = this;
        if(self.isBackground){
            // enviar nuevamente al background
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            self.startActivity(intent);
        }
    }


    public String getScreenshot(Bitmap bitmap, int quality) {
        String out = "";
        try {
            ByteArrayOutputStream jpeg_data = new ByteArrayOutputStream();

            if (bitmap.compress(CompressFormat.JPEG, quality, jpeg_data)) {
                byte[] code = jpeg_data.toByteArray();
                byte[] output = Base64.encode(code, Base64.NO_WRAP);
                String js_out = new String(output);
                js_out = "data:image/jpeg;base64," + js_out;
                out = js_out;
            }

            jpeg_data = null;

        } catch (Exception e) {

        }
        return out;
    }
    @Override
    public void onPageStarted(String url){
        String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();

        this.URL = url;

        MainActivity self = this;

        LOG.d(TAG, nameofCurrMethod + ", url " + this.URL );

        if(this.startFinishLoadPag == true){
            if(url.indexOf("file") != -1){

            }else{
                //App(java) -> socketServer : sendPageStarted
                JSONObject sendPageStarted = new JSONObject();
                try {
                    sendPageStarted.put("socketId", self.dataFW.getString("socketId"));
                    String event = "sendPageStarted";
                    LOG.d(TAG, nameofCurrMethod +
                            ", App(java) -> socketServer : sendPageStarted "
                    );

                    self.getSocket().sendEvent(event, sendPageStarted);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
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

        MainActivity self = this;

        LOG.d(TAG, nameofCurrMethod + ", url " + this.URL );

        if(this.startFinishLoadPag == false){
            //finalizo la carga url local, por primera vez
            countLocal++;

            this.URLList.add(url);
            this.startFinishLoadPag = true;
            this.PageStatus = "f7";

        }else {
            if(url.indexOf("file") != -1){
                //finalizo la carga url local
                this.PageStatus = "f7";
            }else{
                this.PageStatus = "remote";
                //finalizo la carga url remota
                runOnUiThread(new Runnable() {
                    public void run() {
                        // creo un delay, para para lanzar la captura
                        TimerTask task = new TimerTask() {
                            public void run() {

                                cordovaInterface.pluginManager.exec("Screenshot", "saveScreenshot", "", "[\"jpg\",50,\"opraTestScreenShot\"]");
                                LOG.d(TAG, nameofCurrMethod +
                                        ", Screenshot -> saveScreenshot"
                                );

                                // creo un delay, para para recuperar la captura
                                TimerTask taskGetScreenshot = new TimerTask() {
                                    public void run() {
                                        File folder = new File(Environment.getExternalStorageDirectory(), "Pictures");
                                        File file = new File(folder, "opraTestScreenShot.jpg");
                                        FileInputStream streamIn = null;
                                        try {
                                            streamIn = new FileInputStream(file);
                                            Bitmap bitmap = BitmapFactory.decodeStream(streamIn);
                                            streamIn.close();

                                            String Screenshot = self.getScreenshot(bitmap, 50);
                                            JSONObject sendCaptureSocket = new JSONObject();
                                            try {
                                                LOG.d(TAG, nameofCurrMethod +
                                                        ", Screenshot -> saveScreenshot, Screenshot " + Screenshot
                                                );
                                                sendCaptureSocket.put("socketId", self.dataFW.getString("socketId"));
                                                sendCaptureSocket.put("img", Screenshot);
                                                sendCaptureSocket.put("url", self.URL);
                                                String event = "sendCapture";
                                                LOG.d(TAG, nameofCurrMethod +
                                                        ", App(java) -> socketServer : sendCapture "
                                                );

                                                self.getSocket().sendEvent(event, sendCaptureSocket);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } catch (FileNotFoundException e) {
                                            LOG.d(TAG, nameofCurrMethod +
                                                    ", Screenshot -> saveScreenshot, FileNotFoundException :" + e
                                            );
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            LOG.d(TAG, nameofCurrMethod +
                                                    ", Screenshot -> saveScreenshot, IOException"
                                            );
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                long delayGetScreenshot = 1000L;
                                Timer timerGetScreenshot = new Timer("getScreenshot");
                                timerGetScreenshot.schedule(taskGetScreenshot, delayGetScreenshot);

                                taskGetScreenshot = null;
                                timerGetScreenshot = null;

                            }
                        };
                        long delay = 3000L;
                        Timer timer = new Timer("Screenshot");
                        timer.schedule(task, delay);

                        task = null;
                        timer = null;
                    }
                });

            }
        }
    }
}