package com.just_me.just_we.lastfmclient.entity
import com.google.gson.annotations.SerializedName


data class Artist(
        @SerializedName("name") val name: String = "",
        @SerializedName("mbid") val mbid: String = "",
        @SerializedName("url") val url: String = "",
        @SerializedName("image") val image: List<Image> = listOf(),
        @SerializedName("streamable") val streamable: String = "",
        @SerializedName("ontour") val ontour: String = "",
        @SerializedName("stats") val stats: Stats = Stats(),
        @SerializedName("similar") val similar: Similar = Similar(),
        @SerializedName("tags") val tags: Tags = Tags(),
        @SerializedName("bio") val bio: Bio = Bio()
)

data class Image(
    @SerializedName("#text") val url: String = "",
    @SerializedName("size") val size: String = ""
)


data class Tags(
        @SerializedName("tag") val tag: List<Tag> = listOf()
)

data class Tag(
        @SerializedName("name") val name: String = "",
        @SerializedName("url") val url: String = ""
)

data class Stats(
        @SerializedName("listeners") val listeners: String = "",
        @SerializedName("playcount") val playcount: String = ""
)

data class Bio(
        @SerializedName("links") val links: Links = Links(),
        @SerializedName("published") val published: String = "",
        @SerializedName("summary") val summary: String = "",
        @SerializedName("content") val content: String = ""
)

data class Links(
        @SerializedName("link") val link: Link = Link()
)

data class Link(
        @SerializedName("#text") val text: String = "",
        @SerializedName("rel") val rel: String = "",
        @SerializedName("href") val href: String = ""
)

data class Similar(
        @SerializedName("artist") val artist: List<Artist> = listOf()
)