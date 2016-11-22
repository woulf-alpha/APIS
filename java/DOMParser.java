
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class DOMParserWeb extends Exception {
	private InputSource is;
	private DocumentBuilder builder;

	public DOMParserWeb(URL strUrl){
		// abrimos la pagina y obtenemos nuestro canal is para luego parsear
		URL url = null;

		try {
			url = strUrl;
			InputStream is = url.openStream();
			// creamos el fuente a traves del stream que es el que necesita el parseador
			this.is=new InputSource(is);
		} catch (MalformedURLException e) {
			System.out.println("parsermf");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("parserio");
			e.printStackTrace();
		}
		// Creamos nuestro atributo  DocumentBuilder para luego parsear
		builder = null;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			System.out.println("builder");
			e.printStackTrace();
		}
	}

	public DOMParserWeb(String file){
		
		try {
			this.is = new InputSource(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		builder = null;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			System.out.println("builder");
			e.printStackTrace();
		}

	}


	public Document getDocumento() {
		// Con nuestros dos atributos ya podemos parsear y obtener el DOCUMENTO.
		// Co este Documento ya podemos trabajar y sacarle las tripas (los nodos)
		Document doc = null;
		try {
			doc = builder.parse(is);
		} catch (SAXException e) {
			System.out.println("getsax");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("getio");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc;
	}

	// Funcion base PACO
	//

	public String getValorEtiqueta(Element eElement, String sTag) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();

		Node nValue = (Node) nlList.item(0);
		if((CharacterData)nValue instanceof CharacterData)
		{
			return ((CharacterData) nValue).getData();
		}
		return nValue.getNodeValue();
	}

	// Retorna String
	// No testeado
	//
	public String getValorEitqueta(Element eElement, String sTag, int item){
		NodeList nlList = eElement.getElementsByTagName(sTag).item(item).getChildNodes();
		Node nValue = (Node) nlList.item(item);
		if((CharacterData)nValue instanceof CharacterData)
		{
			return ((CharacterData) nValue).getData();
		}
		return nValue.getNodeValue(); 
	}


	// Retorna una String
	// Este devuelve el valor del atributo de un elemento.
	//
	public String getAtributoEtiqueta(Element eElement, String padre, String sTag){
		Element e = (Element) eElement.getElementsByTagName(padre).item(0);
		return e.getAttribute(sTag);
	}


	// Retorna HashMap<K,V> 
	// Se utiliza para obtener todos los atributos de golpe de un elemento.
	//
	public HashMap<String,String> getAtributosEtiqueta(Element element, String padre){

		HashMap<String,String> datos = new HashMap<String, String>();

		Element e = (Element) element.getElementsByTagName(padre).item(0);
		NamedNodeMap atributos = e.getAttributes();
		for (int i = 0; i < atributos.getLength(); ++i)
		{
			Node attr = atributos.item(i);
			datos.put(attr.getNodeName(), attr.getNodeValue());
		}
		return datos;
	}
	
	public ArrayList<Element> getItems(String padre, String item){
		ArrayList<Element> listaElement = new ArrayList<>();
		Document doc = getDocumento();

		Element element = (Element) doc.getElementsByTagName(padre).item(0);
		
		NodeList elements = element.getElementsByTagName(item);
		
		for(int i = 0; i < elements.getLength(); i++){
			Element e = (Element) elements.item(i);
			listaElement.add(e);
		}
		
		return listaElement; 
	}
}
