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

package io.github.hugoangeles0810.marvelcharacters.characters;

import android.support.annotation.NonNull;
import io.github.hugoangeles0810.marvelcharacters.UseCase;
import io.github.hugoangeles0810.marvelcharacters.UseCaseHandler;
import io.github.hugoangeles0810.marvelcharacters.characters.domain.usecase.GetCharacters;

/**
 * Created by hugo on 04/04/17.
 */

public class CharactersPresenter implements CharactersContract.Presenter {

  private final CharactersContract.View mView;
  private final GetCharacters mGetCharacters;

  private final UseCaseHandler mUseCaseHandler;

  public CharactersPresenter(@NonNull  UseCaseHandler useCaseHandler,
                            @NonNull CharactersContract.View view,
                            @NonNull GetCharacters getCharacters) {
    mUseCaseHandler = useCaseHandler;
    mView = view;
    mGetCharacters = getCharacters;
  }

  @Override public void start() {
    loadCharacters();
  }

  @Override public void loadCharacters() {
    mUseCaseHandler.execute(mGetCharacters, new GetCharacters.RequestValues(), new UseCase.UseCaseCallback<GetCharacters.ResponseValue>() {

      @Override public void onSuccess(GetCharacters.ResponseValue response) {
        mView.showCharacters(response.getCharacters());
      }

      @Override public void onError() {

      }
    });
  }
}
