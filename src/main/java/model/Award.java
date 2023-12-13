package model;

import jakarta.persistence.*;

@Entity
@Table(name = "Award")
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "awardId")
    private int awardId;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "dateReceived")
    private java.sql.Date dateReceived;

    @OneToOne
    @JoinColumn(name = "volunteerId", unique = true)
    private Volunteer volunteer;

    // Default constructor required by JPA
    public Award() {
    }

    // Constructor for essential fields
    public Award(int awardId, String name, String description, java.sql.Date dateReceived) {
        this.awardId = awardId;
        this.name = name;
        this.description = description;
        this.dateReceived = dateReceived;
    }

    // Getters and setters

    public int getAwardId() {
        return awardId;
    }

    public void setAwardId(int awardId) {
        this.awardId = awardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.sql.Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(java.sql.Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    // Other methods if needed
}
