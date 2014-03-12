original-android-cgm
====================

A fork of [@jcostik](https://twitter.com/jcostik)'s [original-android-app](https://github.com/hackingtype1/original-android-cgm) that I'm customizing to work on ~~my old Galaxy Nexus~~ a new Moto G and post simple json to [my very basic node.js/express.js/angular.js/mongo app](https://github.com/jasoncalabrese/project-glu)

[@jcostik](https://twitter.com/jcostik)'s orginial [README.md](https://github.com/hackingtype1/original-android-cgm/blob/master/README.md)

> My original code for grabbing CGM data via an Android device... not pretty!
>
> This was my, learn as I go, need to build this to keep an eye on my 5 year-old, android application.
>
> It is not well designed, properly implemented or remotely recommended for use. BE smart, do not make medical decisions based on any data it is gathering.
>
> It is presented for educational purposes, feel free to clean it up and goof around with it.
>
> To connect to the Dexcom G4, you will need a OTG USB cable.
>
> The Dexcom USB port will break! It was not designed for frequent connect/disconnects. Get a spare:-) and be gentle.

**I 2nd the warning about the Dexcom USB port, I have broken several receivers before ever connecting an OTG cable and now have a backup receiver.**

I'm attempting to keep my changes minimal and distinct so that they may help others.  I have a `feature/*` branch for each change/fix/extension and an `integration` branch that combines the features I'm currently using and any upstream commits that I merge in.  The `master` branch will stay a clean clone of upstream.

-[@jasoncalabrese](https://twitter.com/jasoncalabrese)
