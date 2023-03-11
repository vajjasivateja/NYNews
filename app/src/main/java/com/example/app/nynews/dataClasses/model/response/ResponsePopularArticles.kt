package com.example.app.nynews.dataClasses.model.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ResponsePopularArticles(
    @SerializedName("status") var status: String, // OK
    @SerializedName("results") var results: List<Result>?
) {
    @Keep
    data class Result(
        @SerializedName("uri") var uri: String, // nyt://article/ee87e65a-745e-57c1-9d49-80a7de991c54
        @SerializedName("url") var url: String, // https://www.nytimes.com/2023/03/02/nyregion/hotel-pennsylvania-nyc.html
        @SerializedName("id") var id: Long, // 100000008785846
        @SerializedName("source") var source: String, // New York Times
        @SerializedName("published_date") var publishedDate: String, // 2023-03-02
        @SerializedName("updated") var updated: String, // 2023-03-09 06:00:49
        @SerializedName("section") var section: String, // New York
        @SerializedName("byline") var byline: String, // By Dan Barry
        @SerializedName("title") var title: String, // Once the World’s Largest, a Hotel Goes ‘Poof!’ Before Our Eyes
        @SerializedName("abstract") var `abstract`: String // The Hotel Pennsylvania in Manhattan was a virtual city within a city. But in the end, nothing could save it.
    )
}