![Android DText Library](docs/assets/tasker_cover.png)

[![](https://jitpack.io/v/com.gitlab.AlShakib/tasker.svg)](https://jitpack.io/#com.gitlab.AlShakib/tasker)

# Tasker - An [AsyncTask](https://developer.android.com/reference/android/os/AsyncTask) Alternative

`Tasker` is intended to enable proper and easy use of the UI and the background thread. `AsyncTask` is going to be deprecated in API level 30. Android reference suggests to use the standard `java.util.concurrent` class as alternative ([more here](https://developer.android.com/reference/android/os/AsyncTask)). `Tasker` is a wrapper class of `java.util.concurrent` to run computation in the background thread and publish the result in the UI thread. The source code is published under GPLv3 and the license is available [here](LICENSE).

## Table of Contents

[[_TOC_]]

## Supported SDK

The latest version is available for,

* Android SKD 9 and higher

## Getting Started

An `Tasker` lets you perform operations in the background. When they've finished running, it then allows you to update UI in the main thread.

### Installation

#### For Gradle:

**Step 1.** Add the `JitPack` repository to your build file

Add it in your root `build.gradle` at the end of repositories:

```Java
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

**Step 2.** Add the dependency

```java
dependencies {
	implementation "com.gitlab.AlShakib:tasker:1.0.1"
}
```

#### For Maven:

**Step 1.** Add the `JitPack` repository to your build file

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

**Step 2.** Add the dependency

```xml
<dependency>
    <groupId>com.gitlab.AlShakib</groupId>
    <artifactId>tasker</artifactId>
    <version>1.0.1</version>
</dependency>
```



### Usages

You create an `Tasker`  object, and pass a `Tasker.Task<Result>` object to the `executeAsync()` method.

You create an `Tasker.Task<Result>` class by extending the `Tasker.Task<Result>` class, and implementing its `doInBackground()` method. The code in this method runs in a background thread, so it's the perfect place for you to put your code for a heavy job. The `Tasker.Task<Result>` class also has an `onPreExecute()` method that runs before `doInBackground()` and an `onPostExecute()` method that runs afterward.

`Tasker.Task` is defined by a generic parameter: Result which is the type of the task result. You can set this to `Void` if you're not going to use it.

```java
class MyTask extends Tasker.Task<Result> {

    @Override
    protected void onPreExecute() {
		// Code to run before executing the task.
    }

    @Override
    protected Result doInBackground() {
        // Code that you want to run in a background thread.
        return null;
    }

    @Override
    protected void onPostExecute(Result result) {
		// Code that you want to run when the task is complete.
    }
}
```

You can run a `Task` by calling the `Tasker executeAsync()` method and passing it a `Tasker.Task` object.

```java
Tasker tasker = new Tasker();
tasker.executeAsync(new MyTask());
```

For a practical example, see [here](https://gitlab.com/AlShakib/tasker/blob/master/app/src/main/java/dev/alshakib/tasker/example/MainActivity.java#L55)

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## Thanks to

- Project Icon is made by [Pixel Perfect](https://www.flaticon.com/authors/pixel-perfect) from [www.flaticon.com](https://www.flaticon.com)

## License

[GNU General Public License v3.0](LICENSE)

Copyright © 2020 [Al Shakib](https://alshakib.dev/)