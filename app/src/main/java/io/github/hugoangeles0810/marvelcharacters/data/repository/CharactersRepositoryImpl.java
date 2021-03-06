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

package io.github.hugoangeles0810.marvelcharacters.data.repository;

import android.support.annotation.NonNull;

import java.util.List;

import io.github.hugoangeles0810.marvelcharacters.data.model.Character;
import io.github.hugoangeles0810.marvelcharacters.data.source.CharactersDataSource;

/**
 * Created by hugo on 30/03/17.
 */

public class CharactersRepositoryImpl implements CharactersRepository {

    private static  CharactersRepository INSTANCE = null;

    private final CharactersDataSource mCharsRemoteDataSource;

    private final CharactersDataSource mCharsLocalDataSource;

    private CharactersRepositoryImpl(@NonNull CharactersDataSource charsRemoteDataSource,
                                    @NonNull CharactersDataSource charsLocalDataSource) {
        mCharsRemoteDataSource = charsRemoteDataSource;
        mCharsLocalDataSource = charsLocalDataSource;
    }

    public static CharactersRepository getInstance(CharactersDataSource charsRemoteDataSource,
                                                   CharactersDataSource charsLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new CharactersRepositoryImpl(charsRemoteDataSource, charsLocalDataSource);
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Get characters from local data source (SQLite) or remote data source, whichever is
     * available first
     * <p/>
     * Note: {@link LoadCharactersCallback#onDataNotAvailable()} is fired if all data source fail
     * to get data
     */
    @Override
    public void getCharacters(@NonNull final LoadCharactersCallback callback) {
        mCharsLocalDataSource.getCharacters(new CharactersDataSource.LoadCharactersCallback() {
            @Override
            public void onCharactersLoaded(List<Character> characters) {
                callback.onCharactersLoaded(characters);
            }

            @Override
            public void onDataNotAvailable() {
                getCharactersFromRemoteDataSource(callback);
            }
        });
    }

    private void getCharactersFromRemoteDataSource(final LoadCharactersCallback callback) {
        mCharsRemoteDataSource.getCharacters(new CharactersDataSource.LoadCharactersCallback() {
            @Override
            public void onCharactersLoaded(List<Character> characters) {
                callback.onCharactersLoaded(characters);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

}
