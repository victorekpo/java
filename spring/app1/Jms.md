### Benefits of Using JMS
Java Message Service (JMS) is a powerful API for messaging between applications. Here are some key benefits of using JMS:  
1. Asynchronous Communication:  
JMS allows applications to communicate asynchronously, meaning the sender and receiver do not need to interact with the message queue at the same time.
2. Reliability:  
JMS ensures reliable message delivery through features like persistent messaging, transactions, and acknowledgments.
3. Decoupling:  
JMS decouples the sender and receiver, allowing them to operate independently. This leads to more modular and maintainable code.
4. Scalability:  
JMS supports both point-to-point (queues) and publish-subscribe (topics) messaging models, making it suitable for various use cases and scalable architectures.
5. Transaction Support:  
JMS supports transactions, ensuring that a series of operations either complete successfully or fail together, maintaining data integrity.
6. Message Acknowledgment:  
JMS provides different acknowledgment modes (AUTO_ACKNOWLEDGE, CLIENT_ACKNOWLEDGE, DUPS_OK_ACKNOWLEDGE) to ensure messages are processed correctly and not lost.

### Importance of Keeping JMS with SQS
While Amazon Simple Queue Service (SQS) is a fully managed message queuing service, integrating it with JMS provides several advantages:  
1. Standard API:  
JMS provides a standard API for messaging, making it easier to switch between different messaging providers without changing the application code.
2. Advanced Features:  
JMS offers advanced features like message selectors, durable subscriptions, and message-driven beans, which are not natively available in SQS.
3. Acknowledgment Handling:  
JMS handles message acknowledgment more flexibly, allowing for fine-grained control over message processing and ensuring messages are not lost or duplicated.
4. Integration with Java Ecosystem:  
JMS integrates seamlessly with the Java ecosystem, including Spring Boot, making it easier to develop, test, and deploy messaging applications.
5. Enhanced Reliability:  
Combining JMS with SQS leverages the reliability and scalability of SQS while benefiting from the robust features of JMS, resulting in a more resilient messaging system.
By using JMS along with SQS, you can take advantage of the strengths of both technologies, ensuring a robust, scalable, and maintainable messaging solution.

### Resources
- https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/examples-sqs.html
- https://stackoverflow.com/questions/52096153/is-it-mandatory-to-implement-aws-sqs-with-jms-in-java
- https://github.com/awslabs/amazon-sqs-java-messaging-lib
- https://github.com/awslabs/amazon-sqs-java-messaging-lib/pull/177
- https://github.com/awslabs/amazon-sqs-java-messaging-lib/issues/155
- https://repost.aws/questions/QU8sWUtlbmQ5Cc5N82jsNkuw/amazon-sqs-java-messaging-library


### Troubleshooting Steps
- Check if any dependencies are conflicting using
```sh
./mvnw dependency:tree -Dverbose
./mvnw dependency:get -Dartifact=com.amazonaws:amazon-sqs-java-messaging-lib:2.1.3
jar tf ~/.m2/repository/com/amazonaws/amazon-sqs-java-messaging-lib/2.1.3/amazon-sqs-java-messaging-lib-2.1.3.jar
./mvnw dependency:resolve -Dincludes=com.amazonaws:amazon-sqs-java-messaging-lib
```

### Dependencies
```xml
<!-- Amazon SQS Java Messaging Library 2.1.3 (for compatibility with AWS SDK v2) -->
		<dependency>
			<groupId>com.amazonaws</groupId>
			<artifactId>amazon-sqs-java-messaging-lib</artifactId>
			<version>2.1.3</version>
			<scope>compile</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-jms</artifactId>
			<version>6.1.14</version>
		</dependency>

```