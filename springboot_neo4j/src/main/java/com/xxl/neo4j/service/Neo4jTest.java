package com.xxl.neo4j.service;

import org.neo4j.driver.*;

import static org.neo4j.driver.Values.parameters;

/**
 * @author xxl
 * @date 2023/11/26 22:00
 */
public class Neo4jTest {

    public static void main(String[] args) {
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "Yinlidong1995."));
        Session session = driver.session();
        session.run("CREATE (n:Part {name: {name},title: {title}})",
                parameters("name", "Arthur001", "title", "King001"));
        Result result = session.run("MATCH (a:Part) WHERE a.name = {name} " +
                        "RETURN a.name AS name, a.title AS title",
                parameters("name", "Arthur001"));
        while (result.hasNext()) {
            Record record = result.next();
            System.out.println(record.get("title").asString() + " " + record.get("name").asString());
        }
        session.close();
        driver.close();
    }
}
