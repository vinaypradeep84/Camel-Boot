package com.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "USER")
@ApiModel(description = "Represents an user of the system")

public class User implements Serializable {
    /**
     * version id
     */
    private static final long serialVersionUID = 1L;

    //@ApiModelProperty(value = "The ID of the user", required = false)
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "The name of the user", required = false)
    @NotNull
    private String name;

    public User() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
