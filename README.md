# ad-fontes

## Development Mode

### Start Cider from Emacs:

Put this in your Emacs config file:

```
(setq cider-cljs-lein-repl "(do (use 'figwheel-sidecar.repl-api) (start-figwheel!) (cljs-repl))")
```

Navigate to a clojurescript file and start a figwheel REPL with `cider-jack-in-clojurescript` or (`C-c M-J`)

### Run application:

#### Set up database

I used Postgres. On a Mac, it's easy with the Postgres app: https://postgresapp.com/. On other
platforms, you'll need to find a different solution.

Once Postgres is installed, create a new database cluster and a database:
```
initdb pg
createdb ad_fontes
```
You should only have to do that once. Commands on Linux may be differnt than what I listed above.

Then, start Postgres:
```
postgres -D pg &
```

Initialize Git submodules:
```
git submodule update --init
```

Run database migrations and seed the database:
```
lein deps
lein migratus migrate
lein run -m ad-fontes.db.seed
```

If you don't want the database URL specified in project.clj in the `:migratus` section,
you can run:
```
DATABASE_URL=<some URL> lein migratus migrate
```

Run server:
```
lein clean
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

### Run tests:

```
lein clean
lein doo phantom test once
```

The above command assumes that you have [phantomjs](https://www.npmjs.com/package/phantomjs) installed. However, please note that [doo](https://github.com/bensu/doo) can be configured to run cljs.test in many other JS environments (chrome, ie, safari, opera, slimer, node, rhino, or nashorn).

## Production Build

```
lein clean
lein uberjar
```

That should compile the clojurescript code first, and then create the standalone jar.

When you run the jar you can set the port the ring server will use by setting the environment variable PORT.
If it's not set, it will run on port 3000 by default.

To deploy to Heroku, first install the Heroku command-line executable.
```

```

Then, create your app:

```
heroku create
```

Then deploy the application:

```
git push heroku master
```

To compile clojurescript to javascript:

```
lein clean
lein cljsbuild once min
```


## Licenses
Scripture quotations marked SBLGNT are from the SBL Greek New Testament.
Copyright © 2010 [Society of Biblical Literature](http://www.sbl-site.org/)
and [Logos Bible Software](http://www.logos.com/).


Greek morphological parsing and lemmatization is made available under a
[CC-BY-SA License](https://creativecommons.org/licenses/by-sa/3.0/).
No changes have been made.
