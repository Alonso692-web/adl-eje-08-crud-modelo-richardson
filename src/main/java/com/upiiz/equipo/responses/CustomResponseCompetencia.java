package com.upiiz.equipo.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponseCompetencia<T> {

    private int estado;
    private String msg;
    private T competencias;
    private List<Link> links;

}
