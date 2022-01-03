/*
 * This Java Quick Start uses the jackrabbit-standalone-2.4.0.jar
 * file. See the previous section for the location of this JAR file
 */

import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Node;

import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.jackrabbit.core.TransientRepository;

public class GetNodes {

	public static void main(String[] args) throws Exception {

		try {

			// Create a connection to the CQ repository running on local host
			Repository repository = JcrUtils.getRepository("http://localhost:4502/crx/server");

			// Create a Session
			javax.jcr.Session session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));

			// Create a node that represents the root node
			Node root = session.getRootNode();

			// Store content
			//  Node adobe = root.addNode("adobe"); 
			//  Node day = adobe.addNode("day"); 
			//  day.setProperty("message", "Adobe CQ is part of the Adobe Digital Marketing Suite!");

			// Retrieve content
			Node node = root.getNode("content/we-retail/us/en/experience/jcr:content");
			System.out.println(node.getPath());
			System.out.println(node.getProperty("jcr:title").getString());

			// Save the session changes and log out
			session.save();
			session.logout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
