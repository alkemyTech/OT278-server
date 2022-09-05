package com.alkemy.ong.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "social_medias")
@SQLDelete(sql = "UPDATE social_medias SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SocialMedia {
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "facebook_url", length = 100)
    private String facebookUrl;

    @Column(name = "instagram_url", length = 100)
    private String instagramUrl;

    @Column(name = "linkedin_url", length = 100)
    private String linkedinUrl;

    private Boolean deleted;
}
