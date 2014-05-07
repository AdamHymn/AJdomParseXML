package com.main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class Main {

	public static void main(String[] args) {
		parseMainXml("http://192.168.1.14:8888/palmcity/public/main.xml");
	}
	
	public static void parseMainXml(String path){
		try {
			URL url = new URL(path);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setDoInput(true);
			urlConnection.setConnectTimeout(10000);
			
			SAXBuilder saxBuilder = new SAXBuilder(false);
			Document doc = saxBuilder.build(url.openStream());
			Element root = doc.getRootElement();
			Element os = root.getChild("os");
			List list = os.getChildren("o");
			for (int i = 0; i < list.size(); i++) {
				Element oEl = (Element) list.get(i);
				String title = oEl.getChildText("t");
				String frontImage = oEl.getChildText("fi");
				String backImage = oEl.getChildText("bc");
				System.out.println("-oel-"+i+"-->"+title);
				System.out.println("-oel-"+i+"-->"+frontImage);
				System.out.println("-oel-"+i+"-->"+backImage);
				Element dsEl = oEl.getChild("ds");
				List dList = dsEl.getChildren("d");
				for (int j = 0; j < dList.size(); j++) {
					Element dEl = (Element) dList.get(j);
					String childTitle = dEl.getChildText("t");
					System.out.println("--childTitle-->"+childTitle);
					Element isEl = dEl.getChild("is");
					List iList = isEl.getChildren("i");
					for (int k = 0; k < iList.size(); k++) {
						Element iEl = (Element) iList.get(k);
						String iTitle = iEl.getChildText("t");
						String ctype = iEl.getChildText("c");
						System.out.println("--iTitle-->"+iTitle);
						System.out.println("--ctype-->"+ctype);
						List mList = iEl.getChildren("m");
						for (int l = 0; l < mList.size(); l++) {
							Element mEl =(Element) mList.get(l);
							String mUrl = mEl.getChildText("url");
							System.out.println("--mUrl-->"+mUrl);
							List adList = mEl.getChildren("ad");
							for (int m = 0; m < adList.size(); m++) {
								Element adEl = (Element) adList.get(m);
								String adStr = adEl.getText();
								System.out.println("--adstr-->"+adStr);
							}
						}
					}
				}			

				System.out.println("--------------------------------");
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
