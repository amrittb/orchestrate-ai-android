package np.com.amrittwanabasu.orchestrateai.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class OrchestrateServiceResolver {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(APIConstants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <T> T resolveService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
