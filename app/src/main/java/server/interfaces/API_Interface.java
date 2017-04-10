package server.interfaces;

import model.fields;
import model.home;
import model.modelsTopic;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API_Interface {

    String URL_TOPQUEUE_BASE = "http://main.topqueue.in";

    @GET("/api/topics")
    Call<modelsTopic> getTopics(

    );

    @GET("/api/topics/{topic_id}")
    Call<modelsTopic> getIdTopics(
            @Path("topic_id") int topicId
    );

    @GET("/api/fields")
    Call<fields> getfields(
    );

    @GET("/api/fields/{field_id}")
    Call<fields> getIdfields(
            @Path("field_id") int fieldId
    );

    @GET("/api/home")
    Call<home> getrecentandpopular(
    );







    class Factory {

        private static API_Interface service;

        public static API_Interface getInstance() {
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(URL_TOPQUEUE_BASE)
                        .build();

                service = retrofit.create(API_Interface.class);
                return service;
            } else {
                return service;
            }
        }
    }


}
