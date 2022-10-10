package in.codersclub.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btn_login,btn_otp;
    EditText name,otp,phoneNo;
    int randomNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=(EditText) findViewById(R.id.name);
        otp=(EditText) findViewById(R.id.otp);
        phoneNo=(EditText) findViewById(R.id.phoneNo);
        btn_login=(Button) findViewById(R.id.btn_login);
        btn_otp=(Button) findViewById(R.id.btn_otp);
        StrictMode.ThreadPolicy policy =new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Construct data
                    String apiKey = "apikey=" + "MzM2MjQ2MzM2MjcyMzQzNzcyNDk3Mzc0NDE2ZTY3NzY=";
                    Random random= new Random();
                    randomNumber=random.nextInt(999999);
                    String message = "&message=" + "Hey"+name.getText().toString()+"Your OTP is "+randomNumber;
                    String sender = "&sender=" + "ANA";
                    String numbers = "&numbers=" + phoneNo.getText().toString();

                    // Send data
                    HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
                    String data = apiKey + numbers + message + sender;
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
                    conn.getOutputStream().write(data.getBytes("UTF-8"));
                    final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    final StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        stringBuffer.append(line);
                    }
                    rd.close();
                    Toast.makeText(getApplicationContext(),"otp sent successfully",Toast.LENGTH_LONG).show();
                   // return stringBuffer.toString();
                } catch (Exception e) {
                    //System.out.println("Error SMS "+e);
                    //return "Error "+e;
                    Toast.makeText(getApplicationContext(),"ERROR SMS" + e,Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),"ERROR"+e,Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(randomNumber==Integer.valueOf(otp.getText().toString())){
                    Toast.makeText(getApplicationContext(),"You are logged in succesfully",Toast.LENGTH_LONG).show();

                }
                Toast.makeText(getApplicationContext(),"wrong otp",Toast.LENGTH_LONG).show();
            }
        });

    }
}