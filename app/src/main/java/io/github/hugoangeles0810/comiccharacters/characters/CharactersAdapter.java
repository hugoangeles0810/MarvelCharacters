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

package io.github.hugoangeles0810.comiccharacters.characters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import io.github.hugoangeles0810.comiccharacters.R;
import io.github.hugoangeles0810.comiccharacters.characters.domain.model.Character;
import io.github.hugoangeles0810.comiccharacters.util.ImageLoader;
import java.util.ArrayList;
import java.util.List;


public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.ViewHolder> {

  private List<Character> mData;
  private LayoutInflater mInflater;
  private OnItemClickListener mListener;

  interface OnItemClickListener {
    void onItemClick(Character character);
  }

  public CharactersAdapter(@NonNull Context context) {
    mInflater = LayoutInflater.from(context);
    mData = new ArrayList<>();
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.mListener = onItemClickListener;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = mInflater.inflate(R.layout.character_item, parent, false);
    return new ViewHolder(itemView);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    Character character = mData.get(position);
    holder.bind(character, mListener);
  }

  @Override public int getItemCount() {
    return mData.size();
  }

  public void setCharacters(@NonNull List<Character> characters) {
    mData.clear();
    for (Character character : characters) {
      mData.add(character);
    }
    notifyDataSetChanged();
  }

  public void addCharacters(@NonNull List<Character> characters) {
      mData.addAll(characters);
      notifyDataSetChanged();
  }

  public List<Character> getCharacters() {
    return mData;
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    ImageView imageViewPhoto;
    TextView  textViewName;
    TextView  textViewDescription;

    public ViewHolder(View itemView) {
      super(itemView);
      imageViewPhoto = (ImageView) itemView.findViewById(R.id.character_imageview_image);
      textViewName = (TextView) itemView.findViewById(R.id.character_textview_name);
      textViewDescription = (TextView) itemView.findViewById(R.id.character_textview_description);
    }

    public void bind(final Character character, final OnItemClickListener listener) {
      textViewName.setText(character.getName());
      if (character.getDescription() != null && !character.getDescription().trim().isEmpty()) {
        textViewDescription.setVisibility(View.VISIBLE);
        textViewDescription.setText(character.getDescription());
      } else {
        textViewDescription.setVisibility(View.GONE);
      }

      ImageLoader.load(
              imageViewPhoto.getContext(),
              character.getImageUrl(),
              R.drawable.hero_placeholder,
              imageViewPhoto);

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listener != null) {
            listener.onItemClick(character);
          }
        }
      });
    }
  }

}
