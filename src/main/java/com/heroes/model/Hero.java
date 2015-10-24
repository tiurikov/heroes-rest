package com.heroes.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @Size(max = 30, min = 1)
    @NotNull(message = "Hero name mustn't be empty")
    private String name;
    
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date firstAppearance;
    
    @Size(max = 10, min = 1)
    @NotNull(message = "Skill set mustn't be empty")
    private Set<String> skills;
    
    @NotNull(message = "Publisher is required")
    private Publisher publisher;
}
