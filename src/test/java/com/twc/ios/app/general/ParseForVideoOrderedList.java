package com.twc.ios.app.general;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParseForVideoOrderedList extends Utils{

	
	private static String evaluateCharlesFileForDSXVideoCallAndGetResponseBody(Document document, String xpathExpression, String pathParameter) throws Exception {
		
		
		//Getting the transaction element by passing xpath expression
		NodeList nodeList = document.getElementsByTagName("transaction");
		
		//String slotId = "/cms/v4/orderedlist/en_US/mobile/video/pl-the-latest";
		String videoresbody = null;
		
		// Create XPathFactory object
				XPathFactory xpathFactory = XPathFactory.newInstance();
		// Create XPath object
				XPath xpath = xpathFactory.newXPath();
				List<String> values = new ArrayList<String>();
				try {
		// Create XPathExpression object
					XPathExpression expr = xpath.compile(xpathExpression);
					NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
					boolean flag = false;
					mainloop:
					for (int i = 0; i < nodes.getLength(); i++) {
						//values.add(nodes.item(i).getNodeValue());
						if(nodes.item(i).getNodeValue().contains(pathParameter)) {
							System.out.println("DSX Video call found");
							logStep("DSX Video call found");
							
							outerloop:
							for (int p = 0; p < nodeList.getLength(); p++) {
								//System.out.println("Total transactions: "+nodeList.getLength());
								if (nodeList.item(p) instanceof Node) {
									Node node = nodeList.item(p);
									if (node.hasChildNodes()) {
										NodeList nl = node.getChildNodes();
										for (int j = 0; j < nl.getLength(); j++) {
											//System.out.println("node1 length is: "+nl.getLength());
											Node innernode = nl.item(j);
											if (innernode != null) {
												//System.out.println("Innernode name is: "+innernode.getNodeName());
												if (innernode.getNodeName().equals("request")) {
													if (innernode.hasChildNodes()) {
														NodeList n2 = innernode.getChildNodes();
														for (int k = 0; k < n2.getLength(); k++) {
															//System.out.println("node2 length is: "+n2.getLength());
															Node innernode2 = n2.item(k);
															if (innernode2 != null) {
																//System.out.println("Innernode2 name is: "+innernode2.getNodeName());
																if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
																	Element eElement = (Element) innernode2;
																	//System.out.println("Innernode2 element name is: "+eElement.getNodeName());
																	if (eElement.getNodeName().equals("headers")) {
																		if (innernode2.hasChildNodes()) {
																			NodeList n3 = innernode2.getChildNodes();
																			for (int q = 0; q < n3.getLength(); q++) {
																				//System.out.println("node3 length is: "+n3.getLength());
																				Node innernode3 = n3.item(q);
																				if (innernode3 != null) {
																					//System.out.println("Innernode3 name is: "+innernode3.getNodeName());
																					if (innernode3.getNodeType() == Node.ELEMENT_NODE) {
																						Element eElement1 = (Element) innernode3;
																						//System.out.println("Innernode3 element name is: "+eElement1.getNodeName());
																						if (eElement1.getNodeName().equals("header")) {
																							String content = eElement1.getTextContent();
																							//System.out.println("request body "+content);
																							
																							if (content.contains(pathParameter)) {
																								flag = true;
																								//istofRequestBodies.add(content);
																								//System.out.println("request body found "+content);
																								//break;
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}

												if (flag) {
													//System.out.println("Exiting after found true ");
													//System.out.println("checking innernode name is: "+innernode.getNodeName());
													if (innernode.getNodeName().equals("response")) {
					// System.out.println(innernode.getNodeName());
														if (innernode.hasChildNodes()) {
															NodeList n2 = innernode.getChildNodes();
															for (int k = 0; k < n2.getLength(); k++) {
																Node innernode2 = n2.item(k);
																if (innernode2 != null) {
																	if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
																		Element eElement = (Element) innernode2;
																		if (eElement.getNodeName().equals("body")) {
																			String content = eElement.getTextContent();
																			//istofResponseBodies.add(content);
																			//get_Expected_Value_From_APIResponseBody("adzone", content);
																			videoresbody = content;
																			//System.out.println("response body "+content);
																			break mainloop;
																																					}
																	}
																}
															}
														}
													}
													
												}
												//break;
											}
										}
									}
								}
								//flag = false;
							}
	
						}
						
					}
				} catch (XPathExpressionException e) {
					e.printStackTrace();
				}
				return videoresbody;
			}
	
	/*
	 * private String get_adzone_value_inJsonResponseBody(String qryValue) throws
	 * ParseException { String b_paramValue = ""; JSONParser parser = new
	 * JSONParser();
	 * 
	 * // System.out.println("adreq1 is : "+adreq1.toString()); Object obj =
	 * parser.parse(new String(qryValue)); // System.out.println("obj : "+obj);
	 * JSONObject jsonObject = (JSONObject) obj;
	 * 
	 * JSONObject mainTag = null; //mainTag = (JSONObject) jsonObject.get("main");
	 * JSONArray eleArray = (JSONArray) jsonObject.get("main");
	 * 
	 * System.out.println(eleArray); //return b_paramValue; ArrayList<String>
	 * Ingredients_names = new ArrayList<>(); //for (int i = 0; i < eleArray.size();
	 * i++) { String arrayElement = String.valueOf(eleArray.get(0)); obj =
	 * parser.parse(new String(arrayElement)); jsonObject = (JSONObject) obj;
	 * mainTag = (JSONObject) obj; mainTag = (JSONObject) mainTag.get("adsmetrics");
	 * String adZoneValue = String.valueOf(mainTag.get("adzone"));
	 * System.out.println("adZone Value is: "+adZoneValue); //} return adZoneValue;
	 * }
	 */
	private static String get_adzone_value_inJsonResponseBody(String qryValue) throws JSONException {
		String adZoneValue = null;
		try {
			JSONArray jsonArray  = new JSONArray(qryValue);
			//for (int i = 0; i < jsonArray.length(); i++) {
			    JSONObject jsonobject = jsonArray.getJSONObject(0);
			    //System.out.println(jsonobject);
			    //logStep(jsonobject.toString());
			   // String name = jsonobject.getString("name");
			   // String url = jsonobject.getString("url");
			    JSONObject  mainTag = (JSONObject) jsonobject.get("adsmetrics");
			    adZoneValue  = String.valueOf(mainTag.get("adzone"));
			    System.out.println("adZone Value is: "+adZoneValue);
			    logStep("adZone Value is: "+adZoneValue);
			//}
			    
		}catch(Exception e) {
			System.out.println("There is an exception while parsing dsx.weather.com/cms/v4/orderedlist/en_US/mobile/video/ call");
			logStep("There is an exception while parsing dsx.weather.com/cms/v4/orderedlist/en_US/mobile/video/ call");
		}
		
		return adZoneValue;
	}
	
	/**
	 * Since video call iu value is dynamic, generating from the adzone value 
	 */
	public static void getVideoCall_IU_Value_from_adZone() throws Exception {
		//may need to set videoIUValue to null at this time, need to relook later
		 //Read the content form file
			File fXmlFile = new File(outfile.getName());

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setValidating(false);
			dbFactory.setNamespaceAware(true);
			dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
			dbFactory.setFeature("http://xml.org/sax/features/validation", false);
			dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
			dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			Document doc = dBuilder.parse(fXmlFile);
	//Getting the transaction element by passing xpath expression
			NodeList nodeList = doc.getElementsByTagName("transaction");
			//String xpathExpression = "charles-session/transaction/@query";
			String xpathExpression = "charles-session/transaction/@path";
			String videoPathParameter = "/cms/v4/orderedlist/en_US/mobile/video/";
			String videoCallResponseToCheck = evaluateCharlesFileForDSXVideoCallAndGetResponseBody(doc, xpathExpression,videoPathParameter);

			String adZone = get_adzone_value_inJsonResponseBody(videoCallResponseToCheck);
			String videoCallIU = null;
			if(adZone != null) {
				if(adZone.equalsIgnoreCase("video")) {
					 videoCallIU = "iu=%2F7646%2Fapp_iphone_us%2Fvideo";
				}else{
					adZone = adZone.replaceAll("/", "%2F");
					videoCallIU = "iu=%2F7646%2Fapp_iphone_us%2F".concat(adZone);
				}
					
			}else {
				System.out.println("Since dsx.weather.com/cms/v4/orderedlist/en_US/mobile/video/ call is not generated, assigning default video iu value");
				logStep("Since dsx.weather.com/cms/v4/orderedlist/en_US/mobile/video/ call is not generated, assigning default video iu value");
				videoCallIU = "iu=%2F7646%2Fapp_iphone_us%2Fvideo";
			}
			videoIUValue = videoCallIU;
			 System.out.println("Video Call IU Value: "+videoCallIU);
			logStep("Video Call IU Value: "+videoCallIU);	
	}
	
	public static String get_criteo_response_parameter_value_by_placementId_inJsonResponseBody(String qryValue) throws JSONException {
		String adZoneValue = null;
		try {
			JSONArray jsonArray  = new JSONArray(qryValue);
			//for (int i = 0; i < jsonArray.length(); i++) {
			    JSONObject jsonobject = jsonArray.getJSONObject(0);
			    //System.out.println(jsonobject);
			    //logStep(jsonobject.toString());
			   // String name = jsonobject.getString("name");
			   // String url = jsonobject.getString("url");
			    JSONObject  mainTag = (JSONObject) jsonobject.get("adsmetrics");
			    adZoneValue  = String.valueOf(mainTag.get("adzone"));
			    System.out.println("adZone Value is: "+adZoneValue);
			    logStep("adZone Value is: "+adZoneValue);
			//}
			    
		}catch(Exception e) {
			System.out.println("There is an exception while parsing dsx.weather.com/cms/v4/orderedlist/en_US/mobile/video/ call");
			logStep("There is an exception while parsing dsx.weather.com/cms/v4/orderedlist/en_US/mobile/video/ call");
		}
		
		return adZoneValue;
	}
}