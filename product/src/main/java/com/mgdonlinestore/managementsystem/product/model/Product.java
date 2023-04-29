package com.mgdonlinestore.managementsystem.product.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//import javax.persistence.*;

/**
 * @author Khaled ElGohary
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
//@DynamicInsert
//@DynamicUpdate
//@Entity
//@Table(name = "products")
@Document
public class Product {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(unique = true, nullable = false)
    private String id;
    private String productName;
}
