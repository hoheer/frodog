package com.example.frodog;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;



public class dogWeather extends AppCompatActivity {
    String rs = "";
    String rs2 = "";
    String[] wrs = null;



    private GpsTracker gpsTracker;

    public static int TO_GRID = 0;
    public static int TO_GPS = 1;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogweather);
        gpsTracker = new GpsTracker(dogWeather.this);
        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();


        LatXLngY tmp = convertGRID_GPS(TO_GRID, gpsTracker.getLatitude(), gpsTracker.getLongitude());




        Geocoder gCoder = new Geocoder(this, Locale.getDefault());
        List<Address> addr = null;

        try {
            addr = gCoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address a = addr.get(0);
        String add =  a.getAdminArea()+" "+a.getSubLocality()+" "+a.getThoroughfare();
        // add에 현재 위치 저장

        try {
            StringBuilder urlBuilder = new StringBuilder("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=ARIEwNB4jCcVbsbYD8nmaEF%2FI7kzcl8NIclBLC0VSz4SzEW2bdNCk1veYA20vsPmjafWnLDxyDP5jzXItm6HqA%3D%3D"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
            urlBuilder.append("&" + URLEncoder.encode("stationName", "UTF-8") + "=" + URLEncoder.encode("종로구", "UTF-8")); /*측정소 이름*/
            urlBuilder.append("&" + URLEncoder.encode("dataTerm", "UTF-8") + "=" + URLEncoder.encode("DAILY", "UTF-8")); /*요청 데이터기간 (하루 : DAILY, 한달 : MONTH, 3달 : 3MONTH)*/
            urlBuilder.append("&" + URLEncoder.encode("ver", "UTF-8") + "=" + URLEncoder.encode("1.3", "UTF-8")); /*버전별 상세 결과 참고문서 참조*/
            URL url2 = new URL(urlBuilder.toString());

            String url = url2.toString();

            try {
                api_caller dust = new api_caller(url);
                rs = dust.execute().get();
            }
            catch (Exception e) {}
        }
        catch (IOException k) {}





        Date today = new Date();
        SimpleDateFormat format1;
        format1 = new SimpleDateFormat("yyyyMMdd");


        int i =Integer.parseInt(String.valueOf(Math.round(tmp.x)));
        int j =Integer.parseInt(String.valueOf(Math.round(tmp.y)));
        String lat = Integer.toString(i);
        String lon =Integer.toString(j);

        Calendar cal = Calendar.getInstance();
        String base_date = format1.format(cal.getTime());

        String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?serviceKey=ARIEwNB4jCcVbsbYD8nmaEF%2FI7kzcl8NIclBLC0VSz4SzEW2bdNCk1veYA20vsPmjafWnLDxyDP5jzXItm6HqA%3D%3D&numOfRows=10&pageNo=1&base_date="+base_date+"&base_time=0830&nx="+lat+"&ny="+lon;

        try {
            api_caller2 weather = new api_caller2(url);
            rs2 = weather.execute().get();
            wrs = rs2.split(" ");
            Log.d("OPEN_API", "lom : " + lon);
            Log.d("OPEN_API", "lat : " + lat);
            Log.d("OPEN_API", "rs2  : " + rs2);
            Log.d("OPEN_API", "rs2  : " + wrs[7]);


        }
        catch (Exception e) {}



















        TextView dust = findViewById(R.id.fine_dust);
        TextView fine_dust = findViewById(R.id.fime_dust_matter);
        TextView gps = findViewById(R.id.gps);
        String[] arrword = rs.split("");

        TextView humid = findViewById(R.id.hum1);
        TextView temp = findViewById(R.id.temp1);

        humid.setText(wrs[7]);
        temp.setText(wrs[13]);

        gps.setText(add);
        fine_dust.setText(arrword[0]+arrword[1]);
        dust.setText(arrword[2]+arrword[3]);
        ActionBar actionBar =getSupportActionBar();
        actionBar.setTitle("외출");
    }




    private LatXLngY convertGRID_GPS(int mode, double lat_X, double lng_Y )
    {
        double RE = 6371.00877; // 지구 반경(km)
        double GRID = 5.0; // 격자 간격(km)
        double SLAT1 = 30.0; // 투영 위도1(degree)
        double SLAT2 = 60.0; // 투영 위도2(degree)
        double OLON = 126.0; // 기준점 경도(degree)
        double OLAT = 38.0; // 기준점 위도(degree)
        double XO = 43; // 기준점 X좌표(GRID)
        double YO = 136; // 기1준점 Y좌표(GRID)

       


        double DEGRAD = Math.PI / 180.0;
        double RADDEG = 180.0 / Math.PI;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);
        LatXLngY rs3 = new LatXLngY();

        if (mode == TO_GRID) {
            rs3.lat = lat_X;
            rs3.lng = lng_Y;
            double ra = Math.tan(Math.PI * 0.25 + (lat_X) * DEGRAD * 0.5);
            ra = re * sf / Math.pow(ra, sn);
            double theta = lng_Y * DEGRAD - olon;
            if (theta > Math.PI) theta -= 2.0 * Math.PI;
            if (theta < -Math.PI) theta += 2.0 * Math.PI;
            theta *= sn;
            rs3.x = Math.floor(ra * Math.sin(theta) + XO + 0.5);
            rs3.y = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
        }
        else {
            rs3.x = lat_X;
            rs3.y = lng_Y;
            double xn = lat_X - XO;
            double yn = ro - lng_Y + YO;
            double ra = Math.sqrt(xn * xn + yn * yn);
            if (sn < 0.0) {
                ra = -ra;
            }
            double alat = Math.pow((re * sf / ra), (1.0 / sn));
            alat = 2.0 * Math.atan(alat) - Math.PI * 0.5;

            double theta = 0.0;
            if (Math.abs(xn) <= 0.0) {
                theta = 0.0;
            }
            else {
                if (Math.abs(yn) <= 0.0) {
                    theta = Math.PI * 0.5;
                    if (xn < 0.0) {
                        theta = -theta;
                    }
                }
                else theta = Math.atan2(xn, yn);
            }
            double alon = theta / sn + olon;
            rs3.lat = alat * RADDEG;
            rs3.lng = alon * RADDEG;
        }
        return rs3;
    }



    static class LatXLngY
    {
        public double lat;
        public double lng;

        public double x ;
        public double y ;

    }


}