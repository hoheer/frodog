package vos;

import java.io.*;
import java.net.*;


import com.google.gson.*;
import com.google.gson.stream.JsonReader;

//카카오 REST API 가이드라인 참고
public class Main {
	  static boolean expired;  //엑세스 토큰의 갱신 만료 여부 체크
	  public static String get_auth_header(String grant_type, String client_id, String client_secret ) {
		
		  String access_token =  null;
		  //String refresh_token =  "";
	      String token_type = null;
	      Gson gson = new Gson();	      
	      File f = new File("oauth.json");
	      	 	        	       
	        if (f.exists()) {	//기존에 발급받은 토큰이 존재하는 경우       	
	            System.out.println("이전 토큰 유효성 검사중입니다...");
	            
	            try {
	            	token auth = new token();
	            	//파싱할 token클래스 객체 생성
	                JsonReader jsonReader = new JsonReader(new FileReader("oauth.json"));
	                
	                JsonObject jsonobject = new JsonObject();
	                JsonParser jsonParser = new JsonParser();
	                JsonElement element = jsonParser.parse(new FileReader("oauth.json"));
	                jsonobject = element.getAsJsonObject();
	                	                	          	             	    	             
	                auth = gson.fromJson(jsonReader, token.class);  //파싱 받아옴
	               
	                
	                long now = System.currentTimeMillis(); //토큰 유효성 검사에 필요한 현재 시간 값 생성	               
	                if( now > auth.getExpires()) {
	                	 expired = true;//토큰 내 만기일과 현제 시간값 비교 
	                }
	                else expired = false;
	                
	                
	                if(expired==false) { //토큰이 만료되지 않은 경우
	                	access_token = auth.getAccess_token(); //기존 access_token값을 그대로 사용
	                	token_type = auth.getToken_type();
	                	System.out.println("기존 토큰이 유효합니다");
	                }	
	                
	            } catch (Exception e) {
	                e.getMessage();
	            }	            	            	            	           
	        	}
	        	     	        
	        boolean request_new = false;
            if(access_token==null&&token_type==null) { //기존 토큰에서 정상적으로 access_token 값과 token_type값을 가져오지 못했을 경우 새로 토큰을 발급받는다
                request_new = true;
            }

            if(request_new) {
            	//expired = true;
                System.out.println("기존 토큰이 만료되어 새로 발급받습니다....");                                             
                String reqURL = "https://api.avatarsdk.com/o/token/";  
                FileWriter writer = null;
                Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
                token rsp = new token(); //파싱을 위한 토큰 객체 생성
                
                try {
                	                	                	                	
                	URL url = new URL(reqURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setRequestMethod("POST");  //전송타입 post
                    conn.setDoOutput(true);
                    
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                    StringBuilder sb = new StringBuilder();
                    
                    sb.append("grant_type="+grant_type);
                    sb.append("&client_id="+client_id);                   
                    sb.append("&client_secret="+client_secret);  
                    //전송할 파라미터 값 설정
                   
                    
                    bw.write(sb.toString());
                    bw.flush(); //전송
                    
                    
                    int responseCode = conn.getResponseCode();
                    System.out.println("responseCode : " + responseCode); 
                    
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = "";
                    String result = "";
                    
                    while ((line = br.readLine()) != null) {
                        result += line; //result에 받아온 값을 하나의 string으로 정리
                    }                  
                                     
                    JsonParser parser = new JsonParser();
                    JsonElement element = parser.parse(result);
                    
                    String json = gson2.toJson(element);
                                       
                    rsp= new Gson().fromJson(json, token.class);
                     
                    access_token = rsp.getAccess_token();
                    token_type = rsp.getToken_type();
                    
                    long now = System.currentTimeMillis();                                                                             
                    rsp.setExpires(now+(rsp.getExpires_in()-60)*1000);//토큰 만료여부 체크에 필요한 만기시간 설정                   
                                                            
                    String create_as_file =  gson2.toJson(rsp); 
                    
                    try {
                    	//File file = new File("oauth.json");                    	
                        writer = new FileWriter("oauth.json");
                        writer.write(create_as_file);
                    } catch (IOException e) {
                        
                        e.printStackTrace();
                    }finally {
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }                                
                    br.close();
                    bw.close();                	                	                	                	                          	            
        	        }                	                	                                                                       
                catch (IOException e) {                    
                    e.printStackTrace();                    
                }                              
	  }
           String headers = "{\"Authorization\":" + "\""+token_type+" "+access_token+"\""+"}" ; 
           //반환되는 헤더값은 json양식으로 그 내용이 적혀있어야 함
           return headers;             
	  }
	  
public static String get_player_uid_header (String header) {
	String player_uid = null;
	

	File f = new File("player.json");
	Gson gson = new Gson();  
    if (f.exists()) { //기존 player.json 파일이 존재하는 경우
    	System.out.println("기존 저장된 PlayerUID를 불러옵니다");
   	
      try {    	
    	player player = new player();
    	   	
        JsonReader jsonReader = new JsonReader(new FileReader("player.json"));            
        JsonParser jsonParser = new JsonParser();
        //JsonElement element = jsonParser.parse(new FileReader("player.json"));      
              	                	          	             	    	             
        player = gson.fromJson(jsonReader, player.class);        
        player_uid = player.getCode(); 
        //기존 파일이 존재하는 경우  player_uid 값을 재사용함
    	} 
    	catch (Exception e) {e.getMessage();}                        	    	
    }
    
    if (player_uid == null||expired==true) { //player_uid 값이 존재하지 않는 경우 새로 발급 받는다
    	
    	
    	System.out.println("새로운 player를 생성합니다...");    	    	
    	//String player_form = "test_py";
    	
        String reqURL = "https://api.avatarsdk.com/players/";  
        FileWriter writer = null;
        Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
        player rsp = new player();
                       
        try {

        	header param = gson2.fromJson(header,header.class);
        	//헤더에서 authorization값을 뽑아오기 위해 헤더 객체를 생성하고 파싱한다        	
        	URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", param.getAuthorization());
            //uid 발급에 필요한 파라미터 값 전송
            
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            //result에 결과값 모두 저장
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            String json = gson2.toJson(element);
            
            rsp= new Gson().fromJson(json, player.class);  
            //player 클래스로 받아온 json값을 파싱
            player_uid = rsp.getCode(); //uid값 결정
             
           System.out.println("PlayerUID를 파일로 저장합니다");
           String written_to_file =  gson2.toJson(rsp);
           try {
           	//File file = new File("player.json");           	
            writer = new FileWriter("player.json");
            writer.write(written_to_file);
            //player.json 파일 작성
           } catch (IOException e) {               
               e.printStackTrace();
           }finally {
               try {
                   writer.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
	        }                	                	                                                                       
       catch (IOException e) {                    
           e.printStackTrace();                    
       }                                                                                                  	
        }    
         return "{\"X-PlayerUID\":" + "\""+player_uid+"\"}" ; 	
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AUTH_FORM AUTHFORM = new AUTH_FORM();	
		String header = get_auth_header(AUTHFORM.getGrant_type(),AUTHFORM.getClient_id(),AUTHFORM.getClient_secret());
		System.out.println(header);
		
		String player = get_player_uid_header(header);		
		System.out.println(player);
		
						   		 		  		   		 		   		   
	}

}
