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
import android.util.Base64;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.*;
import java.net.URISyntaxException;
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
import android.provider.MediaStore;
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
                                    LocalBroadcastManager.getInstance(self).sendBroadcastSync(onDataModuleJava);
                                    LOG.d(TAG, nameofCurrMethod +
                                            ", App(java) -> f7 : sendConfigProcessUrlSocket"
                                    );
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

                                        for (int i=0; i < listaDeEventos.length(); i++) {
                                            JSONObject currentEvent = listaDeEventos.getJSONObject(i);
                                            Integer x = currentEvent.getInt("x");
                                            Integer y = currentEvent.getInt("y");
                                            String text = currentEvent.getString("data");

                                            JSONObject ParamFocus = new JSONObject();

                                            ParamFocus.put("top", y - 50);
                                            ParamFocus.put("left", x - 50);
                                            ParamFocus.put("right", x);
                                            ParamFocus.put("bottom", y);

                                            // realizamos toch
                                            cordovaInterface.pluginManager.exec("Focus", "focus", "", "[" + ParamFocus.toString() + "]");

                                            Integer w = self.appView.getView().getWidth();
                                            Integer h = self.appView.getView().getHeight();
                                            LOG.d(TAG, ", appView getWidth : " + w + ", appView getHeight : " + h);

                                            if(text.length() > 0){

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


                                            }else{
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

                                            LOG.d(TAG, nameofCurrMethod +
                                                ", Event ( " + i + " )" +
                                                ", x ( " + x + " )" +
                                                ", y ( " + y + " )" +
                                                ", text ( " + text + " )"
                                            );
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }catch (Exception e){
                            LOG.d(TAG, nameofCurrMethod +
                                    ", catch socket.on(sendClient, disconnect, sendMessage, sendClients, sendTypingMessage, sendConfigProcessUrlSocket) : " + e
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
            this.PageStatus = "finalizo la carga local";

        }else {
            if(url.indexOf("file") != -1){
                //finalizo la carga url local

            }else{
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
                                                sendCaptureSocket.put("socketId", self.dataFW.getString("socketId"));
                                                sendCaptureSocket.put("img", Screenshot);
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

