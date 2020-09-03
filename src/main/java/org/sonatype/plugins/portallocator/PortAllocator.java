package org.sonatype.plugins.portallocator;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

/**
 * Interface defining a port allocator.
 *
 * @author Ross M. Lodge
 */
public interface PortAllocator {

	int MAX_PORT = 65535;

	boolean allocatePort(int portNumber) throws MojoExecutionException;

	MavenProject getProject();

}
