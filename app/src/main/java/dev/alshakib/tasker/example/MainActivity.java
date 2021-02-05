/*
 * MIT License
 *
 * Copyright (c) 2021 Al Shakib (shakib@alshakib.dev)
 *
 * This file is part of Tasker
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package dev.alshakib.tasker.example;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import dev.alshakib.tasker.Tasker;
import dev.alshakib.tasker.example.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());
    }

    public void onClickTryMe(View view) {
        Tasker tasker = new Tasker();

        // Add task to execute.
        // Here Bitmap is the result type.
        // We want to download an image in the background
        // then convert that image to bitmap
        // and set that bitmap to an ImageView.
        tasker.executeAsync(new Tasker.Task<Bitmap>() {
            @Override
            protected void onPreExecute() {

                // Show progress bar before start downloading.
                viewBinding.progressBar.setVisibility(View.VISIBLE);
                viewBinding.resultTextView.setVisibility(View.GONE);
                viewBinding.resultImageView.setVisibility(View.GONE);
            }

            @Override
            protected Bitmap doInBackground() {

                // Download image in the background
                // and convert it to Bitmap as result
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

                // When doInBackground method is completed,
                // this method is going to be called.
                // Here we will set result to the image view
                viewBinding.progressBar.setVisibility(View.GONE);
                if (result != null) {
                    viewBinding.resultImageView.setVisibility(View.VISIBLE);
                    viewBinding.resultTextView.setVisibility(View.GONE);
                    viewBinding.resultImageView.setImageBitmap(result);
                } else {
                    viewBinding.resultImageView.setVisibility(View.GONE);
                    viewBinding.resultTextView.setVisibility(View.VISIBLE);
                    viewBinding.resultTextView.setText(getResources().getString(R.string.result_error));
                }
            }
        });
    }
}
