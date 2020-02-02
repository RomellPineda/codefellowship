package io.romellpineda.codefellowship.models;

import javax.persistence.*;
import java.security.Principal;
import java.util.Date;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public String body;

    @ManyToOne(fetch = FetchType.LAZY)
    ApplicationUser user;

    private Date createdAt;
    private Date updatedAt;

    public Post() {}

    public Post(String body, ApplicationUser user, Date createdAt, Date updatedAt) {
        this.body = body;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Post(ApplicationUser user, String body) {
        this.body = body;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
}
