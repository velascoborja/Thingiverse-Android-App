
# App Architecture

The project follows clean architecture divided in the following modules:
- app: app monolith module where resides all the features related core and common code
- domain: domain models, repository and use case definitions.
- data: domain implementation and datasources.
- presenteation: base clases related with presentation layer and helper classes.

## Tech stack

We use Android `ViewModels` with some enhacenments to bring **MVI** features and adpot an unidirectional data flow style. Communication with the view is done via `LiveData`.

We use `Coroutines` to run domain logic.

To perform `DI` we use `Hilt` (no Dagger-Android 🤮)

## Architecture layering

### Domain

All the domain logic of the app lives here. Here you will find UseCases, repository definitions and domain models of the app.

To drive all the data flow from datasources to presentation layer we use a functional data type called Either, which represents discriminated union that can contain either and error (`Throwable`) or a value (`T`). This way we can safely wrap all the computation outcomes in a single type that will safely travel through all the layers without propagating any potential errors.

### Data

At the data layer level we network datasources

Network services implementation is done with Retrofit

### Presentation

Presentation layers mostly contain base VM definition and related classes.

`BaseViewModel` is the base class to inherit and provides the base machinery to work with MVI. 

View state is represented by `ViewState` interface and is usually implemented as a data class to allow using its copying capabilities.

State reducer is implemented by an extension function over the current ViewState.

```
typealias Reducer<T> = T.() -> T
```

This way we can achieve a very simple declarative style when dealing with state changes. Just by calling for example:

```
setState { copy(someField = "someValue")}
```

the ViewModel takes care of dispatching the new ViewState to View observers.

##### ViewActions

ViewActions are special actions that differs from ViewState in the way they are meant to only be handled once (think for example in Navigation actions). They are implemented in another separate stream.

VMs can push new actions to the View just by calling:

```
dispatchAction(SomeAction())
```

##### ViewErrors

Errors are another type that needs to be handled separately from all the other View stuff.

To dispatch errors call the method:

```
dispatchError(Throwable)
```

##### Launching effects

Side effects (coroutines) can be launched by calling:

```
launch {
  //Your stuff here
}
```

Launched coroutines run by default in Main thread so its perfectly safe to call 
`setState/dispactchAction/dispatchError` methods from inside `launch` block

Diving in more detail we have:

#### DataComponent

Provides (and exposes) all the domain implementation dependencies (mainly UseCases)

#### AppComponent

Provides app wide dependencies. Depends on `DataComponent` and also exposes required dependencies from it to their children.