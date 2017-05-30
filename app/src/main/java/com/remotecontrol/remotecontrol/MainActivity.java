package com.remotecontrol.remotecontrol;

import android.content.Context;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.*;
import java.net.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import android.app.Activity;
import android.os.Bundle;
import android.hardware.SensorEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity implements SensorEventListener
{
    Button btnConnect;
    Button btnSend;
    EditText txtMsg;

    myCursor cursor;

    //-----------------------------------------------------------------------------------------------------variables

    private Socket socket = null;//------------------------socket

    private static final int SERVERPORT = 4000;//------------------------port
    private static final String SERVER_IP = "192.168.78.53";//------------------------computer IP

    private SensorManager senSensorManager; //------------------------sensor variables
    private Sensor senAccelerometer; //------------------------sensor variables
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;

    private boolean if_connected = false;

    //-----------------------------------------------------------------------------------------------------variables

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConnect = (Button)findViewById(R.id.btnConnect);
        btnSend = (Button)findViewById(R.id.btnSend);
        txtMsg = (EditText)findViewById(R.id.txtMessage);

        //---------------------------------------------------------------------------------------------------------------  setting sensors

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);

        //---------------------------------------------------------------------------------------------------------------  end setting sensors

        //---------------------------------------------------------------------------------------------------------------

        btnConnect.setOnClickListener(new View.OnClickListener() //                ---         CONNECT SERVER BUTTON CLICK       ---
        {
            public void onClick(View v)
            {
                new Thread(new ClientThread()).start(); //--------------------------------------  CONNECT TO SERVER

                if_connected = true;
            }
        });

        //---------------------------------------------------------------------------------------------------------------

        btnSend.setOnClickListener(new View.OnClickListener() //                ---         SEND MESSAGE BUTTON CLICK       ---
        {
            public void onClick(View v)
            {
                if (socket != null)
                {
                    try
                    {
                        DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
                        InputStreamReader inupt_stream_reader = new InputStreamReader(socket.getInputStream());
                        BufferedReader buffered_reader= new BufferedReader(inupt_stream_reader);
                        PrintWriter print_writer = new PrintWriter(socket.getOutputStream(),true);

                        EditText et = (EditText) findViewById(R.id.txtMessage); //----------------------getting the value of the text written
                        String msg = et.getText().toString();

                        print_writer.println(msg);                              //----------------------send the server a message

                        Context context = getApplicationContext();
                        Toast toast5 = Toast.makeText(context, "inside try", Toast.LENGTH_LONG);
                        toast5.show();
                    }
                    catch (UnknownHostException e)
                    {
                        Context context = getApplicationContext();
                        Toast toast6 = Toast.makeText(context, "catch  1", Toast.LENGTH_LONG);
                        toast6.show();
                    }
                    catch (IOException e)
                    {
                        Context context = getApplicationContext();
                        Toast toast7 = Toast.makeText(context, "chatch  2", Toast.LENGTH_LONG);
                        toast7.show();
                    }
                }
            }
        });
    }

    public class ClientThread implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVERPORT);
//                os = new DataOutputStream(socket.getOutputStream());
//                is=new DataInputStream(socket.getInputStream());

            }
            catch (UnknownHostException e1)
            {
                e1.printStackTrace();
                Context context2 = getApplicationContext();
                Toast toast5 = Toast.makeText(context2, "problem is os or is", Toast.LENGTH_LONG);
                toast5.show();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
                Context context2 = getApplicationContext();
                Toast toast5 = Toast.makeText(context2, "problem is os or is   2", Toast.LENGTH_LONG);
                toast5.show();
            }
        }
    }

    //---------------------------------------------------------------------------------------------------------- ACCELEROMETER CODE

//    @Override
//    public void onSensorChanged(SensorEvent sensorEvent, myCursor cursor) //POST
//    {
//        Sensor mySensor = sensorEvent.sensor;
//
//        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER)
//        {
//            Float x = sensorEvent.values[0];
//            Float y = sensorEvent.values[1];
//            Float z = sensorEvent.values[2];
//
//            cursor.setX(x);
//            cursor.setY(y);
//            cursor.setZ(z);
//
////            String sx = Double.toString(cursor.getX());
////            String sy = Double.toString(cursor.getY());
////            String sz = Double.toString(cursor.getZ());
////            long curTime = System.currentTimeMillis();
//
//            Float new_z = last_z - z;
//            Float new_y = last_y - y;
//            Float new_x = last_x - x;
//
//            if (if_connected) {
//                try {
//                    //--------------------------------------------------new try
//                    String json = "";
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.accumulate("x", x);
//                    jsonObject.accumulate("y", y);
//                    jsonObject.accumulate("z", z);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }


                //---------------------------------------------------last try
//                    OutputStream os = socket.getOutputStream();
//                    ObjectOutputStream oos = new ObjectOutputStream(os);
//
//                    myCursor c = new myCursor(x,y,z);
//
//                    oos.writeObject(c);
//                    oos.writeObject(new String("next"));
//                    oos.close();
//                    os.close();
//
////                    PrintWriter print_writer = new PrintWriter(socket.getOutputStream(), true);
////                    print_writer.println("x = " + x + " y = " + y + " z = " + z); //---------------------------sending the server a message
//                }
//                catch (IOException e)
//                {
//                    Toast toast5 = Toast.makeText(getApplicationContext(), "problem", Toast.LENGTH_LONG);
//                    toast5.show();
//                }

            //--------------------------------------------------------

//            if ((curTime - lastUpdate) > 100) {
//                long diffTime = (curTime - lastUpdate);
//                lastUpdate = curTime;
//
//                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000; //----------------this is only when shake I need all the time
//
//                if (speed > SHAKE_THRESHOLD){
//                    //-------------------------------move the mouse;
//                    try
//                    {
//                        PrintWriter print_writer = new PrintWriter(socket.getOutputStream(),true);
//                        print_writer.println("x = "+x+" y = "+y+" z = "+z); //---------------------------sending the server a message
//                    }
//                    catch(IOException e)
//                    {
//                        Toast toast5 = Toast.makeText(getApplicationContext(), "problem", Toast.LENGTH_LONG);
//                        toast5.show();
//                    }
//                }
//
//                last_x = x;
//                last_y = y;
//                last_z = z;
//            }

            //--------------------------------------------------------
//        }
//    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            Float x = sensorEvent.values[0];
            Float y = sensorEvent.values[1];
            Float z = sensorEvent.values[2];

            cursor.setX(x);
            cursor.setY(y);
            cursor.setZ(z);

            Float new_z = last_z - z;
            Float new_y = last_y - y;
            Float new_x = last_x - x;

            if (if_connected)
            {
                String json = "";
                JSONObject jsonObject = new JSONObject();
                try
                {
                    //--------------------------------------------------new try
                    jsonObject.put("x", x);
                    jsonObject.put("y", y);
                    jsonObject.put("z", z);

                    
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    protected void onPause()
    {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }
    protected void onResume()
    {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

}
