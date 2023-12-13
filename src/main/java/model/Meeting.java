package model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Meeting")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meetingId")
    private int meetingId;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "dateAndTime")
    private java.sql.Timestamp dateAndTime;

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "agenda", columnDefinition = "TEXT")
    private String agenda;

    @Column(name = "durationMinutes")
    private int durationMinutes;

    @ManyToMany
    @JoinTable(
            name = "Attendance",
            joinColumns = @JoinColumn(name = "meetingId"),
            inverseJoinColumns = @JoinColumn(name = "volunteerId")
    )
    private Set<Volunteer> volunteers;

    // Default constructor required by JPA
    public Meeting() {
    }

    // Constructor for essential fields
    public Meeting(int meetingId, String title, java.sql.Timestamp dateAndTime,
                   String location, String agenda, int durationMinutes) {
        this.meetingId = meetingId;
        this.title = title;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.agenda = agenda;
        this.durationMinutes = durationMinutes;
    }

    // Getters and setters

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public java.sql.Timestamp getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(java.sql.Timestamp dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Set<Volunteer> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(Set<Volunteer> volunteers) {
        this.volunteers = volunteers;
    }

    // Other methods if needed
}
