package things.android.estudo.com.jogowithandroidthings;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String GPIO_PIN_LED_1 = "IO5";
    private Gpio mLedGpio1;

    private static final String GPIO_PIN_LED_2 = "IO6";
    private Gpio mLedGpio2;

    private static final String GPIO_PIN_LED_3 = "IO7";
    private Gpio mLedGpio3;

    private static final String GPIO_PIN_LED_4 = "IO8";
    private Gpio mLedGpio4;

    private static final String GPIO_PIN_LED_5 = "IO9";
    private Gpio mLedGpio5;

    private static final String GPIO_PIN_LED_6 = "IO10";
    private Gpio mLedGpio6;

    private static final String GPIO_PIN_LED_7 = "IO11";
    private Gpio mLedGpio7;

    private static final String GPIO_PIN_LED_8 = "IO12";
    private Gpio mLedGpio8;

    private Gpio[] gpios = new Gpio[8];

    private final String TAG = "LEDS";

    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        PeripheralManagerService service = new PeripheralManagerService();
        Log.d(TAG, "Available GPIO: " + service.getGpioList());

        try {
            mLedGpio1 = service.openGpio(GPIO_PIN_LED_1);
            mLedGpio1.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

            mLedGpio2 = service.openGpio(GPIO_PIN_LED_2);
            mLedGpio2.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

            mLedGpio3 = service.openGpio(GPIO_PIN_LED_3);
            mLedGpio3.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

            mLedGpio4 = service.openGpio(GPIO_PIN_LED_4);
            mLedGpio4.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

            mLedGpio5 = service.openGpio(GPIO_PIN_LED_5);
            mLedGpio5.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

            mLedGpio6 = service.openGpio(GPIO_PIN_LED_6);
            mLedGpio6.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

            mLedGpio7 = service.openGpio(GPIO_PIN_LED_7);
            mLedGpio7.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

            mLedGpio8 = service.openGpio(GPIO_PIN_LED_8);
            mLedGpio8.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

            gpios = new Gpio[]{mLedGpio1, mLedGpio2, mLedGpio3, mLedGpio4, mLedGpio5, mLedGpio6 ,mLedGpio7, mLedGpio8};
        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        }

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Media media = dataSnapshot.getValue(Media.class);
                Log.e(TAG, "sucesso ... " + media.media);

                try {
                    mLedGpio1.setValue(false);
                    mLedGpio2.setValue(false);
                    mLedGpio3.setValue(false);
                    mLedGpio4.setValue(false);
                    mLedGpio5.setValue(false);
                    mLedGpio6.setValue(false);
                    mLedGpio7.setValue(false);
                    mLedGpio8.setValue(false);

                    for (int i = 0; i < media.media; i++){
                        gpios[i].setValue(true);
                    }
                } catch (IOException e){
                    Log.e(TAG, "IOException ... " + e.getLocalizedMessage());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        myRef.addValueEventListener(postListener);
    }
}
