package com.hfad.thinder.data.source.remote;

import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.University;
import com.hfad.thinder.data.source.remote.okhttp.UniversityApiService;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Only an admin would have access the this remoteDataSource to check the validity of
 * added universities and to change previously instantiated regexes of student/supervisor emails.
 */
public class UniversityRemoteDataSource {

}
