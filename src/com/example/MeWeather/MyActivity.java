package com.example.MeWeather;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import org.apache.http.client.methods.HttpHead;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     * ViruZ
     */
    String urlCode;
    Spinner spin;
    TextView tim, weather, dir, temp, vlaga, nuton, wind;
    ImageView imaga;
    int time, num;
    String tur;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        spin = (Spinner) findViewById(R.id.cities);
        temp = (TextView) findViewById(R.id.temp);
        vlaga = (TextView) findViewById(R.id.vlaga);
        dir = (TextView) findViewById(R.id.dir);
        nuton = (TextView) findViewById(R.id.nuton);
        wind = (TextView) findViewById(R.id.ws);
        imaga = (ImageView) findViewById(R.id.image);
        weather = (TextView) findViewById(R.id.weather);
        tim = (TextView) findViewById(R.id.time);
        final String[] citiesName = {"Moscow", "Leningrad", "Omsk", "Kazan", "Kaliningrad"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, citiesName);

        spin.setAdapter(adapter);
        spin.setSelection(0);
        spin.setPrompt("City");
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Intent intent = new Intent(MyActivity.this, MyIntentService.class);
                //intent.putExtra("exTown", townesEn[position]);
                //startService(intent);
                String thisCity = citiesName[position];
                num = position;
                urlCode = "http://api.worldweatheronline.com/free/v1/weather.ashx?q=" + thisCity +"&format=xml&num_of_days=5&fx=no&includelocation=yes&key=vzteyw87pmfy69jwu6qg958n";
                new DownLoad().execute(urlCode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }


        });

    }
    URL url;
    class Weather{
        public String temp;
        String wind;
        String vlaga;
        String nuton;
        String vector;
        int imgCode;
        String timec;
    }

    class DownLoad extends AsyncTask<String, Void, Weather> {
        @Override
        protected Weather doInBackground(String... params) {
            try {
                url = new URL(urlCode);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();
                Weather weather = new Weather();
                NodeList nodeList = doc.getElementsByTagName("temp_C");
                weather.temp = nodeList.item(0).getFirstChild().getNodeValue();
                nodeList = doc.getElementsByTagName("windspeedKmph");
                weather.wind = nodeList.item(0).getFirstChild().getNodeValue();
                nodeList = doc.getElementsByTagName("winddir16Point");
                weather.vector = nodeList.item(0).getFirstChild().getNodeValue();
                nodeList = doc.getElementsByTagName("humidity");
                weather.vlaga= nodeList.item(0).getFirstChild().getNodeValue();
                nodeList = doc.getElementsByTagName("pressure");
                weather.nuton = nodeList.item(0).getFirstChild().getNodeValue();
                nodeList = doc.getElementsByTagName("weatherCode");
                weather.imgCode = Integer.parseInt(nodeList.item(0).getFirstChild().getNodeValue());
                nodeList = doc.getElementsByTagName("observation_time");
                weather.timec = nodeList.item(0).getFirstChild().getNodeValue();
                return weather;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(Weather result) {
            temp.setText("Temperature: " + result.temp + "°C");
            vlaga.setText("Humidity: " + result.vlaga + "%");
            wind.setText("Wind speed: " + result.wind + "Km/h");
            dir.setText("Direction: " + result.vector);
            nuton.setText("Pressure: " + result.nuton + " mb");
            String weath = null;
            Integer snow = R.drawable.snow;
            Integer clear = R.drawable.clear;
            Integer fair = R.drawable.fair;
            Integer foggy = R.drawable.foggy;
            Integer hot = R.drawable.hot;
            Integer rain = R.drawable.rain;
            Integer thunder = R.drawable.thunder;
            Integer cloudy = R.drawable.cloudy;
            Integer cloudypart = R.drawable.cloudypart;
            Integer rainyfair = R.drawable.fairrain;
            Integer thunderfair = R.drawable.thunderfair;
            Integer hell = R.drawable.hail;
            Integer heavyrain = R.drawable.heavyrain;
            Integer snowcloud = R.drawable.snowcloudy;
            Integer snowfair = R.drawable.snowfair;
            Integer rainysnow = R.drawable.rainysnow;
            Integer clearnight = R.drawable.nightclear;
            Integer snownight = R.drawable.nightsnow;
            Integer cloudynight = R.drawable.nightcloudy;
            Integer nightrain = R.drawable.nightrain;
            Integer nightsnow = R.drawable.nightsnow;
            Integer nightthunder = R.drawable.nightthunder;

            Integer day = null;
            Integer night = null;

            switch(result.imgCode){
                case 395:
                    weath = "Moderate or heavy snow in area with thunder";
                    day = snow;
                    night = snownight;
                    break;
                case 392:
                    weath = "Patchy light snow in area with thunder";
                    day = snow;
                    night = snownight;
                    break;
                case 389:
                    weath = "Moderate or heavy rain in area with thunder";
                    day = rain;
                    night = nightrain;
                    break;
                case 386:
                    weath = "Patchy light rain in area with thunder";
                    day = thunder;
                    night = nightthunder;
                    break;
                case 377:
                    weath = "Moderate or heavy showers of ice pellets";
                    day = hell;
                    night = day;
                    break;
                case 374:
                    weath = "Light showers of ice pellets";
                    day = hell;
                    night = hell;
                    break;
                case 371:
                    weath = "Moderate or heavy snow showers";
                    day = snow;
                    night = snownight;
                    break;
                case 368:
                    weath = "Light snow showers";
                    day = snow;
                    night = snownight;
                    break;
                case 365:
                    weath = "Moderate or heavy sleet showers";
                    day = rainysnow;
                    night = day;
                    break;
                case 362:
                    weath = "Light sleet showers";
                    day = rainysnow;
                    night = day;
                    break;
                case 359:
                    weath = "Torrential rain shower";
                    day = rain;
                    night = nightrain;
                    break;
                case 356:
                    weath = "Moderate or heavy rain shower";
                    day = rain;
                    night = day;
                    break;
                case 353:
                    weath = "Light rain shower";
                    day = rain;
                    night = day;
                    break;
                case 350:
                    weath = "Ice pellets";
                    day = hell;
                    night = hell;
                    break;
                case 338:
                    weath = "Heavy snow";
                    day = snow;
                    night = snownight;
                    break;
                case 335:
                    weath = "Patchy heavy snow";
                    day = snow;
                    night = snownight;
                    break;
                case 332:
                    weath = "Moderate snow";
                    day = snow;
                    night = snownight;
                    break;
                case 329:
                    weath = "Patchy moderate snow";
                    day = snow;
                    night = nightsnow;
                    break;
                case 326:
                    weath = "Light snow";
                    day = snow;
                    night = snownight;
                    break;
                case 323:
                    weath = "Patchy light snow";
                    day = snow;
                    night = snownight;
                    break;
                case 320:
                    weath = "Moderate or heavy sleet";
                    day = rainysnow;
                    night = rainysnow;
                    break;
                case 317:
                    weath = "Light sleet";
                    day = rainysnow;
                    night = day;
                    break;
                case 314:
                    weath = "Moderate or Heavy freezing rain";
                    day = heavyrain;
                    night = nightrain;
                    break;
                case 311:
                    weath = "Light freezing rain";
                    day = snow;
                    night = nightsnow;
                    break;
                case 308:
                    weath = "Heavy rain";
                    day = heavyrain;
                    night = nightrain;
                    break;
                case 305:
                    weath = "Heavy rain at times";
                    day = heavyrain;
                    night = nightrain;
                    break;
                case 302:
                    weath = "Moderate rain";
                    day = rain;
                    night = nightrain;
                    break;
                case 299:
                    weath = "Moderate rain at times";
                    day = rain;
                    night = nightrain;
                    break;
                case 296:
                    weath = "Light rain";
                    day = rain;
                    night = nightrain;
                    break;
                case 293:
                    weath = "Patchy light rain";
                    day = rain;
                    night = nightrain;
                    break;
                case 284:
                    weath = "Heavy freezing drizzle";
                    day = rain;
                    night = nightrain;
                    break;
                case 281:
                    weath = "Freezing drizzle";
                    day = rain;
                    night = nightrain;
                    break;
                case 266:
                    weath = "Light drizzle";
                    day = rain;
                    night = nightrain;
                    break;
                case 263:
                    weath = "Patchy light drizzle";
                    day = rain;
                    night = nightrain;
                    break;
                case 260:
                    weath = "Freezing fog";
                    day = foggy;
                    night = foggy;
                    break;
                case 248:
                    weath = "Fog";
                    day = foggy;
                    night = day;
                    break;
                case 230:
                    weath = "Blizzard";
                    day = snow;
                    night = snownight;
                    break;
                case 227:
                    weath = "Blowing snow";
                    day = snow;
                    night = snow;
                    break;
                case 200:
                    weath = "Thundery outbreaks in nearby";
                    day = thunder;
                    night = nightthunder;
                    break;
                case 185:
                    weath = "Patchy freezing drizzle nearby";
                    day = rainyfair;
                    night = nightrain;
                    break;
                case 182:
                    weath = "Patchy sleet nearby";
                    day = rainysnow;
                    night = nightrain;
                    break;
                case 179:
                    weath = "Patchy snow nearby";
                    day = snow;
                    night = snownight;
                    break;
                case 176:
                    weath = "Patchy rain nearby";
                    day = rain;
                    night = nightrain;
                    break;
                case 143:
                    weath = "Mist";
                    day = cloudy;
                    night = cloudynight;
                    break;
                case 122:
                    weath = "Overcast";
                    day = cloudy;
                    night = cloudynight;
                    break;
                case 119:
                    weath = "Cloudy";
                    day = cloudy;
                    night = cloudy;
                    break;
                case 116:
                    weath = "Partly Cloudy";
                    day = cloudypart;
                    night = cloudynight;
                    break;
                case 113:
                    weath = "Clear/Sunny";
                    day = clear;
                    night = clearnight;
                    break;
            }
            tur = result.timec;
            time = Integer.parseInt(String.valueOf(tur.charAt(0)) + String.valueOf(tur.charAt(1)));
            switch (num){
                case 0:
                    time += 4;
                    break;
                case 1:
                    time += 4;
                    break;
                case 2:
                    time += 7;
                    break;
                case 3:
                    time += 4;
                    break;
                case 4:
                    time += 3;
                    break;
            }


            String gr = tur;

            tur = String.valueOf(time) + String.valueOf(tur.charAt(2)) + String.valueOf(tur.charAt(3)) + String.valueOf(tur.charAt(4)) + String.valueOf(tur.charAt(5)) + String.valueOf(tur.charAt(6)) + String.valueOf(tur.charAt(7));
            tim.setText(tur);
            tur = String.valueOf(gr.charAt(6)) + String.valueOf(gr.charAt(7));
            if ((time >= 9 && tur == "PM") || (time <= 6 && tur == "AM")){
                imaga.setImageResource(night);
            } else {
                imaga.setImageResource(day);
            }
            weather.setText(weath);


        }
    }
}
