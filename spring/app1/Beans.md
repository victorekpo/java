## Beans
1. @Component: This is a generic stereotype for any Spring-managed component. It is a general-purpose annotation that can be used to mark any class as a Spring bean. It is the base annotation for other specialized annotations like @Service, @Repository, and @Controller.  

2. @Service: This is a specialization of @Component. It is used to annotate classes that perform service tasks, typically containing business logic. Using @Service makes the intent of the class clearer and can also provide additional functionalities specific to service classes.
   @Component: This is a generic stereotype for any Spring-managed component. It is a general-purpose annotation that can be used to mark any class as a Spring bean. It is the base annotation for other specialized annotations like @Service, @Repository, and @Controller.  
   @Service: This is a specialization of @Component. It is used to annotate classes that perform service tasks, typically containing business logic. Using @Service makes the intent of the class clearer and can also provide additional functionalities specific to service classes.

## Do Components Run Automatically?
Components do not run automatically. They are instantiated and managed by the Spring container, but their methods are only executed when they are explicitly called by other components, such as controllers, other services, or scheduled tasks.

The OpenSearchInitializer component runs automatically because it implements the CommandLineRunner interface. In a Spring Boot application, any bean that implements CommandLineRunner will have its run method executed after the application context is loaded and right before the Spring Boot application starts.