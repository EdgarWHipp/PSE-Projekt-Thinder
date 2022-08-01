package com.hfad.thinder.data.source.remote;

import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.University;
import com.hfad.thinder.data.source.remote.okhttp.UniversityApiService;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Only an admin would have access the this remoteDataSource to check the validity of
 * added universities and to change previously instantiated regexes of student/supervisor emails.
 */
public class UniversityRemoteDataSource {

}
