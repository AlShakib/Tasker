![Android DText Library](docs/assets/tasker_cover.png)

[![](https://jitpack.io/v/com.gitlab.AlShakib/tasker.svg)](https://jitpack.io/#com.gitlab.AlShakib/tasker)

# Tasker - An [AsyncTask](https://developer.android.com/reference/android/os/AsyncTask) Alternative

Tasker is intended to enable proper and easy use of the UI and background thread. `AsyncTask` is going to be deprecated in API level 30. Android reference suggests to use the standard `java.util.concurrent` class as alternative ([more you can read here](https://developer.android.com/reference/android/os/AsyncTask)). Tasker is a wrapper class of `java.util.concurrent` to run computation on a background thread and publish the result on the UI thread. The source code is published under GPLv3 and the license is available [here](LICENSE).

## Table of Contents

[[_TOC_]]

## Supported SDK

The latest version is available for,

* Android SKD 9 and higher

## Getting Started

### Installation

#### For Gradle:

**Step 1.** Add the `JitPack` repository to your build file

Add it in your root `build.gradle` at the end of repositories:

```Java
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

**Step 2.** Add the dependency

```java
dependencies {
	implementation 'com.gitlab.AlShakib:tasker:1.0.0'
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
    <version>1.0.0</version>
</dependency>
```



### Usages

For a simple drawable from a string,

```java
// Create a Tasker instance
Tasker tasker = new Tasker();
// Execute a task as async
// Here Bitmap is the result type.
tasker.executeAsync(new Tasker.Task<Bitmap>() {
    @Override
    protected void onPreExecute() {
        // This method is going to be called
        // before execution in background thread
    }

    @Override
    protected Bitmap doInBackground() {
        // Run calculation in the background thread in here.
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        // Update the UI with the result here.
    }
});
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