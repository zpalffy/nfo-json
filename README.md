# nfo-json

A command-line utility that generates json for a number of .nfo files.  The utility is able to scan multiple directories at a time and combine all output into a single json document.  There are also options to download contained movie posters to a given directory while also resizing them.  It is written in Java utilizing Gradle to build.

Why?
----
To present a list of all movies or tv shows on a web page.  [An example is here](http://zpalffy.github.io/nfo-json-example) ([source](https://github.com/zpalffy/zpalffy.github.io/tree/master/nfo-json-example)) using only html and javascript, but it would be possible to write your own presentation using the json output (and potentially the downloaded posters.)

Download
--------
A built distribution is [available here](https://www.dropbox.com/s/ropmkw4zkclzltl/nfojson.zip?dl=0).

Building
--------
- `gradle tasks` will generate a list of tasks that can be performed.  
- `gradle distzip` will create a zip file for distributing the utility.
- `gradle installdist` will create the distribution director under `build`.
- `gradle run` will run the utility in-place.

Usage
-----
If you run the utility with `-h` you will get a list of options that may be supplied to the utility, e.g. `nfojson -h`.

Example Output
--------------
The following is output when run on a directory that contains 2 .nfo files like `nfojson -pretty /Media/Video/Movies`

``` json
[
   {
      "genre": "COMEDY,CRIME,MYSTERY",
      "tomatoReviews": 26,
      "tomatoRotten": 10,
      "metascore": 36,
      "location": "/Media/Video/Movies/Clue (1985).nfo",
      "votes": "47,840",
      "runtime": "94min",
      "actor": [
         {"name": "Eileen Brennan"},
         {"name": "Tim Curry"},
         {"name": "Madeline Kahn"},
         {"name": "Christopher Lloyd"}
      ],
      "outline": "Six guests are invited to a strange house and must cooperate with the staff to solve a murder mystery.",
      "released": "13 Dec 1985",
      "title": "Clue",
      "tomatoMeter": 62,
      "updated": "Wed Jun 25 16:04:24 CDT 2008",
      "tomatoUserRating": 3.6,
      "tomatoConsensus": "N/A",
      "year": 1985,
      "writer": "John Landis (story), Jonathan Lynn (story), Jonathan Lynn (screenplay)",
      "Website": "N/A",
      "tomatoRating": 5.8,
      "tomatoUserReviews": "92,811",
      "director": "Jonathan Lynn",
      "mpaa": "PG",
      "tomatoFresh": 16,
      "tomatoImage": "fresh",
      "BoxOffice": "N/A",
      "DVD": "27 Jun 2000",
      "country": "USA",
      "Production": "Paramount Pictures",
      "awards": "N/A",
      "rating": 7.4,
      "language": "English, French",
      "tomatoUserMeter": 87,
      "thumb": "http://ia.media-imdb.com/images/M/MV5BNTc3NDc1ODYxNl5BMl5BanBnXkFtZTcwMjc5MDMyMQ@@._V1_SX300.jpg"
   },
   {
      "genre": "BIOGRAPHY,DRAMA,SPORT",
      "tomatoReviews": 178,
      "tomatoRotten": 38,
      "metascore": 62,
      "location": "/Media/Video/Movies/42 (2013).nfo",
      "votes": "53,530",
      "runtime": "128min",
      "actor": [
         {"name": "Chadwick Boseman"},
         {"name": "Harrison Ford"},
         {"name": "Nicole Beharie"},
         {"name": "Christopher Meloni"}
      ],
      "outline": "The story of Jackie Robinson from his signing with the Brooklyn Dodgers organization in 1945 to his historic 1947 rookie season when he broke the color barrier in Major League Baseball.",
      "released": "12 Apr 2013",
      "title": 42,
      "tomatoMeter": 79,
      "updated": "Sun Aug 25 17:48:33 CDT 2013",
      "tomatoUserRating": 4.1,
      "tomatoConsensus": "42 is an earnest, inspirational, and respectfully told biography of an influential American sports icon, though it might be a little too safe and old-fashioned for some.",
      "year": 2013,
      "writer": "Brian Helgeland",
      "Website": "http://42movie.warnerbros.com/",
      "tomatoRating": 6.8,
      "tomatoUserReviews": "117,649",
      "director": "Brian Helgeland",
      "mpaa": "PG-13",
      "tomatoFresh": 140,
      "tomatoImage": "certified",
      "BoxOffice": "$95.0M",
      "DVD": "16 Jul 2013",
      "country": "USA",
      "Production": "Warner Bros. Pictures",
      "awards": "4 wins & 16 nominations.",
      "rating": 7.6,
      "language": "English",
      "tomatoUserMeter": 86,
      "thumb": "http://image.tmdb.org/t/p/original/kCAJavd4H4WXBdFb7PpmfsNlKPn.jpg"
   }
]
```

If run with `-thumb-dir` supplied, then movie posters are downloaded, e.g. `nfojson -pretty -thumb-dir ~/posters -thumb-height 100 /Media/Video/Movies`  This command will download the following posters:

```
~/posters/clue.jpg
~/posters/42.jpg
```

The resulting json also contains the following for the `localThumb` property:

```
...
"localThumb": "clue.jpg",
...
"localThumb": "42.jpg",
...
```

History
-------
* **1.0** Initial commit
