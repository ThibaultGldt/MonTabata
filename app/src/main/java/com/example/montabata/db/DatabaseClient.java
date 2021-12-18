package com.example.montabata.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.AutoMigration;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DatabaseClient {
    // Instance unique permettant de faire le lien avec la base de données
    private static DatabaseClient instance;

    // Objet représentant la base de données de votre application
    private AppDatabase appDatabase;

    // Constructeur
    private DatabaseClient(final Context context) {

        // Créer l'objet représentant la base de données de votre application
        // à l'aide du "Room database builder"
        // MyToDos est le nom de la base de données
        //appDatabase = Room.databaseBuilder(context, AppDatabase.class, "MyToDos").build();

        ////////// REMPLIR LA BD à la première création à l'aide de l'objet roomDatabaseCallback
        // Ajout de la méthode addCallback permettant de populate (remplir) la base de données à sa création
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "MonTabata")
                .addMigrations(MIGRATION_1_2)
                .addCallback(roomDatabaseCallback)
                .build();
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `exercice` (`id` INTEGER, `nom` TEXT, `description` TEXT, `duree` INTEGER, `repos` INTEGER, PRIMARY KEY(`id`))");
            database.execSQL("CREATE TABLE IF NOT EXISTS `training` (`m_id` INTEGER NOT NULL, `m_nom` TEXT, `m_cycle` INTEGER NOT NULL, PRIMARY KEY(`m_id`))");
        }
    };

    // Méthode statique
    // Retourne l'instance de l'objet DatabaseClient
    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    // Retourne l'objet représentant la base de données de votre application
    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    // Objet permettant de populate (remplir) la base de données à sa création

    RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {

        // Called when the database is created for the first time.
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //
            db.execSQL("INSERT INTO training(m_nom, m_cycle) VALUES("+"'Haut du corps'"+", 4);");
            db.execSQL("INSERT INTO exercice (nom, description, duree, repos) VALUES(\"Tractions\", \"test\", 10, 20);");
            db.execSQL("INSERT INTO exercice (nom, description, duree, repos) VALUES(\"Pompes\", \" Faire le mouvement lentement\", 10, 20);");

        }
    };
}
