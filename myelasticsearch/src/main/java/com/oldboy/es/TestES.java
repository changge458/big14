package com.oldboy.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestES {
    public static void main(String[] args) throws Exception {

//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
//        searchSourceBuilder.aggregation(AggregationBuilders.terms("top_10_states").field("state").size(10));
//        SearchRequest searchRequest = new SearchRequest();
//        searchRequest.indices("social-*");
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse searchResponse = client.search(searchRequest);


        RestClientBuilder builder = RestClient.builder(
                new HttpHost("s102", 9200),
                new HttpHost("s103", 9200),
                new HttpHost("s104", 9200)
        );

        RestHighLevelClient client = new RestHighLevelClient(builder);

        SearchRequest searchRequest = new SearchRequest();
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        String s1 = response.toString();
        System.out.println(s1);


//        GetRequest request = new GetRequest("logstash-2019.03.29", "doc", "7gx1yGkBoe3IWymWtN2b");
//        GetResponse get = client.get(request, RequestOptions.DEFAULT);
//
//        String s = get.getSourceAsString();
//        System.out.println(s);

        client.close();
    }
}
