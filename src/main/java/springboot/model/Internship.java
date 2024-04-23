package springboot.model;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
public class Internship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String description;
    LocalDate dateBegin;
    LocalDate dateEnd;
    LocalDate dateEndRecording;
    String state;

    public Internship(){}

    public Internship(Long id, String name, String description, LocalDate dateBegin, LocalDate dateEnd, LocalDate dateEndRecording, String state) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.dateEndRecording = dateEndRecording;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(LocalDate dateBegin) {
        this.dateBegin = dateBegin;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public LocalDate getDateEndRecording() {
        return dateEndRecording;
    }

    public void setDateEndRecording(LocalDate dateEndRecording) {
        this.dateEndRecording = dateEndRecording;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
