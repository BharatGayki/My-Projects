package com.example.ecommerce.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category" , uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long category_id; ;

    @NotBlank(message = "category name is required")
    @Size(max = 15 , min = 2, message = "Category name must be between 2 and 15 characters")
    private String name ;

    private boolean is_deleted ;

    private boolean is_activated;


    public Category(String name) {
        this.name = name;
        this.is_deleted = false;
        this.is_activated = true;

    }


}
