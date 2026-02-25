package kr.co.choi.property_manager.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "property_photos")
public class PropertyPhoto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private String storedName;   // UUID 파일명

    @Column(nullable = false)
    private String url;     // 화면에서 접근할 URL (/uploads/xxxx.jpg)

    private LocalDateTime createdAt;

    protected PropertyPhoto() {}

    public PropertyPhoto(Property property, String originalName, String storedName, String url) {
        this.property = property;
        this.originalName = originalName;
        this.storedName = storedName;
        this.url = url;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {return id;}
    public String getUrl() {return url;}
    public String getOriginalName() {return originalName;}
    public Property getProperty() {return property;}

}
