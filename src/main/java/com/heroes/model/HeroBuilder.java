package com.heroes.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Stanislav Tyurikov
 */
public class HeroBuilder
{
    private String name;
    private Date firstAppearance;
    private Publisher publisher;
    private final Set<String> skills = new HashSet<>();


    public static HeroBuilder aHero()
    {
        return new HeroBuilder();
    }


    public Hero build()
    {
        return new Hero(name, firstAppearance, skills, publisher);
    }


    public HeroBuilder withName(String name)
    {
        this.name = name;
        return this;
    }


    public HeroBuilder withFirstAppearance(Date firstAppearance)
    {
        this.firstAppearance = firstAppearance;
        return this;
    }


    public HeroBuilder withSkill(String skill)
    {
        skills.add(skill);
        return this;
    }


    public HeroBuilder withPublisher(Publisher publisher)
    {
        this.publisher = publisher;
        return this;
    }
}
