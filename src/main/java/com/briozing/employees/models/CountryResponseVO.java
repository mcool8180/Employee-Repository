//package com.briozing.employees.models;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import jdk.nashorn.internal.objects.annotations.Getter;
//import jdk.nashorn.internal.objects.annotations.Setter;
//import lombok.*;
//@JsonIgnoreProperties(ignoreUnknown = true)
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
//public class CountryResponseVO {
//
//
//    private Long id;
//
//    private String name;
//}
package com.briozing.employees.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CountryResponseVO {

    private Long id;

    private String name;
}