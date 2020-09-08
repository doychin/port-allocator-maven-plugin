package org.sonatype.plugins.portallocator;

import java.util.List;

import org.apache.maven.plugin.*;
import org.apache.maven.project.MavenProject;

/**
 * Release ports allocated from PortAllocatorMojo
 * @goal release-ports
 * @author Doychin Bondzhev
 * @phase prepare-package
 */
public class PortReleaseMojo extends AbstractMojo {

    /**
     * @parameter property="project"
     * @required
     */
    private MavenProject project;

    public MavenProject getProject() {
        return project;
    }

    public void setProject(MavenProject project) {
        this.project = project;
    }

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().debug("Check for ports to cleanup");

        AllocationMap allocationMap = new AllocationMap();

        List<Integer> portList = allocationMap.releasePorts(project.getName());
        if (portList == null) {
            return;
        }
        getLog().debug("Releasing ports " + portList);

        project.getProperties().setProperty("released.ports", portList.toString());
    }
}
