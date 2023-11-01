package com.tanum.app.data.remote.retrofit

import com.tanum.app.data.model.body.AktivitasBody
import com.tanum.app.data.model.body.LahanBody
import com.tanum.app.data.model.body.LoginBody
import com.tanum.app.data.model.body.RegisterBody
import com.tanum.app.data.remote.response.AktivitasResponse
import com.tanum.app.data.remote.response.ArtikelResponse
import com.tanum.app.data.remote.response.DeleteResponse
import com.tanum.app.data.remote.response.LahanResponse
import com.tanum.app.data.remote.response.ListAktivitasResponse
import com.tanum.app.data.remote.response.ListArtikelResponse
import com.tanum.app.data.remote.response.ListLahanResponse
import com.tanum.app.data.remote.response.ListVideoResponse
import com.tanum.app.data.remote.response.LoginResponse
import com.tanum.app.data.remote.response.ProfileResponse
import com.tanum.app.data.remote.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    /*
        User Service
     */

    // register
    @POST("auth/register")
    suspend fun register(
        @Body registerBody: RegisterBody
    ): RegisterResponse

    // login
    @POST("auth/login")
    suspend fun login(
        @Body loginBody: LoginBody
    ): LoginResponse

    // user profile
    @GET("users/profile")
    suspend fun getUserProfile(
        @Header("Authorization") auth: String
    ): ProfileResponse

    /*
        Lahan Service
     */

    // create land
    @POST("lands")
    suspend fun createLand(
        @Header("Authorization") auth: String,
        @Body lahanBody: LahanBody
    ): LahanResponse

    // edit land
    @PATCH("lands/{id}")
    suspend fun editLand(
        @Path("id") id: Int,
        @Header("Authorization") auth: String,
        @Body lahanBody: LahanBody
    ): LahanResponse

    // get list lahan
    @GET("lands")
    suspend fun getListLahan(
        @Header("Authorization") auth: String
    ): ListLahanResponse

    // delete lahan
    @DELETE("lahan/{id}")
    suspend fun deleteLahan(
        @Path("id") id: Int,
        @Header("Authorization") auth: String
    ): DeleteResponse

    /*
        Aktivitas Lahan Service
     */

    // create activity
    @POST("activities/{landId}")
    suspend fun createActivity(
        @Path("landId") landId: Int,
        @Header("Authorization") auth: String,
        @Body aktivitasBody: AktivitasBody
    ): AktivitasResponse

    // edit activity
    @PATCH("activities/{activityId}")
    suspend fun editActivity(
        @Path("activityId") activityId: Int,
        @Header("Authorization") auth: String,
        @Body aktivitasBody: AktivitasBody
    ): AktivitasResponse

    // get list activity by land
    @GET("activities/{landId}")
    suspend fun getListActivityByLand(
        @Path("landId") landId: Int,
        @Header("Authorization") auth: String
    ): ListAktivitasResponse

    // delete activity
    @DELETE("activities/{activityId}")
    suspend fun deleteActivity(
        @Path("activityId") activityId: Int,
        @Header("Authorization") auth: String
    ): DeleteResponse

    /*
        Artikel Service
     */

    // get list article
    @GET("articles")
    suspend fun getListArticles(
        @Query("page") page: Int,
        @Query("take") take: Int,
        @Header("Authorization") auth: String
    ): ListArtikelResponse

    // get article detail
    @GET("articles/{articleId}")
    suspend fun getArticleDetail(
        @Path("articleId") articleId: Int,
        @Header("Authorization") auth: String
    ): ArtikelResponse

    /*
        Video Service
     */

    // get list video
    @GET("videos")
    suspend fun getListVideo(
        @Query("page") page: Int,
        @Query("take") take: Int,
        @Header("Authorization") auth: String
    ): ListVideoResponse

}