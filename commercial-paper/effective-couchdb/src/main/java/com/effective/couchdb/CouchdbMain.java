package com.effective.couchdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;

import java.io.IOException;
import java.time.Instant;

public class CouchdbMain {

    private static final String JSON_DATA_2KB = "[{\"_id\":\"61168a491c224ccf6a8bd73b\",\"expression\":0,\"equalsValues\":[\"voluptate\",\"consectetur\"],\"notEqualsValues\":[]},{\"_id\":\"61168a49f30272db5dc8a932\",\"expression\":1,\"equalsValues\":[\"culpa\"],\"notEqualsValues\":[]},{\"_id\":\"61168a49101ed1406668b520\",\"expression\":2,\"equalsValues\":[\"magna\"],\"notEqualsValues\":[]},{\"_id\":\"61168a49a40d4219cad0a2d5\",\"expression\":3,\"equalsValues\":[\"exercitation\"],\"notEqualsValues\":[]},{\"_id\":\"61168a49e68bdf1399b9db65\",\"expression\":4,\"equalsValues\":[\"ad\"],\"notEqualsValues\":[]},{\"_id\":\"61168a497126f925a633e2ee\",\"expression\":5,\"equalsValues\":[\"occaecat\",\"qui\"],\"notEqualsValues\":[]},{\"_id\":\"61168a49cf43317a82528242\",\"expression\":6,\"equalsValues\":[\"eu\",\"ullamco\"],\"notEqualsValues\":[]},{\"_id\":\"61168a498c1ca5bd531bc5ff\",\"expression\":7,\"equalsValues\":[\"dolor\"],\"notEqualsValues\":[]},{\"_id\":\"61168a4993c2c825e4c45f71\",\"expression\":8,\"equalsValues\":[\"laborum\",\"ut\"],\"notEqualsValues\":[]},{\"_id\":\"61168a49b5b836b82a8bd4bc\",\"expression\":9,\"equalsValues\":[\"qui\",\"ad\"],\"notEqualsValues\":[]},{\"_id\":\"61168a498548b7a846435d2e\",\"expression\":10,\"equalsValues\":[\"sit\",\"exercitation\"],\"notEqualsValues\":[]},{\"_id\":\"61168a49b7a1b145b522a320\",\"expression\":11,\"equalsValues\":[\"amet\"],\"notEqualsValues\":[]},{\"_id\":\"61168a492e8fb750f3a14513\",\"expression\":12,\"equalsValues\":[\"exercitation\"],\"notEqualsValues\":[]},{\"_id\":\"61168a49bc5cca62221f9869\",\"expression\":13,\"equalsValues\":[\"aute\",\"aute\"],\"notEqualsValues\":[]},{\"_id\":\"61168a4958dba6baafac1324\",\"expression\":14,\"equalsValues\":[\"nostrud\"],\"notEqualsValues\":[]},{\"_id\":\"61168a49186abf55d3b3f310\",\"expression\":15,\"equalsValues\":[\"dolor\",\"laborum\"],\"notEqualsValues\":[]},{\"_id\":\"61168a4960c6c3404edde37b\",\"expression\":16,\"equalsValues\":[\"sunt\",\"ad\"],\"notEqualsValues\":[]},{\"_id\":\"61168a4981c01b4596106b85\",\"expression\":17,\"equalsValues\":[\"nisi\",\"ipsum\"],\"notEqualsValues\":[]},{\"_id\":\"61168a494ea2ceeb9e997693\",\"expression\":18,\"equalsValues\":[\"eu\"],\"notEqualsValues\":[]},{\"_id\":\"61168a4921f72ff8b4f6d1b8\",\"expression\":19,\"equalsValues\":[\"irure\",\"nulla\"],\"notEqualsValues\":[]}]";

    public static void main(String[] args) throws IOException {
        HttpClient httpClient = new StdHttpClient.Builder()
                .url("http://localhost:5984")
                .build();

        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        CouchDbConnector db = new StdCouchDbConnector("events", dbInstance);
        db.createDatabaseIfNotExists();
        //createMany(20_000, db);
    }

    private static void queryMany(CouchDbConnector db) {
        long start = 1628948802616L;
        long end = 1628949195634L;
        //...ToDo
    }

    private static void createMany(int n, CouchDbConnector db) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Object data = mapper.readValue(JSON_DATA_2KB, Object.class);
        for (long i = 0; i < n; i++) {
            System.out.println(i);
            String id = i + "";
            Event event = new Event();
            event.setId(id);
            event.setData(data);
            event.setCreatedAt(Instant.now().toEpochMilli());
            String type = i % 2 == 0 ? "Type_A" : "Type_B";
            event.setType(type);
            db.create(id, event);
        }
    }
}
