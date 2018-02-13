package fu.jiunhau.idv.sensorexdemo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int sensorCount;
    private SensorEventListener sensorEventListener;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void  initSenser(){
        sensorCount = 0;
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] values = event.values;
                int absintvalueX = (int) Math.abs(values[0]);
                sensorCount += absintvalueX;
                if (sensorCount > 600) {
                    senserEvent(true);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    public void stopSensor(){
        sensorManager.unregisterListener(sensorEventListener);
        senserEvent(false);
    }

    public void senserEvent(boolean sensor){
        if (sensor){
            Toast.makeText(getApplication(),"搖動成功",Toast.LENGTH_LONG).show();
            stopSensor();
        }else{
            Toast.makeText(getApplication(),"感測器中止",Toast.LENGTH_LONG).show();
        }
        sensorCount = 0;
    }

    public void onSenserStartClick(View view){
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        initSenser();
        boolean enable = sensorManager.registerListener(
                sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        if (!enable) {
            stopSensor();
        }
    }
}
