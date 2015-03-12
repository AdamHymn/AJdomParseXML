package com.main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;


public class Main {

	public static void main(String[] args) {
		String city = "̫ԭ";
		try {
			System.out.println("--->"+URLEncoder.encode(city , "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		parseWeatherXml("http://api.map.baidu.com/telematics/v3/weather?location=%E5%A4%AA%E5%8E%9F&ak=8f110426274bb8a3788ce9352720c457");
		try {
			parseWeatherXml("http://api.map.baidu.com/telematics/v3/weather?location="+URLEncoder.encode(city , "UTF-8")+"&ak=8f110426274bb8a3788ce9352720c457");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		parseMainXml("http://localhost:8080/palmcity/public/main.xml");
	}
	
	/**�����ٶ�����Ԥ����Ϣ��xml*/
	public static void parseWeatherXml(String path){
		List<String[]> ls = new ArrayList<>();
		String info = null;
		try {
			URL url = new URL(path);
			URLConnection uc = url.openConnection();
			uc.setConnectTimeout(10000);
			uc.setDoInput(true);
			
			SAXBuilder saxBuilder = new SAXBuilder(false);
			org.jdom.Document doc = saxBuilder.build(uc.getInputStream());
			org.jdom.Element root = doc.getRootElement();
			System.out.println("aa-->"+root.getName());
			Element resultsEl = root.getChild("results");
			
			Element weatherEl = resultsEl.getChild("weather_data");
//			System.out.println("bb-->"+resultsEl.getContentSize());
			for (int i = 0; i < weatherEl.getContentSize()-2;i++ ) {
				i++;
				if(weatherEl.getContent(i).toString().equals("[Element: <date/>]")){
					System.out.println("========================");
				}
				System.out.println("----weather---->"+weatherEl.getContent(i).getValue());
				
			}		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		
		
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
			System.out.println("�������Ŀ��"+list.size()+"��");
			for (int i = 0; i < list.size(); i++) {
				Element oEl = (Element) list.get(i);
				String title = oEl.getChildText("t");
				String frontImage = oEl.getChildText("fi");
				String backImage = oEl.getChildText("bc");
				System.out.println("-oel-"+(i+1)+"-->"+title);
				System.out.println("-oel-"+(i+1)+"-->"+frontImage);
				System.out.println("-oel-"+(i+1)+"-->"+backImage);
				Element dsEl = oEl.getChild("ds");
				List dList = dsEl.getChildren("d");
				System.out.println("�����--��"+title+"���µ��ӷ�����Ŀ��"+dList.size()+"��");
				for (int j = 0; j < dList.size(); j++) {
					Element dEl = (Element) dList.get(j);
					String childTitle = dEl.getChildText("t");
					System.out.println("--childTitle-->"+childTitle);
					Element isEl = dEl.getChild("is");
					List iList = isEl.getChildren("i");
					System.out.println("�ӷ���--��"+childTitle+"���µ������Ŀ��"+iList.size()+"��");
					for (int k = 0; k < iList.size(); k++) {
						Element iEl = (Element) iList.get(k);
						String iTitle = iEl.getChildText("t");
						String ctype = iEl.getChildText("c").trim();//������ͺ���Ҫ�����ݲ�ͬ�����ͣ�Ԫ���ݻ᲻ͬ����Ȼ������������չʾ����Ҳ��ͬ��
						System.out.println("--iTitle-->"+iTitle);
						System.out.println("--ctype-->"+ctype);
						List mList = iEl.getChildren("m");
						System.out.println("���--��"+iTitle+"���µ�Ԫ������Ŀ��"+mList.size()+"��");
						//�˴�����ʼ����������ͣ����������
						if(ctype.equalsIgnoreCase("singletextlist")
								|| ctype.equalsIgnoreCase("singletextlist")
								|| ctype.equalsIgnoreCase("singleimagetextlist")
								|| ctype.equalsIgnoreCase("html5page")
								|| ctype.equalsIgnoreCase("carousel")
						  ){
							for (int l = 0; l < mList.size(); l++) {
								Element mEl =(Element) mList.get(l);
								String mUrl = mEl.getChildText("url");
								System.out.println("--mUrl-->"+mUrl);
								List adList = mEl.getChildren("ad");
								System.out.println("Ԫ�����й�����Ŀ��"+adList.size()+"��");
								if(adList != null){
									for (int m = 0; m < adList.size(); m++) {
										Element adEl = (Element) adList.get(m);
										String adType = adEl.getChildText("type");
										String adTitle = adEl.getChildText("title");
										String adImage = adEl.getChildText("image");
										String adUrl = adEl.getChildText("url");
										System.out.println("--adType-->"+adType);
										System.out.println("--adTitle-->"+adTitle);
										System.out.println("--adImage-->"+adImage);
										System.out.println("--adUrl-->"+adUrl);
									}
								}
							}
						   }else 
							   if(ctype.equalsIgnoreCase("multtextlist") || ctype.equalsIgnoreCase("multimagetextlist")){
									for (int l = 0; l < mList.size(); l++) {
										Element mEl =(Element) mList.get(l);
//										String mUrl = mEl.getChildText("url");
//										System.out.println("--mUrl-->"+mUrl);
										List columnList = mEl.getChildren("column");
										System.out.println("Ԫ������(��Ŀ)����Ŀ��"+columnList.size()+"��");
										if(columnList != null){
											for (int m = 0; m < columnList.size(); m++) {
												Element columnEl = (Element) columnList.get(m);
												String columnTitle = columnEl.getChildText("title");
												String columnUrl = columnEl.getChildText("url");
												System.out.println("--columnTitle-->"+columnTitle);
												System.out.println("--columnUrl-->"+columnUrl);
											}
										}
										List adList = mEl.getChildren("ad");
										System.out.println("Ԫ�����У���棩����Ŀ��"+adList.size()+"��");
										if(adList != null){
											for (int m = 0; m < adList.size(); m++) {
												Element adEl = (Element) adList.get(m);
												String adType = adEl.getChildText("type");
												String adTitle = adEl.getChildText("title");
												String adImage = adEl.getChildText("image");
												String adUrl = adEl.getChildText("url");
												System.out.println("--adType-->"+adType);
												System.out.println("--adTitle-->"+adTitle);
												System.out.println("--adImage-->"+adImage);
												System.out.println("--adUrl-->"+adUrl);
											}
										}
									}
							   }else
								   if(ctype.equalsIgnoreCase("imagelink") || ctype.equalsIgnoreCase("textlink")){
										for (int l = 0; l < mList.size(); l++) {
											Element mEl =(Element) mList.get(l);
											List linkList = mEl.getChildren("link");
											System.out.println("Ԫ������(link)����Ŀ��"+linkList.size()+"��");
											if(linkList != null){
												for (int m = 0; m < linkList.size(); m++) {
													Element linkEl = (Element) linkList.get(m);
													String linkType = linkEl.getChildText("type");
													String linkTitle = linkEl.getChildText("title");
													String linkImage = linkEl.getChildText("image");
													String linkUrl = linkEl.getChildText("url");
													System.out.println("--linkType-->"+linkType);
													System.out.println("--linkTitle-->"+linkTitle);
													System.out.println("--linkImage-->"+linkImage);
													System.out.println("--linkUrl-->"+linkUrl);
												}
											}
											List adList = mEl.getChildren("ad");
											System.out.println("Ԫ�����У���棩����Ŀ��"+adList.size()+"��");
											if(adList != null){
												for (int m = 0; m < adList.size(); m++) {
													Element adEl = (Element) adList.get(m);
													String adType = adEl.getChildText("type");
													String adTitle = adEl.getChildText("title");
													String adImage = adEl.getChildText("image");
													String adUrl = adEl.getChildText("url");
													System.out.println("--adType-->"+adType);
													System.out.println("--adTitle-->"+adTitle);
													System.out.println("--adImage-->"+adImage);
													System.out.println("--adUrl-->"+adUrl);
												}
											}
										}
								   }else
									   if(ctype.equalsIgnoreCase("imageapp") || ctype.equalsIgnoreCase("textapp")){
										   for (int l = 0; l < mList.size(); l++) {
												Element mEl =(Element) mList.get(l);
												List appList = mEl.getChildren("app");
												System.out.println("Ԫ������(app)����Ŀ��"+appList.size()+"��");
												if(appList != null){
													for (int m = 0; m < appList.size(); m++) {
														Element appkEl = (Element) appList.get(m);
														String appType = appkEl.getChildText("type");
														String appTitle = appkEl.getChildText("title");
														String appImage = appkEl.getChildText("image");
														String appUrl = appkEl.getChildText("url");
														String appPath = appkEl.getChildText("path");
														System.out.println("--appType-->"+appType);
														System.out.println("--appTitle-->"+appTitle);
														System.out.println("--appImage-->"+appImage);
														System.out.println("--appUrl-->"+appUrl);
														System.out.println("--appPath-->"+appPath);
													}
												}
										   }
									   }else
										   if(ctype.equalsIgnoreCase("activity")){
											   for (int l = 0; l < mList.size(); l++) {
												Element cEl = (Element) mList.get(l);
												String cPath = cEl.getText();
												System.out.println("--cPath-->"+cPath);
											}
										   }else{
											   System.out.println("--!!!!!!-->"+"�޴�������ͣ���");
											   return;
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
