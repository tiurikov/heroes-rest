package com.heroes.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableUndirectedGraph;

/**
 *
 * @author Stanislav Tyurikov
 */
public class InMemoryHeroRepository implements HeroRepository
{
    private final Map<String, Hero> heroesIndex;
    private final NeighborIndex<String, DefaultEdge> allianceIndex;
    private final ListenableUndirectedGraph<String, DefaultEdge> allicaneGraph;


    public InMemoryHeroRepository()
    {
        this.heroesIndex = new HashMap<>();
        this.allicaneGraph = new ListenableUndirectedGraph<>(DefaultEdge.class);
        this.allianceIndex = new NeighborIndex<>(allicaneGraph);
        this.allicaneGraph.addGraphListener(allianceIndex);
    }


    @Override
    public Hero findHero(String pseudonym)
    {
        return heroesIndex.getOrDefault(pseudonym, UNKNOWN_HERO);
    }


    @Override
    public void saveHero(String pseudonym, Hero hero)
    {
        heroesIndex.put(pseudonym, hero);
        allicaneGraph.addVertex(pseudonym);
    }


    @Override
    public void removeHero(String pseudonym)
    {
        assertExists(pseudonym);

        heroesIndex.remove(pseudonym);
        allicaneGraph.removeVertex(pseudonym);
    }


    @Override
    public Set<String> findAllHeroes()
    {
        return heroesIndex.keySet();
    }


    @Override
    public void unite(String pseudonymX, String pseudonymY)
    {
        assertExists(pseudonymX, pseudonymY);

        try {
            allicaneGraph.addEdge(pseudonymY, pseudonymX);
        } catch (Exception e) {
            throw new HeroRepositoryException(
                    "Unable to create alliance for hero '" + pseudonymX + "' and hero '" + pseudonymY + "'");
        }
    }


    @Override
    public void disunite(String pseudonymX, String pseudonymY)
    {
        assertExists(pseudonymX, pseudonymY);

        allicaneGraph.removeEdge(pseudonymY, pseudonymX);
    }


    @Override
    public Set<String> findAllies(String pseudonym)
    {
        assertExists(pseudonym);
        return allianceIndex.neighborsOf(pseudonym);
    }


    private void assertExists(String... pseudonyms)
    {
        for (String pseudonym : pseudonyms) {
            if (!allicaneGraph.containsVertex(pseudonym)) {
                throw new HeroRepositoryException("Unknown Hero '" + pseudonym + "'");
            }
        }
    }


    @Override
    public void deleteAll()
    {
        allicaneGraph.removeAllVertices(heroesIndex.keySet());
        heroesIndex.clear();
    }
}
