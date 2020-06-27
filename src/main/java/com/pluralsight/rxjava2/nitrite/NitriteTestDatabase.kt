package com.pluralsight.rxjava2.nitrite

import org.dizitart.no2.Nitrite
import java.io.Closeable
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

class NitriteTestDatabase @JvmOverloads constructor(schema: NitriteSchema, databaseFilename: String = "testDatabase.nitrite") : Closeable {

    var database: Nitrite
        private set
    private var databasePath: Path

    init {
        val userHomeDirectory = System.getProperty("user.home")
        Path.of(userHomeDirectory, "PluralSight").also {
            if (!Files.exists(it)) {
                Files.createDirectory(it)
            }
        }
        databasePath = Path.of(userHomeDirectory, "PluralSight", databaseFilename).also {
            if (Files.exists(it)) {
                Files.delete(it)
            }
        }
        database = Nitrite.builder()
                .compressed()
                .filePath(databasePath.toFile())
                .openOrCreate("admin", "admin")
        schema.applySchema(database)
    }

    @Throws(IOException::class)
    override fun close() {
        database.close()
        Files.delete(databasePath)
    }

}