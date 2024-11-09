package com.upiiz.equipo.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponseEquipo<T> {

    private int estado;
    private String msg;
    private T equipos;
    private List<Link> links;

}
