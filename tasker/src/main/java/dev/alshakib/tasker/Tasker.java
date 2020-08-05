/*
 * MIT License
 *
 * Copyright (c) 2020 Al Shakib (shakib@alshakib.dev)
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

package dev.alshakib.tasker;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Tasker {
    private final Handler handler;
    private final Executor executor;

    public Tasker() {
        this.handler = new Handler(Looper.getMainLooper());
        this.executor = Executors.newCachedThreadPool();
    }

    public <R> void executeAsync(Task<R> task) {
        try {
            task.onPreExecute();
            executor.execute(new RunnableTask<>(handler, task));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class RunnableTask<R> implements Runnable {
        private final Handler handler;
        private final Task<R> task;

        public RunnableTask(Handler handler, Task<R> task) {
            this.handler = handler;
            this.task = task;
        }

        @Override
        public void run() {
            try {
                final R result = task.call();
                handler.post(new RunnableTaskForHandler<>(task, result));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class RunnableTaskForHandler<R> implements Runnable {
        private final Task<R> task;
        private final R result;

        public RunnableTaskForHandler(Task<R> task, R result) {
            this.task = task;
            this.result = result;
        }

        @Override
        public void run() {
            task.onPostExecute(result);
        }
    }

    public abstract static class Task<R> implements Callable<R> {
        protected void onPreExecute() {}
        protected abstract R doInBackground();
        protected void onPostExecute(R result) {}

        @Override
        public R call() {
            return doInBackground();
        }
    }
}
