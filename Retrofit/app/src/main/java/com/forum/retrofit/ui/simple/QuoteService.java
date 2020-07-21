package com.forum.retrofit.ui.simple;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface QuoteService {

    @GET("/json")
    Single<QuoteDto> getQuote();

    @GET("/redirect")
    Single<QuoteDto> getQuoteRedirected();

    @GET("/json")
    Single<Response<QuoteDto>> getQuoteEnriched();
}
