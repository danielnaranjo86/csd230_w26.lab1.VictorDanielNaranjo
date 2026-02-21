package csd230.lab1.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@Entity @DiscriminatorValue("MAGAZINE")
public class MagazineEntity extends PublicationEntity {
    private int orderQty;
    private LocalDateTime currentIssue;
    public MagazineEntity() {}
    //public MagazineEntity(String t, Double p, Integer c, Integer o, LocalDateTime d) { super(t, p, c); this.orderQty = o; this.currentIssue = d; }
    public int getOrderQty() { return orderQty; }
    public void setOrderQty(int o) { this.orderQty = o; }
    public void setCurrentIssue(LocalDateTime d) { this.currentIssue = d; }
    public LocalDateTime getCurrentIssue() { return currentIssue; }
    @Override public String toString() { return "Mag{issue=" + currentIssue + ", " + super.toString() + "}"; }
}
