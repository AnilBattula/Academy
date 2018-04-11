package com.vv.test.automation.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import com.vv.test.automation.utilities.ConfigManager;
import com.vv.test.automation.web.accelerators.ActionsLibrary;



public class SoapRequestVerification extends ActionsLibrary{
	public SOAPConnection soapConnection;
	public String appurl;

	/**
	 * @author Cigniti
	 * @param secid
	 * @param password
	 * @return
	 * @throws SOAPException
	 * @throws IOException
	 */
	public static SOAPMessage createSOAPRequest(String secid, String password, String BodyName, String qName, String childQName, String soapRequestName) throws SOAPException, IOException {
		String serverURI = "";
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPBody body = soapMessage.getSOAPBody();

		QName bodyName = new QName(serverURI, BodyName, "impl");
		SOAPBodyElement bName = body.addBodyElement(bodyName);

		QName name = new QName(qName);
		SOAPElement child = bName.addChildElement(name);

		name = new QName(childQName);
		SOAPElement symbol2 = child.addChildElement(name);
		symbol2.addTextNode(secid);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", serverURI  + soapRequestName);
		soapMessage.saveChanges();
		soapMessage.writeTo(System.out);
		return soapMessage;
	}

	/**
	 * @author Cigniti
	 * @param secID
	 * @param password
	 * @return
	 */
	public SOAPMessage sendSoapRequest(String secID, String password) 
	{
		SOAPMessage response = null;
		try
		{
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = soapConnectionFactory.createConnection();

			String url = null;
			if(ConfigManager.getProperties().getProperty("environment").trim().toLowerCase().equals("qa"))
			{
				url ="";
			}
			java.net.URL endpoint = new URL(url);
			response = connection.call(createSOAPRequest(secID,password,"",""), endpoint);
			connection.close();
		}
		catch(Exception e)
		{
			e.getLocalizedMessage();
		}
		return response;
	}

	/**
	 * @author Cigniti
	 * @param message
	 * @return
	 */
	public String soapMessageToString(SOAPMessage message) 
	{
		String result = null;
		if (message != null) 
		{
			ByteArrayOutputStream baos = null;
			try 
			{
				baos = new ByteArrayOutputStream();
				message.writeTo(baos);
				result = baos.toString();
			} 
			catch (Exception e) {
			} 
			finally 
			{
				if (baos != null) 
				{
					try 
					{
						baos.close();
					} 
					catch (IOException ioe) 
					{
					}
				}
			}
		}
		return result;
	} 

	/**
	 * @author Cigniti
	 * @param connectionString
	 * @param soapRequest
	 * @return
	 * @throws SOAPException
	 */
	public SOAPMessage executeRequest(SOAPConnection connectionString, SOAPMessage soapRequest) throws SOAPException
	{
		SOAPMessage response = null;
		java.net.URL endpoint = null;
		try{
			try {
				endpoint = new URL(appurl);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response = connectionString.call(soapRequest, endpoint);
		}
		catch(Exception e){

		}
		return response;
	}

	/**
	 * @author Cigniti
	 * @return
	 */
	public SOAPConnection soapConnectionRequest() 
	{
		try
		{
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			soapConnection = soapConnectionFactory.createConnection();
			if(ConfigManager.getProperties().getProperty("environment").trim().contains("QA"))
			{
				appurl ="";
			}
			else if(ConfigManager.getProperties().getProperty("environment").trim() =="STAG")
			{
				appurl ="";
			}
			else if(ConfigManager.getProperties().getProperty("environment").trim() =="DEV")
			{
				appurl ="";
			}
			else if(ConfigManager.getProperties().getProperty("environment").trim() =="Prod")
			{
				appurl ="";
			}
		}
		catch(Exception e)
		{
			e.getLocalizedMessage();
		}
		return 	soapConnection;
	}

	/**
	 * @author Cigniti
	 * @return
	 */
	
	
	public SOAPConnection soapConnectionRequesttoMembership() 
	{
		try
		{
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			soapConnection = soapConnectionFactory.createConnection();


			if(ConfigManager.getProperties().getProperty("environment").trim().contains("QA"))
			{
				appurl ="";
			}
			else if(ConfigManager.getProperties().getProperty("environment").trim() =="STAG")
			{
				appurl ="";
			}
			else if(ConfigManager.getProperties().getProperty("environment").trim() =="DEV")
			{
				appurl ="";
			}
		}
		catch(Exception e)
		{
			e.getLocalizedMessage();
		}
		return 	soapConnection;
	}

	/**
	 * @author Cigniti
	 */
	public void closeSoapConnection(){
		try{
			soapConnection.close();	
		}
		catch(Exception e){
			e.getLocalizedMessage();
		}
	}
	
	/**
	 * @author Cigniti
	 * @description: SOAP request
	 * @param from
	 * @param to
	 * @param ip
	 * @param serverURI
	 * @return
	 * @throws SOAPException
	 * @throws IOException
	 */
	public static SOAPMessage createSOAPRequest(String from, String to, String ip, String serverURI) throws SOAPException, IOException
	{
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.setPrefix("soapenv");
		envelope.removeNamespaceDeclaration("SOAP-ENV");
		envelope.addNamespaceDeclaration("equ", serverURI);
		envelope.removeAttribute("SOAP-ENV");
		// SOAP Body
		SOAPBody soapBody = envelope.getBody();
		soapBody.setPrefix("soapenv");
		soapBody.removeNamespaceDeclaration("SOAP-ENV");
		soapBody.removeAttribute("Header");
		SOAPElement soapBodyElem = soapBody.addChildElement("doCopyFile", "equ");
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("arg0");
		soapBodyElem1.addTextNode(from);
		SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("arg1");
		soapBodyElem2.addTextNode(to);
		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", ip+":8080"  + "/ProfileSetter/services/UtilsPort");
		soapMessage.getSOAPHeader().setPrefix("soapenv");
		soapMessage.saveChanges();
		/* Print the request message */
		System.out.print("Request SOAP Message = ");
		soapMessage.writeTo(System.out);

		return soapMessage;
	}
}