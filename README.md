# Semantic Algiment Analyser

This application examines and analyses the alignment between the service and information layers in Smart City architectures to support Smart City strategies by using semantic analysis. 
This analysis uses a top-down Enterprise Architecture (EA) approach starting with city goals and objectives, and ending up in city services implementation details. 
This is critical for city managers who need alignment information to support the operation and management of public city services. The main components are described below:

Transformation: This component implements the transformation of ArchiSmartCity models generated in XML format into the Resource Description Framework (RDF).
Semantic Processing: This component examines the knowledge of the models created and analyses the alignment between city services and their underlying information systems.
Visualisation: This component presents the information on the RDF models in a web application interface that can be used by Smart City stakeholders (e.g., city authorities, service providers). End users can query the models starting from city goals, city objectives, or city services. The system then displays the identified alignment between the Smart City strategies, city services, and their information systems according to the ArchiSmartCity metamodel definition. Furthermore, this alignment is analysed by comparing the current and target values of the city services qualities to present a detailed level of alignment.
