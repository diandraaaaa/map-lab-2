package model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Participant")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participantId")
    private int participantId;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "birthDay")
    private java.sql.Date birthDay;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "phone", length = 10)
    private String phone;

    @Column(name = "registrationDate")
    private java.sql.Date registrationDate;

    @Column(name = "additionalNotes", columnDefinition = "TEXT")
    private String additionalNotes;

    @ManyToMany
    @JoinTable(
            name = "ParticipantProject",
            joinColumns = @JoinColumn(name = "participantId"),
            inverseJoinColumns = @JoinColumn(name = "projectId")
    )
    private Set<Project> projects;

    @ManyToMany
    @JoinTable(
            name = "ParticipantSkill",
            joinColumns = @JoinColumn(name = "participantId"),
            inverseJoinColumns = @JoinColumn(name = "skillId")
    )
    private Set<Skill> skills;

    // Default constructor required by JPA
    public Participant() {
    }

    // Constructor for essential fields
    public Participant(int participantId, String name, java.sql.Date birthDay, String email,
                       String phone, java.sql.Date registrationDate, String additionalNotes) {
        this.participantId = participantId;
        this.name = name;
        this.birthDay = birthDay;
        this.email = email;
        this.phone = phone;
        this.registrationDate = registrationDate;
        this.additionalNotes = additionalNotes;
    }

    // Getters and setters

    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.sql.Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(java.sql.Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public java.sql.Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(java.sql.Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    // Other methods if needed
}
