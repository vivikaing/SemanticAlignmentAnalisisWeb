package semantic.alignment.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;

import no.acando.xmltordf.Builder;

public class Util {
	public Dataset xml2rdf(String inFile) {
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(inFile));
			Dataset dataset = Builder.getAdvancedBuilderJena().build().convertToDataset(in);
			return dataset;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static ArrayList<String> FindObject(Model model, String property, ArrayList<String> conceptsInput,
			String tag) {
		try {

			ArrayList<String> conceptsOutput = new ArrayList<String>();
			Property prop = model.getProperty(property);
			ResIterator iter = model.listSubjectsWithProperty(prop);
			while (iter.hasNext()) {
				Resource subject = iter.nextResource();			
				//for (int i = 0; i < conceptsInput.size(); i++) {			
				if (conceptsInput.contains("" + subject)) {
					NodeIterator nodes = model.listObjectsOfProperty(subject, prop);
					while (nodes.hasNext()) {
						RDFNode object = nodes.nextNode();
						//System.out.println("" + subject + tag + object);
						conceptsOutput.add("" + object);
					}
				}
				//}
			}
			return conceptsOutput;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static String FindUniqueObject(Model model, String property, String conceptInput,
			String tag) {
		try {

			String conceptOutput = new String();
			Property prop = model.getProperty(property);
			ResIterator iter = model.listSubjectsWithProperty(prop);
			while (iter.hasNext()) {
				Resource subject = iter.nextResource();			
				//for (int i = 0; i < conceptsInput.size(); i++) {			
				if (conceptInput.contains("" + subject)) {
					NodeIterator nodes = model.listObjectsOfProperty(subject, prop);
					while (nodes.hasNext()) {
						RDFNode object = nodes.nextNode();
						//System.out.println("" + subject + tag + object);
						conceptOutput = object.toString();
					}
				}
				//}
			}
			return conceptOutput;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static ArrayList<String> FindSubject(Model model, String property, ArrayList<String> conceptsInput,
			String tag) {
		try {

			ArrayList<String> conceptsOutput = new ArrayList<String>();
			Property prop = model.getProperty(property);
			ResIterator iter = model.listSubjectsWithProperty(prop);
			while (iter.hasNext()) {
				Resource subject = iter.nextResource();
				NodeIterator nodes = model.listObjectsOfProperty(subject, prop);
				while (nodes.hasNext()) {
					RDFNode object = nodes.nextNode();
					
					//for (int i = 0; i < conceptsInput.size(); i++) {
					if (conceptsInput.contains("" + object)) {
						//System.out.println("" + subject);
						conceptsOutput.add("" + subject);
					//}
					}
				}
			}
			return conceptsOutput;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<String> FindSubjectGeneric(Model model, String property, String conceptInput,
			String tag) {
		try {

			ArrayList<String> conceptsOutput = new ArrayList<String>();
			Property prop = model.getProperty(property);
			ResIterator iter = model.listSubjectsWithProperty(prop);
			while (iter.hasNext()) {
				Resource subject = iter.nextResource();
				NodeIterator nodes = model.listObjectsOfProperty(subject, prop);
				while (nodes.hasNext()) {
					RDFNode object = nodes.nextNode();
					
					//for (int i = 0; i < conceptsInput.size(); i++) {
					if (conceptInput.contains("" + object)) {
						//System.out.println("" + subject);
						conceptsOutput.add("" + subject);
					//}
					}
				}
			}
			return conceptsOutput;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static ArrayList<String> FindSubjectRelationship(Model model, String property,
			ArrayList<String> conceptsInput, String tag, String relationshipName) {
		try {

			ArrayList<String> conceptsOutput = new ArrayList<String>();
			Property prop = model.getProperty(property);
			ResIterator iter = model.listSubjectsWithProperty(prop);
			while (iter.hasNext()) {
				Resource subject = iter.nextResource();
				if (conceptsInput.contains("" + subject)) {
					NodeIterator nodes = model.listObjectsOfProperty(subject, prop);
					while (nodes.hasNext()) {
						RDFNode object = nodes.nextNode();
						if (object.toString().equals(relationshipName)) {
							//System.out.println("" + subject + " " + object);
							conceptsOutput.add("" + subject);
						}

					}
				}
			}
			return conceptsOutput;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
