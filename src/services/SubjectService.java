package services;

import academicinfo.Subject;
import java.util.HashMap;
import java.util.Map;

public class SubjectService {
    private Map<String, Subject> subjects;

    public SubjectService() {
        subjects = new HashMap<>();
    }

    public void addSubject(Subject subject) {
        subjects.put(subject.getId(), subject);
    }

    public Subject getSubject(String id) {
        return subjects.get(id);
    }

    // Methods for managing subjects
}
