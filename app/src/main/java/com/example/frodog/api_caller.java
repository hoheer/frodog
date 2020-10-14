package com.example.frodog;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class api_caller extends AsyncTask<Void,Void, String> {
    private String url;

    public api_caller(String url) throws IOException {
        this.url = url;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        // parsing할 url 지정(API 키 포함해서)
        String result = null;
        DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactoty.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = dBuilder.parse(url);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
        if(doc != null) {
            try {
                // root tag
                doc.getDocumentElement().normalize();
                System.out.println("Root element: " + doc.getDocumentElement().getNodeName()); // Root element: result
            } catch (NullPointerException E) {

            }


            // 파싱할 tag
            NodeList nList = doc.getElementsByTagName("item");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    //Log.d("OPEN_API", "data Time  : " + getTagValue("dataTime", eElement));
                    // Log.d("OPEN_API", "미세먼지  : " + getTagValue("pm10Value", eElement));
                    // Log.d("OPEN_API", "초미세먼지 : " + getTagValue("pm25Value", eElement));
                    result = getTagValue("pm25Value", eElement) + getTagValue("pm10Value", eElement);
                }    // for end
            }
        }// if end
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }


    private String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
    }



}

