package com.example.app.nynews.dataClasses.model.response


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ResponseArticlesList(
    @SerializedName("status") var status: String, // OK
    @SerializedName("response") var response: Response
) {
    @Keep
    data class Response(
        @SerializedName("docs") var docs: ArrayList<NewsList>?
    ) {
        @Keep
//        @Entity(
//            tableName = "articles"
//        )
        data class NewsList(
//            @PrimaryKey(autoGenerate = true)
//            var id: Int? = null,
            @SerializedName("abstract") var abstract: String,
            @SerializedName("web_url") var webUrl: String, // https://www.nytimes.com/2012/11/29/garden/sales-at-leucos-usa-luceplan-usa-and-others.html
            @SerializedName("lead_paragraph") var leadParagraph: String, // Leucos USA and Luceplan USA are having a warehouse sale from Friday to Sunday, with up to 70 percent off lighting and accessories (the Gio Ponti sconce for Leucos, regularly $3,520, will be $1,056; the Berenice table LED for Luceplan, regularly $590, will be $236); 70-72 Wooster Street (Broome Street), (212) 966-1399.
            @SerializedName("source") var source: String, // The New York Times
            @SerializedName("pub_date") var pubDate: String, // 2012-11-28T23:16:06+0000
            @SerializedName("type_of_material") var typeOfMaterial: String, // News
            @SerializedName("_id") var Id: String, // nyt://article/6fc772e5-9753-5dcd-8111-4e5115622d24
            @SerializedName("word_count") var wordCount: Int, // 147
            @SerializedName("uri") var uri: String // nyt://article/6fc772e5-9753-5dcd-8111-4e5115622d24
        )
    }
}