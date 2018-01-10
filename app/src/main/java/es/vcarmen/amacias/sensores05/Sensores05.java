package es.vcarmen.amacias.sensores05;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Sensores05 extends AppCompatActivity implements SensorEventListener
{
    private int contador;
    private double azimut, vertical, lateral;
    private String orientacion;
    private TextView tvAz, tvVe, tvLa, tvOr, tvC;
    private SensorManager mySensorManager;
    private Sensor orienta;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensores05);

        // Enlazamos los elementos del layout con su definición
        tvAz = findViewById( R.id.azimut);
        tvVe = findViewById( R.id.vertical);
        tvLa = findViewById( R.id.lateral);
        tvOr = findViewById( R.id.orientacion);
        tvC  = findViewById( R.id.contador );

        // Inicializamos nuestro gestor de sensores como servicio
        mySensorManager = ( SensorManager ) getSystemService( Context.SENSOR_SERVICE );
        // Definimos un sensor de orientación y con nuestro gestor le asignamos su tipo de sensor
        orienta = mySensorManager.getDefaultSensor( Sensor.TYPE_ORIENTATION );
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mySensorManager.registerListener( this, orienta, SensorManager.SENSOR_DELAY_NORMAL );
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mySensorManager.unregisterListener( this );
    }

    class CambiaTexto implements Runnable
    {
        @Override
        public void run()
        {
            tvAz.setText( "" + azimut );
            tvVe.setText( "" + vertical );
            tvLa.setText( "" + lateral );
            tvOr.setText( "" + orientacion );
            tvC .setText( "" + contador );
        }
    }

    @Override
    public void onSensorChanged( SensorEvent event )
    {
        azimut   = event.values[ 0 ];
        vertical = event.values[ 1 ];
        lateral  = event.values[ 2 ];

        contador ++;

        // posicionamiento( azimut, vertical, lateral );

        // azimut
        if      ( azimut <  22 ) orientacion = "NORTE";
        else if ( azimut <  67 ) orientacion = "NORESTE";
        else if ( azimut < 112 ) orientacion = "ESTE";
        else if ( azimut < 157 ) orientacion = "SURESTE";
        else if ( azimut < 202 ) orientacion = "SUR";
        else if ( azimut < 247 ) orientacion = "SUROESTE";
        else if ( azimut < 292 ) orientacion = "OESTE";
        else if ( azimut < 337 ) orientacion = "NOROESTE";
        else                     orientacion = "NORTE";

        // vertical
        if ( vertical < -50 )    orientacion = "VERTICAL ARRIBA";
        if ( vertical >  50 )    orientacion = "VERTICAL ABAJO";

        // lateral
        if ( vertical < -50 )    orientacion = "LATERAL DERECHA";
        if ( vertical >  50 )    orientacion = "LATERAL IZQUIERDA";

        runOnUiThread( new CambiaTexto() );
    }


    @Override
    public void onAccuracyChanged( Sensor sensor, int accuracy )
    {

    }

}