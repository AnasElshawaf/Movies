package com.example.anase.popularmovies2;

import ckm.simple.sql_provider.UpgradeScript;
import ckm.simple.sql_provider.annotation.ProviderConfig;
import ckm.simple.sql_provider.annotation.SimpleSQLConfig;

/**
 * Created by anase on 21/11/2016.
 */

@SimpleSQLConfig(
        name = "PopularMovies",
        authority = "com.example.anase.popularmovies2.authority",
        database = "Movie.db",
        version = 1)

public class PopularConfig implements ProviderConfig {

    @Override
    public UpgradeScript[] getUpdateScripts() {
        return new UpgradeScript[0];
    }
}
