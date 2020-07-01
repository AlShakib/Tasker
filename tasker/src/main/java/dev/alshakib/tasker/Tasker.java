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
        handler = new Handler(Looper.getMainLooper());
        executor = Executors.newCachedThreadPool();
    }

    public <R> void executeAsync(Task<R> task) {
        try {
            task.onPreExecute();
            executor.execute(new RunnableTask<R>(handler, task));
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
                handler.post(new RunnableTaskForHandler<R>(task, result));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class RunnableTaskForHandler<R> implements Runnable {
        private Task<R> task;
        private R result;

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
        protected abstract void onPreExecute();
        protected abstract R doInBackground();
        protected abstract void onPostExecute(R result);

        @Override
        public R call() {
            return doInBackground();
        }
    }
}
