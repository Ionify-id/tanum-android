package com.tanum.app.model.remote.retrofit

import com.tanum.app.model.data.body.AktivitasBody
import com.tanum.app.model.data.body.LahanBody
import com.tanum.app.model.data.body.LoginBody
import com.tanum.app.model.data.body.RegisterBody
import com.tanum.app.model.remote.response.AktivitasResponse
import com.tanum.app.model.remote.response.ArtikelResponse
import com.tanum.app.model.remote.response.DeleteResponse
import com.tanum.app.model.remote.response.DetailAktivitasResponse
import com.tanum.app.model.remote.response.DetailLahanResponse
import com.tanum.app.model.remote.response.LahanResponse
import com.tanum.app.model.remote.response.ListAktivitasResponse
import com.tanum.app.model.remote.response.ListArtikelResponse
import com.tanum.app.model.remote.response.ListLahanResponse
import com.tanum.app.model.remote.response.ListVideoResponse
import com.tanum.app.model.remote.response.LoginResponse
import com.tanum.app.model.remote.response.ProfileResponse
import com.tanum.app.model.remote.response.RegisterResponse
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
    suspend fun getListLand(
        @Header("Authorization") auth: String,
        @Query("page") page: Int,
        @Query("take") take: Int
    ): ListLahanResponse

    // get detail lahan
    @GET("lands/{id}")
    suspend fun getDetailLand(
        @Path("id") id: Int,
        @Header("Authorization") auth: String
    ): DetailLahanResponse

    // delete lahan
    @DELETE("lands/{id}")
    suspend fun deleteLand(
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
        @Header("Authorization") auth: String,
        @Query("page") page: Int,
        @Query("take") take: Int
    ): ListAktivitasResponse

    @GET("activities/detail/{activityId}")
    suspend fun getDetailActivities(
        @Path("activityId") activityId: Int,
        @Header("Authorization") auth: String
    ): DetailAktivitasResponse

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
        @Header("Authorization") auth: String,
        @Query("page") page: Int,
        @Query("take") take: Int
    ): ListArtikelResponse

    // get article detail
    @GET("articles/{articleId}")
    suspend fun getArticleDetail(
        @Header("Authorization") auth: String,
        @Path("articleId") articleId: Int
    ): ArtikelResponse

    /*
        Video Service
     */

    // get list video
    @GET("videos")
    suspend fun getListVideo(
        @Header("Authorization") auth: String,
        @Query("page") page: Int,
        @Query("take") take: Int
    ): ListVideoResponse

}