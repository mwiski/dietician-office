#### **Dietician office application**
1. #### **General description**
	This is a java backend project for an application to schedule visits to dieticians
2. #### **Link to last commit**
	https://github.com/mwiski/dietician-office/commit/547bd90fa4ce163409e4bb0b4036eff6fb57744a
3. #### **Requirements before running**
	- The application has been created using Java 8, Gradle, MySql and H2 databases, Spring and Hibernate frameworks, runs on the embedded Tomcat server. The detailed information is included in build.gradle.
	- Before running the application make sure you have the appropriate software installed. Credentials for the databases are listed in application.properties file. Remember to change the credentials in case you use other username, password, project name, also check the server timezone. 
	- Add your api.key to secure endpoints. You can create accounts in edamam and spoonacular applications to see their functionality. This application uses mentioned apps to generate recipes and check foods values.
	- When running the application make sure that your database is closed or uses different port.
4. #### **Running the application**
    - Application is set to run on port 8081
	- Before running the application build it using 'gradlew build' command in the terminal of your IDE. To start the application run a file called DieticianOfficeApplication situated in the package 'pl.mwiski.dieticianoffice'.
	- Remember to add your app.key at the end of paths for example when testing app with Postman
5. #### **Endpoints**
    After running the application go to http://localhost:8081/swagger-ui.html for API documentation and comprehensive description of the endpoints.
5. #### **Frontend**
    Link to github project with sample frontend to Dietician office application
    https://github.com/mwiski/dietician-office-frontend
