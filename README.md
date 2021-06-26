**# WebCrawler-v2**

This project contains Web crawler which will search for word for given URL(s) and stores the result in Output File

**Configuration needs to be updated 
**
1. List of URL(s)
2. Text to search
3. Stores the search result in Output File

Depends need to be setup as per below. Ensure below are place artifacts available in same location
1. Ensure library - jsoup-1.13.1.jar is part of lib folder
2. WebCrawler-0.0.1-SNAPSHOT.jar has to be in same location as lib folder
3. Copy config.properties and update properties as per local environment. Sample of property file can be referred
4. Have output.txt file created in the same location as per config.properties

**Execution:**
java -jar WebCrawler-0.0.1-SNAPSHOT.jar

The result of execution will be stored in output.txt file.
