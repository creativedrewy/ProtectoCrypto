package com.creativedrewy.protectocrypto.usecase

import android.content.Intent
import android.content.SharedPreferences
import javax.inject.Inject

data class FieldUpdate(
    val key: String = "",
    val data: String = ""
)

class IncomingDataUseCase @Inject constructor(
    private val sharedPrefs: SharedPreferences
) {

    companion object {
        const val INPUT_KEY_PREF = "inputKeyPref"
        const val INPUT_DATA_PREF = "inputDataPref"
    }

    /**
     * Process the intent for the text payload and return it with
     * the relevant data field it will be put in
     */
    fun processIntentForUpdate(intent: Intent): FieldUpdate {
        var returnFields = FieldUpdate()

        with (intent) {
            if (action == Intent.ACTION_SEND && type == "text/plain") {
                getStringExtra(Intent.EXTRA_TEXT)?.let { incomingText ->
                    val storedKey = sharedPrefs.getString(INPUT_KEY_PREF, "")
                    val storedData = sharedPrefs.getString(INPUT_DATA_PREF, "")

                    when {
                        storedKey == "" && storedData == "" -> {
                            //sharedPrefs.edit { putString(INPUT_KEY_PREF, incomingText) }

                        }
                        storedKey != "" && storedData == "" -> {
                            //sharedPrefs.edit { putString(INPUT_DATA_PREF_PREF, incomingText) }

                        }
                        storedKey != "" && storedData != "" -> {

                        }
                    }
                }
            }
        }

        return returnFields
    }

}