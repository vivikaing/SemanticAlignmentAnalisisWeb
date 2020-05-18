package semantic.alignment.logic;

import java.util.ArrayList;

import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;

import semantic.aligment.model.AppService;
import semantic.aligment.model.CityService;
import semantic.aligment.model.Domain;
import semantic.aligment.model.Generic;
import semantic.aligment.model.Goal;
import semantic.aligment.model.Indicator;
import semantic.aligment.model.Objective;
import semantic.aligment.model.QoSAppService;
import semantic.aligment.model.WebService;
import semantic.alignment.util.Util;


public class ReadRdf {
	
	private String path = "";
	
	public ReadRdf(String path) {
		this.path = path;
	}
	
	public Model readXmlModel() {
		Util util = new Util();
		Dataset dataset = util.xml2rdf(path + "sample9.archimate");
		System.out.println(dataset.toString());
		Model model = dataset.getDefaultModel();
		return model;
	}
	
	public ArrayList<Goal> findGoals() {
		Model model = readXmlModel();
		Property p = model.getProperty("http://www.w3.org/2001/XMLSchema-instance#type");
		ResIterator iter = model.listSubjectsWithProperty(p);
		ArrayList<String> concepts = new ArrayList<String>();
		ArrayList<String> objectivesArchiIds = new ArrayList<String>();
		ArrayList<Goal> goals = new ArrayList<Goal>();


		while (iter.hasNext()) {
			Resource subject = iter.nextResource();
			NodeIterator nodes = model.listObjectsOfProperty(subject, p);
			while (nodes.hasNext()) {
				RDFNode object = nodes.nextNode();
				// if(object.toString().equals("folder")) {
				if (object.toString().equals("archimate:Goal")) {
					// System.out.println("" + subject);
					concepts = new ArrayList<String>();
					concepts.add("" + subject);

					// Find Object: Find Goals Names
					ArrayList<String> goalsNames = new ArrayList<String>();
					goalsNames = Util.FindObject(model, "name", concepts, " name Goal: ");

					// Find Object: Find Goals ArchiMate Ids
					ArrayList<String> goalsIds = new ArrayList<String>();
					goalsIds = Util.FindObject(model, "id", concepts, " id goal archiMate: ");

					// 1)
					// Find Subject: Find Relationship from Goals to Objectives RDF Ids
					ArrayList<String> objectivesRelationshipRdfIds = new ArrayList<String>();
					objectivesRelationshipRdfIds = Util.FindSubject(model, "target", goalsIds,
							" id relationship objective rdf: ");

					// 2)
					// Find Subject: Find Objectives RDF Ids which have a specific relationship
					ArrayList<String> objectivesIdswithRel = new ArrayList<String>();
					objectivesIdswithRel = Util.FindSubjectRelationship(model,
							"http://www.w3.org/2001/XMLSchema-instance#type", objectivesRelationshipRdfIds,
							" relationship: ", "archimate:QuantifyRelationship");

					// 3)
					// Find Object: Find Objectives ArchiMate Ids
					objectivesArchiIds = Util.FindObject(model, "source", objectivesIdswithRel, " id Objective: ");

					// 4)
					// Find Object: Find Objectives RDF Ids
					ArrayList<String> objectivesRfdIds = new ArrayList<String>();
					objectivesRfdIds = Util.FindSubject(model, "id", objectivesArchiIds, " id objective rdf: ");

					// 5)
					// Find Object: Find Objectives Names
					ArrayList<String> objectivesNames = new ArrayList<String>();
					objectivesNames = Util.FindObject(model, "name", objectivesRfdIds, " name Objective: ");

					// Print out current goal if the goal has objectives
					if (objectivesNames.size() > 0) {
						//System.out.println("Goal: " + goalsIds.get(0) + " " + goalsNames.get(0));
						Goal goal = new Goal(goalsIds.get(0), goalsNames.get(0));
						goals.add(goal);
					}
				}
			}
		}
		return goals;
	}
	
	public ArrayList<Objective> findObjectives(String idGoal)
	{
		Model model = readXmlModel();
		Property p = model.getProperty("http://www.w3.org/2001/XMLSchema-instance#type");
		String conceptName = new String();
		String relationshipName = new String();
		String relType = new String();
		String relType1 = new String();
			
			conceptName = "archimate:Goal";
			relationshipName = "archimate:QuantifyRelationship";
			relType = "target";
			relType1 = "source";
			ArrayList<Generic> genericObjectives = new ArrayList<Generic>();
			genericObjectives = findTriplesGeneric(p, idGoal, conceptName, relationshipName, relType, relType1);
			
			ArrayList<Objective> objectives = new ArrayList<Objective>();
			for(int j = 0; j < genericObjectives.size(); j++){
				Objective objective = new Objective();
				objective.setId(genericObjectives.get(j).getId());
				objective.setName(genericObjectives.get(j).getName());
				
				conceptName = "archimate:Objective";
				relationshipName = "archimate:MeasureRelationship"; 
				relType = "target"; 
				relType1 = "source"; 
				ArrayList<Generic> genericIndicators = new ArrayList<Generic>();
				genericIndicators = findTriplesGeneric(p, objective.getId(), conceptName, relationshipName,relType, relType1);
				
				for(int k = 0; k < genericIndicators.size(); k++){
					Indicator indicator = new Indicator();
					indicator.setId(genericIndicators.get(k).getId());
					indicator.setName(genericIndicators.get(k).getName());
										
					conceptName = "archimate:Indicator"; 
					ArrayList<Generic> indicatorProperties = findProperties(p, indicator.getId(), conceptName); 
					for(int q = 0; q < indicatorProperties.size(); q++) {
						if(indicatorProperties.get(q).getId().contains("target_value")){
							indicator.setTargetValue(indicatorProperties.get(q).getName());
						}
						else if(indicatorProperties.get(q).getId().contains("measured_value")){
							indicator.setCurrentValue(indicatorProperties.get(q).getName());
						}
						else if(indicatorProperties.get(q).getId().contains("comparison_operator")){
							indicator.setOperator(indicatorProperties.get(q).getName());
						}
						else if(indicatorProperties.get(q).getId().contains("unit_of_measure")){
							indicator.setUnitOfMeasure(indicatorProperties.get(q).getName());
						}	
						else if(indicatorProperties.get(q).getId().contains("qol_dimension")){
							indicator.setQolDimension(indicatorProperties.get(q).getName());
						}
					}
										
					relationshipName = "archimate:PerformanceRelationship"; 
					relType = "source"; 
					relType1 = "target";
					ArrayList<Generic> genericCityServices = new ArrayList<Generic>();
					genericCityServices = findTriplesGeneric(p, indicator.getId(), conceptName, relationshipName, relType, relType1); 
					
					ArrayList<CityService> cityServices = new ArrayList<CityService>();
					for(int m = 0; m < genericCityServices.size(); m++){
						CityService cityService = new CityService();
						cityService.setId(genericCityServices.get(m).getId());
						cityService.setName(genericCityServices.get(m).getName());
						
						conceptName = "archimate:CityService"; 
						relationshipName = "archimate:BelongingRelationship"; 
						relType = "source"; 
						relType1 = "target";
						ArrayList<Generic> genericDomains = new ArrayList<Generic>();
						genericDomains = findTriplesGeneric(p, cityService.getId(), conceptName, relationshipName, relType, relType1);
											
						ArrayList<Domain> domains = new ArrayList<Domain>();
						for(int n = 0; n < genericDomains.size(); n++) {
							Domain domain = new Domain();
							domain.setId(genericDomains.get(n).getId());
							domain.setName(genericDomains.get(n).getName());
							domains.add(domain);
						}
						cityService.setDomains(domains);
						cityServices.add(cityService);						
					}
					objective.setIndicator(indicator);
					objective.setCityServices(cityServices);
				}
				objectives.add(objective);
			}
					
		return objectives;
		
	}
	public ArrayList<Generic> findTriplesGeneric(Property p, String concept,
			String conceptName, String relationshipName, String relType, String relType1) {
		Model model = readXmlModel();
		ResIterator iter = model.listSubjectsWithProperty(p);
		ArrayList<String> childArchiIds = new ArrayList<String>();
		
		ArrayList<Generic> generics= new ArrayList<Generic>();
	

		while (iter.hasNext()) {
			Resource subject = iter.nextResource();
			NodeIterator nodes = model.listObjectsOfProperty(subject, p);
			while (nodes.hasNext()) {
				RDFNode object = nodes.nextNode();
				if (object.toString().equals(conceptName)) {
					//System.out.println("" + subject);

					// 1)
					// Find Subject from Object to Subject RDF Ids
					ArrayList<String> childRelationshipRdfIds = new ArrayList<String>();
					childRelationshipRdfIds = Util.FindSubjectGeneric(model, relType, concept, " :: ");

					// 2)
					// Find Subject relationship
					ArrayList<String> childIdswithRel = new ArrayList<String>();
					childIdswithRel = Util.FindSubjectRelationship(model,
							"http://www.w3.org/2001/XMLSchema-instance#type", childRelationshipRdfIds,
							" relationship: ", relationshipName);

					// 3)
					// Find Object ArchiMate Ids
					ArrayList<String> archiId = new ArrayList<String>();
					archiId = Util.FindObject(model, relType1, childIdswithRel, " :: ");
					for (int i = 0; i < archiId.size(); i++) {					
								
						if (childArchiIds.contains("" + archiId.get(i)) == false) {
							childArchiIds.add(archiId.get(i));

							// 4)
							// Find Object: Find Child RDF Ids
							ArrayList<String> childRfdIds = new ArrayList<String>();
							childRfdIds = Util.FindSubjectGeneric(model, "id", archiId.get(i), " :: ");

							// 5)
							// Find Object: Find Child Names
							ArrayList<String> childNames = new ArrayList<String>();
							childNames = Util.FindObject(model, "name", childRfdIds, " :: ");

							System.out.println("Child: " + childArchiIds.get(i) + " " + childNames.get(0));
							Generic generic = new Generic();
							generic.setId(childArchiIds.get(i));
							generic.setName(childNames.get(0));
							generics.add(generic);
							
						}
					}
				}
			}
		}
		return generics;
	}
	
	public ArrayList<Generic> findProperties(Property p, String concept,
			String conceptName) {
		
		Model model = readXmlModel();
		// 1)
		// Find Subject 
		ArrayList<Generic> generics= new ArrayList<Generic>();	
		ArrayList<String> subjectRdfId = new ArrayList<String>();
		subjectRdfId = Util.FindSubjectGeneric(model, "id", concept, " :: ");
		
		// 2)
		// Find Objects as properties
		ArrayList<String> childProperties = new ArrayList<String>();
		childProperties = Util.FindObject(model, "http://acandonorway.github.com/XmlToRdf/ontology.ttl#hasChild", subjectRdfId, " :: ");

		for (int i = 0; i < childProperties.size(); i++) {					
											    
				// 3)
				// Find Object: Find Child Keys
				String childKey = new String();
				childKey = Util.FindUniqueObject(model, "key", childProperties.get(i), " :: ");
				
				// 4)
				// Find Object: Find Child Values
				String childValue = new String();
				childValue = Util.FindUniqueObject(model, "value", childProperties.get(i), " :: ");
				
				Generic generic = new Generic();
				generic.setId(childKey);
				generic.setName(childValue);
				generics.add(generic);
				
			}
		return generics;
	}
	
	public ArrayList<CityService> findCityServices(String idObjective)
	{
		Model model = readXmlModel();
		Property p = model.getProperty("http://www.w3.org/2001/XMLSchema-instance#type");
		String conceptName = new String();
		String relationshipName = new String();
		String relType = new String();
		String relType1 = new String();
							
		conceptName = "archimate:Objective";
		relationshipName = "archimate:MeasureRelationship"; 
		relType = "target"; 
		relType1 = "source"; 
		ArrayList<Generic> genericIndicators = new ArrayList<Generic>();
		genericIndicators = findTriplesGeneric(p, idObjective, conceptName, relationshipName,relType, relType1);
		
		ArrayList<CityService> cityServices = new ArrayList<CityService>();

		for(int k = 0; k < genericIndicators.size(); k++){
			Indicator indicator = new Indicator();
			indicator.setId(genericIndicators.get(k).getId());
			indicator.setName(genericIndicators.get(k).getName());
			
			conceptName = "archimate:Indicator"; 
			ArrayList<Generic> indicatorProperties = findProperties(p, indicator.getId(), conceptName); 
			for(int q = 0; q < indicatorProperties.size(); q++) {
				if(indicatorProperties.get(q).getId().contains("target_value")){
					indicator.setTargetValue(indicatorProperties.get(q).getName());
				}
				else if(indicatorProperties.get(q).getId().contains("measured_value")){
					indicator.setCurrentValue(indicatorProperties.get(q).getName());
				}
				else if(indicatorProperties.get(q).getId().contains("comparison_operator")){
					indicator.setOperator(indicatorProperties.get(q).getName());
				}
				else if(indicatorProperties.get(q).getId().contains("unit_of_measure")){
					indicator.setUnitOfMeasure(indicatorProperties.get(q).getName());
				}
				else if(indicatorProperties.get(q).getId().contains("qol_dimension")){
					indicator.setQolDimension(indicatorProperties.get(q).getName());
				}
			}
			
			relationshipName = "archimate:PerformanceRelationship"; 
			relType = "source"; 
			relType1 = "target";
			ArrayList<Generic> genericCityServices = new ArrayList<Generic>();
			genericCityServices = findTriplesGeneric(p, indicator.getId(), conceptName, relationshipName, relType, relType1); 
			
			for(int m = 0; m < genericCityServices.size(); m++){
				CityService cityService = new CityService();
				cityService.setId(genericCityServices.get(m).getId());
				cityService.setName(genericCityServices.get(m).getName());
				
				conceptName = "archimate:CityService"; 
				relationshipName = "archimate:BelongingRelationship"; 
				relType = "source"; 
				relType1 = "target";
				ArrayList<Generic> genericDomains = new ArrayList<Generic>();
				genericDomains = findTriplesGeneric(p, cityService.getId(), conceptName, relationshipName, relType, relType1);
									
				ArrayList<Domain> domains = new ArrayList<Domain>();
				for(int n = 0; n < genericDomains.size(); n++) {
					Domain domain = new Domain();
					domain.setId(genericDomains.get(n).getId());
					domain.setName(genericDomains.get(n).getName());
					domains.add(domain);
				}
				
				conceptName = "archimate:ApplicationService"; 
				relationshipName = "archimate:AutomateRelationship"; 
				relType = "target";
				relType1 = "source";
				ArrayList<Generic> genericAppServices = new ArrayList<Generic>();
				genericAppServices = findTriplesGeneric(p, cityService.getId(),conceptName, relationshipName, relType, relType1);
				  				
				ArrayList<AppService> appServices = new ArrayList<AppService>();
				for(int s = 0; s < genericAppServices.size(); s++) {
					AppService appService = new AppService();
					appService.setId(genericAppServices.get(s).getId());
					appService.setName(genericAppServices.get(s).getName());
					
					conceptName = "archimate:QualityApplicationService"; 	
					relationshipName = "archimate:MeetRelationship"; 
					relType = "source";
					relType1 = "target";
					ArrayList<Generic> genericQosAppServices = new ArrayList<Generic>();
					genericQosAppServices = findTriplesGeneric(p, appService.getId(),conceptName, relationshipName, relType, relType1);
					
					ArrayList<QoSAppService> qosAppServices = new ArrayList<QoSAppService>();
					for(int r = 0; r < genericQosAppServices.size(); r++) {
						QoSAppService qosAppService = new QoSAppService();
						qosAppService.setId(genericQosAppServices.get(r).getId());
						qosAppService.setName(genericQosAppServices.get(r).getName());
						qosAppServices.add(qosAppService);
						
						ArrayList<Generic> qosProperties = findProperties(p, qosAppService.getId(), conceptName); 
						for(int q = 0; q < qosProperties.size(); q++) {
							if(qosProperties.get(q).getId().contains("target_value")){
								qosAppService.setTargetValue(qosProperties.get(q).getName());
							}
							else if(qosProperties.get(q).getId().contains("monitored_value")){
								qosAppService.setMonitoredValue(qosProperties.get(q).getName());
							}
							else if(qosProperties.get(q).getId().contains("comparison_operator")){
								qosAppService.setOperator(qosProperties.get(q).getName());
							}
							else if(qosProperties.get(q).getId().contains("unit_of_measure")){
								qosAppService.setUnitOfMeasure(qosProperties.get(q).getName());
							}		
						}
						
					}
					
					conceptName = "archimate:WebService"; 	
					relationshipName = "archimate:InterfaceRelationship"; 
					relType = "target";
					relType1 = "source";
					ArrayList<Generic> genericWebServices = new ArrayList<Generic>();
					genericWebServices = findTriplesGeneric(p, appService.getId(),conceptName, relationshipName, relType, relType1);
					
					for(int w = 0; w < genericWebServices.size(); w++) {
						WebService webService = new WebService();
						webService.setId(genericWebServices.get(w).getId());
						webService.setName(genericWebServices.get(w).getName());
						appService.setWebService(webService);
					}
					
					appService.setQosAppService(qosAppServices);
					appServices.add(appService);
				}
				cityService.setIdObjective(idObjective);
				cityService.setIndicator(indicator);
				cityService.setDomains(domains);
				cityService.setAppServices(appServices);
				cityServices.add(cityService);	
			}
		}
									
		return cityServices;
	}
	
	public ArrayList<Objective> findAllObjectives(){
		ArrayList<Goal> goals = findGoals();
		ArrayList<Objective> objectives = new ArrayList<Objective>();

		for(int i = 0; i < goals.size(); i++){
			objectives.addAll(findObjectives(goals.get(i).getId()));
		}		
		return objectives;
	}
	
	public ArrayList<CityService> findAllCityServices(){
		ArrayList<Goal> goals = findGoals();
		ArrayList<Objective> objectives = new ArrayList<Objective>();
		ArrayList<CityService> cityServices = new ArrayList<CityService>();
		
		for(int i = 0; i < goals.size(); i++){
			objectives.addAll(findObjectives(goals.get(i).getId()));
		}
		
		for(int j = 0; j < objectives.size(); j++){
			cityServices.addAll(findCityServices(objectives.get(j).getId()));
		}
		
		return cityServices;
	}
}
