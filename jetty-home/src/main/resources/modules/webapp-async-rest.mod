# DO NOT EDIT - See: https://www.eclipse.org/jetty/documentation/current/startup-modules.html

[description]
Download and deploy the Async Rest webapp demo.

[tags]
demo
webapp

[depends]
deploy

[files]
maven://org.eclipse.jetty.example-async-rest/example-async-rest-webapp/${jetty.version}/war|webapps/async-rest.war

