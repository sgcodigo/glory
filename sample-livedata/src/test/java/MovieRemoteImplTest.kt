import com.codigo.movies.data.datasource.remote.MovieRemote
import com.codigo.movies.data.datasource.remote.MovieRemoteImpl
import com.codigo.movies.data.network.service.MovieService
import com.codigo.movies.domain.type.Either
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieRemoteImplTest {

    @get:Rule
    val mockWebServer = MockWebServer()

    private lateinit var movieRemote: MovieRemote

    private val popularMoviesJson = """{"page":1,"total_results":10000,"total_pages":500,"results":[{"popularity":625.548,"vote_count":1349,"video":false,"poster_path":"\/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg","id":475557,"adult":false,"backdrop_path":"\/pLO4qJdQxhAMPaFJu7q8bgme6R3.jpg","original_language":"en","original_title":"Joker","genre_ids":[80,18,53],"title":"Joker","vote_average":8.7,"overview":"During the 1980s, a failed stand-up comedian is driven insane and turns to a life of crime and chaos in Gotham City while becoming an infamous psychopathic crime figure.","release_date":"2019-10-04"},{"popularity":227.539,"vote_count":1369,"video":false,"poster_path":"\/zfE0R94v1E8cuKAerbskfD3VfUt.jpg","id":474350,"adult":false,"backdrop_path":"\/8moTOzunF7p40oR5XhlDvJckOSW.jpg","original_language":"en","original_title":"It Chapter Two","genre_ids":[27],"title":"It Chapter Two","vote_average":7,"overview":"27 years after overcoming the malevolent supernatural entity Pennywise, the former members of the Losers' Club, who have grown up and moved away from Derry, are brought back together by a devastating phone call.","release_date":"2019-09-06"},{"popularity":105.318,"vote_count":613,"video":false,"poster_path":"\/a4BfxRK8dBgbQqbRxPs8kmLd8LG.jpg","id":429203,"adult":false,"backdrop_path":"\/6X2YjjYcs8XyZRDmJAHNDlls7L4.jpg","original_language":"en","original_title":"The Old Man & the Gun","genre_ids":[35,80,18],"title":"The Old Man & the Gun","vote_average":6.3,"overview":"The true story of Forrest Tucker, from his audacious escape from San Quentin at the age of 70 to an unprecedented string of heists that confounded authorities and enchanted the public. Wrapped up in the pursuit are a detective, who becomes captivated with Forrest’s commitment to his craft, and a woman, who loves him in spite of his chosen profession.","release_date":"2018-09-28"},{"popularity":189.551,"vote_count":4278,"video":false,"poster_path":"\/lcq8dVxeeOqHvvgcte707K0KVx5.jpg","id":429617,"adult":false,"backdrop_path":"\/5myQbDzw3l8K9yofUXRJ4UTVgam.jpg","original_language":"en","original_title":"Spider-Man: Far from Home","genre_ids":[28,12,878],"title":"Spider-Man: Far from Home","vote_average":7.6,"overview":"Peter Parker and his friends go on a summer trip to Europe. However, they will hardly be able to rest - Peter will have to agree to help Nick Fury uncover the mystery of creatures that cause natural disasters and destruction throughout the continent.","release_date":"2019-07-02"},{"popularity":147.383,"id":582083,"video":false,"vote_count":0,"vote_average":0,"title":"Kamen Rider Build NEW WORLD: Kamen Rider Grease","release_date":"2019-11-27","original_language":"ja","original_title":"仮面ライダービルドNEW WORLD　仮面ライダーグリス","genre_ids":[28,878,12],"backdrop_path":"\/pGVMdzRF0vUosEu51fxMZsLZTqb.jpg","adult":false,"overview":"Deputy Officer of the United Nations Alliance, Simon Marcus, who aims to conquer the world with the terrorist organization Downfall along with the mad scientist Keiji Uraga, attack the Kamen Riders with overwhelming strength. It is only on Kamen Rider Grease that the enemy's abilities did not work. In order to defeat this new enemy and rescue Misora, Kazumi Sawatari must create a new item ... but it is told that it requires the  life of his friends, the Three Crows. The ultimate decision must be made.","poster_path":"\/zm7VhkQgBP9PCqq4R9l3SQmhEIq.jpg"},{"popularity":113.377,"vote_count":140,"video":false,"poster_path":"\/klkFYDZOetegK8floj6IjvbzzQ2.jpg","id":513045,"adult":false,"backdrop_path":"\/xgfn98c2UzvFWP6MXDzytearmQ3.jpg","original_language":"en","original_title":"Stuber","genre_ids":[28,35,80,53],"title":"Stuber","vote_average":6.5,"overview":"After crashing his car, a cop who's recovering from eye surgery recruits an Uber driver to help him catch a heroin dealer. The mismatched pair soon find themselves in for a wild day of stakeouts and shootouts as they encounter the city's seedy side.","release_date":"2019-07-12"},{"popularity":134.044,"vote_count":375,"video":false,"poster_path":"\/kTQ3J8oTTKofAVLYnds2cHUz9KO.jpg","id":522938,"adult":false,"backdrop_path":"\/spYx9XQFODuqEVoPpvaJI1ksAVt.jpg","original_language":"en","original_title":"Rambo: Last Blood","genre_ids":[28,53],"title":"Rambo: Last Blood","vote_average":6.1,"overview":"When John Rambo's niece travels to Mexico to find the father that abandoned her and her mother, she finds herself in the grasps of Calle Mexican sex traffickers. When she doesn't return home as expected, John learns she's crossed into Mexico and sets out to get her back and make them pay.","release_date":"2019-09-20"},{"popularity":124.278,"vote_count":2513,"video":false,"poster_path":"\/w9kR8qbmQ01HwnvK4alvnQ2ca0L.jpg","id":301528,"adult":false,"backdrop_path":"\/m67smI1IIMmYzCl9axvKNULVKLr.jpg","original_language":"en","original_title":"Toy Story 4","genre_ids":[12,16,35,14,10751],"title":"Toy Story 4","vote_average":7.6,"overview":"Woody has always been confident about his place in the world and that his priority is taking care of his kid, whether that's Andy or Bonnie. But when Bonnie adds a reluctant new toy called \"Forky\" to her room, a road trip adventure alongside old and new friends will show Woody how big the world can be for a toy.","release_date":"2019-06-21"},{"popularity":123.295,"vote_count":1421,"video":false,"poster_path":"\/keym7MPn1icW1wWfzMnW3HeuzWU.jpg","id":384018,"adult":false,"backdrop_path":"\/hpgda6P9GutvdkDX5MUJ92QG9aj.jpg","original_language":"en","original_title":"Fast & Furious Presents: Hobbs & Shaw","genre_ids":[28],"title":"Fast & Furious Presents: Hobbs & Shaw","vote_average":6.5,"overview":"A spinoff of The Fate of the Furious, focusing on Johnson's US Diplomatic Security Agent Luke Hobbs forming an unlikely alliance with Statham's Deckard Shaw.","release_date":"2019-08-02"},{"popularity":116.829,"vote_count":2820,"video":false,"poster_path":"\/ziEuG1essDuWuC5lpWUaw1uXY2O.jpg","id":458156,"adult":false,"backdrop_path":"\/stemLQMLDrlpfIlZ5OjllOPT8QX.jpg","original_language":"en","original_title":"John Wick: Chapter 3 - Parabellum","genre_ids":[28,80,53],"title":"John Wick: Chapter 3 - Parabellum","vote_average":7.1,"overview":"Super-assassin John Wick returns with a ${'$'}14 million price tag on his head and an army of bounty-hunting killers on his trail. After killing a member of the shadowy international assassin’s guild, the High Table, John Wick is excommunicado, but the world’s most ruthless hit men and women await his every turn.","release_date":"2019-05-17"},{"popularity":118.391,"vote_count":3631,"video":false,"poster_path":"\/3iYQTLGoy7QnjcUYRJy4YrAgGvp.jpg","id":420817,"adult":false,"backdrop_path":"\/rVqY0Bo4Npf6EIONUROxjYAJfmD.jpg","original_language":"en","original_title":"Aladdin","genre_ids":[12,35,14,10749,10751],"title":"Aladdin","vote_average":7.1,"overview":"A kindhearted street urchin named Aladdin embarks on a magical adventure after finding a lamp that releases a wisecracking genie while a power-hungry Grand Vizier vies for the same lamp that has the power to make their deepest wishes come true.","release_date":"2019-05-24"},{"popularity":122.158,"vote_count":85,"video":false,"poster_path":"\/uTALxjQU8e1lhmNjP9nnJ3t2pRU.jpg","id":453405,"adult":false,"backdrop_path":"\/c3F4P2oauA7IQmy4hM0OmRt2W7d.jpg","original_language":"en","original_title":"Gemini Man","genre_ids":[28,878,53],"title":"Gemini Man","vote_average":6.2,"overview":"Henry Brogen, an aging assassin tries to get out of the business but finds himself in the ultimate battle: fighting his own clone who is 25 years younger than him and at the peak of his abilities.","release_date":"2019-10-11"},{"popularity":121.658,"vote_count":8183,"video":false,"poster_path":"\/jpfkzbIXgKZqCZAkEkFH2VYF63s.jpg","id":920,"adult":false,"backdrop_path":"\/a1MlbLBk5Sy6YvMbSuKfwGlDVlb.jpg","original_language":"en","original_title":"Cars","genre_ids":[12,16,35,10751],"title":"Cars","vote_average":6.7,"overview":"Lightning McQueen, a hotshot rookie race car driven to succeed, discovers that life is about the journey, not the finish line, when he finds himself unexpectedly detoured in the sleepy Route 66 town of Radiator Springs. On route across the country to the big Piston Cup Championship in California to compete against two seasoned pros, McQueen gets to know the town's offbeat characters.","release_date":"2006-06-09"},{"popularity":78.333,"vote_count":2158,"video":false,"poster_path":"\/8j58iEBw9pOXFD2L0nt0ZXeHviB.jpg","id":466272,"adult":false,"backdrop_path":"\/kKTPv9LKKs5L3oO1y5FNObxAPWI.jpg","original_language":"en","original_title":"Once Upon a Time... in Hollywood","genre_ids":[35,18],"title":"Once Upon a Time... in Hollywood","vote_average":7.6,"overview":"A faded television actor and his stunt double strive to achieve fame and success in the film industry during the final years of Hollywood's Golden Age in 1969 Los Angeles.","release_date":"2019-07-26"},{"popularity":75.116,"vote_count":565,"video":false,"poster_path":"\/4WH6AZd473lRZ3hUp8TYiv2LfZW.jpg","id":511987,"adult":false,"backdrop_path":"\/lm4xH0YwFbVvTgdtau1thNK5S6J.jpg","original_language":"en","original_title":"Crawl","genre_ids":[28,18,27,53],"title":"Crawl","vote_average":6.1,"overview":"While struggling to save her father during a Category 5 hurricane, a young woman finds herself trapped inside a flooding house and fighting for her life against Florida’s most savage and feared predators.","release_date":"2019-07-12"},{"popularity":75.32,"vote_count":252,"video":false,"poster_path":"\/zBhv8rsLOfpFW2M5b6wW78Uoojs.jpg","id":540901,"adult":false,"backdrop_path":"\/5GynP6w2OQWSbKnCLHrBIriF4Cw.jpg","original_language":"en","original_title":"Hustlers","genre_ids":[35,80,18],"title":"Hustlers","vote_average":6.2,"overview":"A crew of savvy former strip club employees band together to turn the tables on their Wall Street clients.","release_date":"2019-09-13"},{"popularity":71.194,"vote_count":699,"video":false,"poster_path":"\/fpe0eG2TBLJLQiqlhhvaWIfixXz.jpg","id":419704,"adult":false,"backdrop_path":"\/p3TCqUDoVsrIm8fHK9KOTfWnDjZ.jpg","original_language":"en","original_title":"Ad Astra","genre_ids":[12,18,878],"title":"Ad Astra","vote_average":6.2,"overview":"An astronaut travels to the outer edges of the solar system to find his father and unravel a mystery that threatens the survival of our planet. He uncovers secrets which challenge the nature of human existence and our place in the cosmos.","release_date":"2019-09-20"},{"popularity":80.437,"vote_count":1409,"video":false,"poster_path":"\/dPrUPFcgLfNbmDL8V69vcrTyEfb.jpg","id":479455,"adult":false,"backdrop_path":"\/uK9uFbAwQ1s2JHKkJ5l0obPTcXI.jpg","original_language":"en","original_title":"Men in Black: International","genre_ids":[28,12,35,878],"title":"Men in Black: International","vote_average":5.9,"overview":"The Men in Black have always protected the Earth from the scum of the universe. In this new adventure, they tackle their biggest, most global threat to date: a mole in the Men in Black organization.","release_date":"2019-06-14"},{"popularity":86.47,"vote_count":2075,"video":false,"poster_path":"\/cCTJPelKGLhALq3r51A9uMonxKj.jpg","id":320288,"adult":false,"backdrop_path":"\/ojVQv3qO5dL9kA7pk9KxpMi0ANO.jpg","original_language":"en","original_title":"Dark Phoenix","genre_ids":[28,12,878],"title":"Dark Phoenix","vote_average":6.1,"overview":"The X-Men face their most formidable and powerful foe when one of their own, Jean Grey, starts to spiral out of control. During a rescue mission in outer space, Jean is nearly killed when she's hit by a mysterious cosmic force. Once she returns home, this force not only makes her infinitely more powerful, but far more unstable. The X-Men must now band together to save her soul and battle aliens that want to use Grey's new abilities to rule the galaxy.","release_date":"2019-06-07"},{"popularity":66.463,"vote_count":10958,"video":false,"poster_path":"\/5vHssUeVe25bMrof1HyaPyWgaP.jpg","id":245891,"adult":false,"backdrop_path":"\/iJlGxN0p1suzloBGvvBu3QSSlhT.jpg","original_language":"en","original_title":"John Wick","genre_ids":[28,53],"title":"John Wick","vote_average":7.2,"overview":"Ex-hitman John Wick comes out of retirement to track down the gangsters that took everything from him.","release_date":"2014-10-24"}]}"""

    @Before
    fun init() {
        movieRemote = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(MovieService::class.java)
            .let { MovieRemoteImpl(it) }
    }

    @Test
    fun getPopularMoviesIsSuccess() {
        MockResponse()
            .setBody(popularMoviesJson)
            .setResponseCode(200)
            .also { mockWebServer.enqueue(it) }
        val result = runBlocking { movieRemote.fetchPopularMovies() }
        Assert.assertTrue(result.isRight)
        val size = (result as Either.Right).b.size
        Assert.assertEquals(20, size)
    }

    @After
    fun close() {
        mockWebServer.shutdown()
    }
}