/*
 * Copyright (c) 2020 Al Shakib (shakib@alshakib.dev)
 *
 * This file is part of Tasker
 *
 * Tasker is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Tasker is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Tasker.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.alshakib.tasker.example;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import dev.alshakib.tasker.Tasker;

public class MainActivity extends AppCompatActivity {
    private final String LOG_TAG = this.getClass().getSimpleName();
    private ImageView resultImageView;
    private ProgressBar progressBar;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultImageView = findViewById(R.id.result_image_view);
        progressBar = findViewById(R.id.progress_bar);
        resultTextView = findViewById(R.id.result_text_view);
    }

    public void onClickTryMe(View view) {
        Tasker tasker = new Tasker();
        tasker.executeAsync(new Tasker.Task<Bitmap>() {
            @Override
            protected void onPreExecute() {
                // Show progress bar
                progressBar.setVisibility(View.VISIBLE);
                resultTextView.setVisibility(View.GONE);
                resultImageView.setVisibility(View.GONE);
            }

            @Override
            protected Bitmap doInBackground() {
                // Download image in the background
                Bitmap result = null;
                try {
                    URL ImageUrl = new URL(getResources().getString(R.string.image_url));
                    HttpURLConnection conn = (HttpURLConnection) ImageUrl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream inputStream = conn.getInputStream();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    result = BitmapFactory.decodeStream(inputStream, null, options);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                // Background job is completed.
                // Set result bitmap to the image view
                progressBar.setVisibility(View.GONE);
                if (result != null) {
                    resultImageView.setVisibility(View.VISIBLE);
                    resultTextView.setVisibility(View.GONE);
                    resultImageView.setImageBitmap(result);
                } else {
                    resultImageView.setVisibility(View.GONE);
                    resultTextView.setVisibility(View.VISIBLE);
                    resultTextView.setText(getResources().getString(R.string.result_error));
                }
            }
        });
    }
}