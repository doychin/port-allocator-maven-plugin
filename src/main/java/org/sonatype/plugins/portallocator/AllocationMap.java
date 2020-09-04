package org.sonatype.plugins.portallocator;

import java.util.*;

public class AllocationMap {

    private final Map<String, List<Integer>> allocatedPortsMap;

    private final Set<Integer> allocatedPorts;

    @SuppressWarnings("unchecked")
    public AllocationMap(Map<String, Object> pluginContextMap) {
        synchronized (pluginContextMap) {
            if (pluginContextMap.get("allocatedPortsMap") != null) {
                allocatedPortsMap = (Map<String, List<Integer>>) pluginContextMap.get("allocatedPortsMap");
            } else {
                allocatedPortsMap = new HashMap<String, List<Integer>>();
                pluginContextMap.put("allocatedPortsMap", allocatedPortsMap);
            }

            if (pluginContextMap.get("allocatedPorts") != null) {
                allocatedPorts = (Set<Integer>) pluginContextMap.get("allocatedPorts");
            } else {
                allocatedPorts = new HashSet<Integer>();
                pluginContextMap.put("allocatedPorts", allocatedPorts);
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
