package services;

import academicinfo.Group;
import java.util.HashMap;
import java.util.Map;

public class GroupService {
    private Map<String, Group> groups;

    public GroupService() {
        groups = new HashMap<>();
    }

    public void addGroup(Group group) {
        groups.put(group.getId(), group);
    }

    public Group getGroup(String id) {
        return groups.get(id);
    }

    // Methods for managing groups
}
