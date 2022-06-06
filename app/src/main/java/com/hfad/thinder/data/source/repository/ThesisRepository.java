package com.hfad.thinder.data.source.repository;

import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.remote.ThesisRemoteDataSource;
import com.hfad.thinder.data.source.remote.retrofit.ThesisApiService;

import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton instance of a ThesisRepository.
 * Database access to fetch theses is launched over this class.
 */
public final class ThesisRepository implements BaseRepository<Thesis> {
  private static ThesisRepository INSTANCE;
  private ThesisRemoteDataSource thesisRemoteDataSource;

  private ThesisRepository() {

  }

  /**
   * @return current instance of ThesisRepository singleton class.
   */
  public static ThesisRepository getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ThesisRepository();
    }
    return INSTANCE;
  }

  private Call<List<Thesis>> getTheses() {
    Retrofit api = new Retrofit.Builder()
            .baseUrl("").addConverterFactory(GsonConverterFactory.create()).build();

    ThesisApiService apiService = api.create(ThesisApiService.class);
    Call<List<Thesis>> call = apiService.getTheses();
    call.enqueue(new Callback<List<Thesis>>() {
      @Override
      public void onResponse(final Call<List<Thesis>> call, final Response<List<Thesis>> response) {
        if (!response.isSuccessful()) {
          System.out.println(response.code());
        }
        /**
         * Theses are now acquired through the response.body() call and post.getForm() as an example.
         */

      }

      @Override
      public void onFailure(final Call<List<Thesis>> call, final Throwable t) {
        System.out.println(t.getMessage());
      }
    });

  }

  @Override
  public List getAll() {
    return null;
  }

  @Override
  public Optional getById(final int id) {
    return Optional.empty();
  }

  @Override
  public boolean save(final Thesis thesis) {
    return false;
  }

  @Override
  public boolean delete(final int id) {
    return false;
  }
}
