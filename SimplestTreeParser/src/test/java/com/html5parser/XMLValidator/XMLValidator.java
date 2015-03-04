package com.html5parser.XMLValidator;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLValidator {
	
	public void validate(Document dom, String schemaLocation) throws SAXException, IOException{
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		
		File schemaFile = new File(schemaLocation);
		Source schemaSource = new StreamSource(schemaFile.toURI().toString());
		Schema schema = schemaFactory.newSchema(schemaSource);
		
		Validator validator = schema.newValidator();
		
		Source xmlSource = new DOMSource(dom);
		
		validator.validate( xmlSource );
		
	}

}
