package model;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contractId")
    private int contractId;

    @Column(name = "startDate")
    private Date startDate;

    @Column(name = "endDate")
    private Date endDate;

    @Column(name = "volunteerId", unique = true)
    private int volunteerId;

    @OneToOne
    @JoinColumn(name = "volunteerId", referencedColumnName = "volunteerId", insertable = false, updatable = false)
    private Volunteer volunteer;

    // Default constructor required by JPA
    public Contract() {
    }

    // Constructor for essential fields
    public Contract(int contractId, Date startDate, Date endDate, int volunteerId) {
        this.contractId = contractId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.volunteerId = volunteerId;
    }

    // Getters and setters

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(int volunteerId) {
        this.volunteerId = volunteerId;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    // Other methods if needed
}
