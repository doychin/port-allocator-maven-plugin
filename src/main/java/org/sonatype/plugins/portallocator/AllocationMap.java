package org.sonatype.plugins.portallocator;

import java.util.*;

public class AllocationMap {

    public static final String ALLOCATED_PORTS_MAP = "allocatedPortsMap";

    public static final String ALLOCATED_PORTS = "allocatedPorts";

    private static final Map<String, Object> sharedMap = new HashMap<String, Object>();

    private final Map<String, List<Integer>> allocatedPortsMap;

    private final Set<Integer> allocatedPorts;

    @SuppressWarnings("unchecked")
    public AllocationMap() {
        synchronized (sharedMap) {
            if (sharedMap.get(ALLOCATED_PORTS_MAP) != null) {
                allocatedPortsMap = (Map<String, List<Integer>>) sharedMap.get(ALLOCATED_PORTS_MAP);
            } else {
                allocatedPortsMap = new HashMap<String, List<Integer>>();
                sharedMap.put(ALLOCATED_PORTS_MAP, allocatedPortsMap);
            }

            if (sharedMap.get(ALLOCATED_PORTS) != null) {
                allocatedPorts = (Set<Integer>) sharedMap.get(ALLOCATED_PORTS);
            } else {
                allocatedPorts = new HashSet<Integer>();
                sharedMap.put(ALLOCATED_PORTS, allocatedPorts);
            }
        }
    }

    public void registerPort(int portNumber, String projectName) {
        synchronized (allocatedPortsMap) {
            List<Integer> portsList = allocatedPortsMap.get(projectName);
            if (portsList == null) {
                portsList = new ArrayList<Integer>();
                allocatedPortsMap.put(projectName, portsList);
            }
            portsList.add(portNumber);
        }
        synchronized (allocatedPorts) {
            allocatedPorts.add(portNumber);
        }
    }

    public boolean contains(int portNumber) {
        synchronized (allocatedPorts) {
            return allocatedPorts.contains(portNumber);
        }
    }

    public List<Integer> releasePorts(String name) {
        List<Integer> portList;
        synchronized (allocatedPortsMap) {
            portList = allocatedPortsMap.remove(name);
            if (portList == null || portList.isEmpty()) {
                return null;
            }
        }

        synchronized (allocatedPorts) {
            allocatedPorts.removeAll(portList);
        }
        return portList;
    }
}
