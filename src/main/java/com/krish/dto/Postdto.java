package com.krish.dto;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class Postdto {
    private String profile;
    private String type;
    private String description;
    private String experience;
    private List<String> technology;
    private String salary;
    
    public Postdto() {}
    
    public List<String> getTechnology() {
        return technology;
    }

    public void setTechnology(List<String> technology) {
        this.technology = technology;
    }
}

