package kr.co.choi.property_manager.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String address;

    // 지도 핵심 좌표
    private Double lat;
    private Double lng;

    // 가격
    @Column(nullable = false)
    private Long price;

    // 유형
    @Enumerated(EnumType.STRING)
    @Column(nullable =false)
    private PropertyType type;

    // 상태 (거래중/완료)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyStatus status;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt DESC")
    private List<Memo> memos = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected Property() { }

    public Property(String title, String address, Long price, PropertyType type,PropertyStatus status) {
        this.title = title;
        this.address = address;
        this.price = price;
        this.type = type;
        this.status = status;
    }

    @PrePersist
    void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    void perUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // == 연관관계 편의 메서드 ===
    public void addMemo(Memo memo) {
        this.memos.add(memo);
        memo.setProperty(this);
    }

    public void removeMemo(Memo memo) {
        this.memos.add(memo);
        memo.setProperty(null);
    }

    // getters (일단 필요한 것만)
    public Long getId() {return id;}
    public String getTitle() {return title;}
    public String getAddress() {return address;}
    public Double getLat() {return lat;}
    public Double getLng() {return lng;}
    public Long getPrice() {return price;}
    public PropertyType getType() {return type;}
    public PropertyStatus getStatus() {return status;}
    public List<Memo> getMemo() {return memos;}

    // 좌표 업데이트용 (지오코딩 붙일 때 씀)
    public void updateLocation(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public void updateBasic(String title, String address, Long price, PropertyType type, PropertyStatus status ) {
        this.title = title;
        this.address = address;
        this.price = price;
        this.type = type;
        this.status = status;
    }


} 