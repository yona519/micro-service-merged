package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectStatusOutput {
    private Long proj_status_id;

    private String proj_status_name;
    private String proj_status_description;

    private int del_flag;

}
