package np.com.amrittwanabasu.orchestrateai.lyrics.predictor;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrchestrateAPIService {

    @POST("predictor/lyrics")
    Call<String> predictLyrics(@Body String lyrics);
}
