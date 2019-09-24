[![](https://jitpack.io/v/sgcodigo/glory.svg)](https://jitpack.io/#sgcodigo/glory)

# Glory

Glory is a simple lib for MVI pattern using google's viewmodel. It contains -

* view states and one time events separation
* ability to survive configuration changes thanks to viewmodel

Of course you can build MVI app without using any lib. This lib just provides a basic boiler plate template required to build MVI Android app using google's viewmodel. It leverages the following concepts and components:

#### ViewState
View state is just a simple data class representing things to be shown on screen. When view recreated as a result of configuration change, that view will re subscribe to the viewstate in viewmodel and will render the most recent viewstate. So   it should only includes data and states which you don't want to render on floating widgets [Widgets which visibility is partly controlled by the system. Eg, Toast, Dialog and Snackbar]. So states like progress and error [to be rendered on embeded widgets like TextView] are good condidates to be included in view state. Every intent fired by the view will be processed by the model [either of ViewModel/Repository/Interactor] and will produce a new viewstate as a result. Typically, it has one to one relation to screens of the app: one screen, one view state. A screen might has more than one view sate when using composable views. Each composable view will has its own view state in that case. 

```
data class ProductsViewState(
  val macs: List<Product> = emptyList(),
  val loadingMacs: Boolean = false,
  val loadMacsError: Throwable? = null, // will be rendered on Textview
  val iPhones: List<Product> = emptyList(),
  val loadingIPhones: Boolean = false,
  val loadIPhonesError: Throwable? = null // will be rendered on Textview
)
```

#### Events
Events represents things which should be rendered on screen only one time. Like a toast/snack/dialog error message and navigation event as a result of a certain api call. Mixing these kinds of things inside viewstates will yield unwanted results. For instance, a message is shown as a result of an operation using a toast. That toast will be shown again when configuration changes happened. It'll get worse if we are to navigate to another screen as a consequence of that operation. Event should be used in this case as it guarantees that it'll be rendered on screen only one time.

```
sealed class LoginEvent {
  data class ValidationError(val error: Throwable) : ProductsEvent() // want to show as a toast
  
  object LoginSuccessful : ProductsEvent() //navigate to main screen
}
```

#### MviViewModel
It exposes two streams: viewStates and events. Those streams will be subscribed by the view. Lesson learned that mixing states and events in a single viewState class makes emitting states from model and rendering logic overly complicated overtime. Internally view state is wrapped inside a BehaviorSubject as the most recent state should always be availabe for consequent subscription and event is wrapped inside  PublishSubject which is good to emit one time event at realtime. It defines abstract method *processIntents()* to process intents fired from the view. A PublishSubject will have to be created inside viewmodel by the developer for every intent fired from the view.

```
class ProductsViewModel : MviViewModel<..,..> {

  fun fetchMacs() {
    fetchMacsSubject.onNext(Any())
  }
    
  override fun processIntents(): Observable<ProductsViewState> {
    val fetchMacsStates: Observable<ProductsViewState> = fetchMacsSubject
        .switchMap { interactor.fetchMacs() }
        
    // other states //
   }
}    
```
An Intent to fetch Macs is fired from the view by calling *fetchMacs* on Activity.onCreate(). That intent will be handled by the PublishSubject *fetchMacsSubject* and will be processed inside `processIntents()` method. One thing to consider is eventhough `fetchMacs()` is called inside onCreate(), it will be called again if the configuration changed. Can use Rxjava's `take()` operator like the following to prevent this. Ignore it if `fetchMacs` is supposed to be called multiple times.

```
  val fetchMacsStates: Observable<ProductsPartialState> = fetchMacsSubject.take(1)
      .switchMap { interactor.fetchMacs() }
```

#### State Reducer
We can make rendering logic so simple by following passive view pattern. In MVI context, this means just render exactly what's currently inside the viewstate. If the view state says loading then show progress bar and hide it if it says otherwise. If viewstate doesn't include data list , don't show data even it it was preivously shown. In another word, the viewstate and what the user see must be identical at all time. This means it's the job of the model to prepare what to show on screen before emitting a new view state. This means we can no longer do things like this inside RecyclerView adapter. Here's one approach to handle pagination.

```
val movies: MutableList<Movie> = mutableListOf()
    
fun addMovies(newMovies: List<Movie>) {
  movies.addAll(newMovies)
  notifyDataSetChanged()
}
```
Now it's the Model's job to combine the old movies with the new ones thus what's left for the view is to call Adapter's `setMovies(movies)`. Calling `setMovies` every time will likely incurs performance hit so it's recommanded to use DiffUtil or a convenience wrapper class like `ListAdapter` to help with diffing. Combining oldstates with new states is called state reducing in MVI. Let's consider a screen showing movies with pagination. A **limited** list of movies will be returned every time we do a fetch. Thing to note here is it's clear that each fetch won't return the whole complete view state. So we'll use a partial state to represents the states[data/loading/error] of every fetch. And this partial state knows how to create a new complete view state by merging itself with the old view state. It knows how to reduce state in other word. Here's how it look likes -

```
sealed class MoviesPartialState {
    
  abstract fun reduce(oldState: MoviesViewState) : MoviesViewState
    
  data class FirstPageResult(val movies: List<Movie>) : MoviesPartialState() {
    override fun reduce(oldState: MoviesViewState): MoviesViewState {
      return oldState.copy(
        movies = movies,
        loadingFirstPage = false,
        loadFirstPageError = null
      )
    }
  }

  data class NextPageResult(val movies: List<Movie>) : MoviesPartialState() {
    override fun reduce(oldState: MoviesViewState): MoviesViewState {
      return oldState.copy(
        movies = oldState.movies + movies,
        loadingNextPage = false,
        loadNextPageError = null
      )
    }
  }

  object LoadingFirstPage : MoviesPartialState() {
    override fun reduce(oldState: MoviesViewState): MoviesViewState {
      return oldState.copy(
        loadingFirstPage = true,
        loadFirstPageError = null
      )
    }
  }
  
  ... other partial states
}
```

And finally, each partial states will be merged to form a single Observable stream and use scan() operator to do the reducing.

```
fun processIntents() {

  val firstPageMoviesState: Observable<MoviesPartialState> = fetchFirstPageMoviesSubject
      .switchMap { interactor.fetchFirstPageMovies(limit) }
  
  val nextPageMoviesState : Observable<MoviesPartialState> = fetchNextPageMoviesSubject
      .switchMap { interactor.fetchNextPageMovies(page, limit) }
        
  return Observable.mergeArray(
    fetchFirstPageMoviesState,
    fetchNextPageMoviesState
  )
  .scan(MoviesViewState()) { oldState, partialState ->
    partialState.reduce(oldState)
  }
}
```

#### MviActivity/Fragment/FragmentDialog

Developer is typing ...


## Get Started

**STEP 1.** Add it in your root build.gradle at the end of repositories:
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

**STEP 2.** Add the dependency at module level build.gradle

*Rxjava*
```
dependencies {
  implementation 'com.github.sgcodigo.glory:mvi-rx:latest_version'
}
```


*Livedata*
```
  Under development ...
```
## Playing with samples
Refer to sample-clean-mvi module the implementation of Rx version of the lib. That sample also uses clean architecture along side mvi.


For sample-livedata project to work, you first need to replace the fake api key in **confidential.properties** with your **TMDB** api key:
```
TMDB_API_KEY = "your_api_key"
```

If you don't have an api key yet, go ahead and register an api key [here](https://www.themoviedb.org).
