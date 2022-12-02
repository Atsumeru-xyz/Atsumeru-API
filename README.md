# Atsumeru-API

Kotlin API wrapper for Atsumeru

### Dependency

Make sure to add Jitpack repository into your `build.gradle`
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```

Then just put `implementation` line into your gradle dependencies:
```groovy
dependencies {
    implementation 'com.github.AtsumeruDev:Atsumeru-API:<x.y>'
}
```

### How to use

Init library:
```kotlin
AtsumeruAPI.init(OkHttpClientBuilder, isDebug)
```

Add server:
```kotlin
val serverManager = AtsumeruAPI.getServerManager()
serverManager.addServer(
    Server(
        serverManager.createNewServerId(),
        <server name>,
        <server address>,
        Pair(<username>, <password>),
        isEncrypted // if connecting with ConnectKey
    )
)
```

List servers:
```kotlin
val list = AtsumeruAPI.getServerManager().listServers()
```

Change current server:
```kotlin
AtsumeruAPI.changeServer(<server id>)
```

Make calls using predefined methods like this:

Synchronous call
```kotlin
val books = AtsumeruAPI.getBooksList().blockingGet()
```

Async call
```kotlin
AtsumeruAPI.getBooksList().
    .cache()
    .subscribeOn(Schedulers.newThread())
    .observeOn(Schedulers.io())
    .subscribe(
        books -> {},
        throwable -> {}
    )
```
