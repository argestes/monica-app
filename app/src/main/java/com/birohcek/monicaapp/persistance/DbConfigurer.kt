package com.birohcek.monicaapp.persistance

import android.app.Application
import android.util.Log
import com.birohcek.monicaapp.Database
import com.birohcek.monicaapp.networking.models.SingleDateDto
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.logs.LogSqliteDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
class DbConfigurer {

    @Provides
    fun provideSqlDriver(context: Application): SqlDriver {
        val driver: SqlDriver = AndroidSqliteDriver(Database.Schema, context, "test.db")
        val loggingDriver = LogSqliteDriver(driver) { log ->
            Log.d("SQLITELOG", log)
        }

        return loggingDriver
    }

    @Provides
    fun provideDatabase(driver: SqlDriver): Database {
        val dateStringAdapter = object : ColumnAdapter<SingleDateDto, String> {

            override fun decode(databaseValue: String): SingleDateDto {
                val split = databaseValue.split("|")
                val ageBased = split[0].toBooleanNullable()
                val isYearUnknown = split[1].toBooleanNullable()
                val data = split[2].let {
                    if (it == "null")
                        null
                    else
                        it
                }

                return SingleDateDto(ageBased, isYearUnknown, data)
            }

            override fun encode(value: SingleDateDto): String {
                return "${value.isAgeBased}|${value.isYearUnknown}|${value.date}"

            }
        }

        return Database(driver,
        contactAdapter = Contact.Adapter(
            birthDateAdapter = dateStringAdapter
        ))
    }
}