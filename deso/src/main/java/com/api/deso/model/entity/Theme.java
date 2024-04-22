package com.api.deso.model.entity;

import lombok.*;
import lombok.experimental.Accessors;


import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@ToString(exclude = {"themeEventList"})
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String content;

    private Long type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "theme")
    List<ThemeEvent> themeEventList;



}
