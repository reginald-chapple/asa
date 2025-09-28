package com.asa.web.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "predictions")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prediction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question", nullable = false, length = 500)
    private String question;

    @Column(name = "answer", nullable = false, length = 500)
    private String answer;

    @Column(name = "explanation", length = 1000)
    private String explanation;

    @Column(name = "confidence_score", nullable = false)
    private Double confidenceScore;

    @Version // optimistic locking column
    private Long version;

}
