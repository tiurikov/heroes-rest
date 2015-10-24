package com.heroes.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Stanislav Tyurikov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hero
{
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date firstAppearance;
    private Set<String> skills;
    private Publisher publisher;
}
