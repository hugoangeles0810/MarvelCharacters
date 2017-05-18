/*
 * Copyright 2017 Hugo Angeles
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 *    http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.hugoangeles0810.comiccharacters.data.source.remote;

import java.util.List;

import io.github.hugoangeles0810.comiccharacters.characterdetail.domain.model.Comic;
import io.github.hugoangeles0810.comiccharacters.data.source.ComicsRemoteDataSource;
import retrofit.RetrofitError;

public class ComicsRemoteDataSourceImpl implements ComicsRemoteDataSource {

    private ApiClient.ServicesApi mServicesApi;

    private ComicsRemoteDataSourceImpl(ApiClient.ServicesApi servicesApi) {
        mServicesApi = servicesApi;
    }

    public static ComicsRemoteDataSourceImpl getInstance(ApiClient.ServicesApi servicesApi) {
        return new ComicsRemoteDataSourceImpl(servicesApi);
    }

    @Override
    public void getComics(Long characterId, LoadComicsCallback callback) {
        try {
            List<Comic> comics = mServicesApi.getComics(characterId);

            if (comics != null || !comics.isEmpty()) {
                callback.onComicsLoaded(comics);
            } else {
                callback.onDataNotAvailable();
            }
        } catch (RetrofitError e) {
            callback.onDataNotAvailable();
        }
    }
}