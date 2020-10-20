package com.creativedrewy.protectocrypto.usecase

import android.content.Intent
import android.content.SharedPreferences
import javax.inject.Inject

class FieldUpdate(
    val key: String = "",
    val data: String = ""
)

class IncomingDataUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences
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

//        var returnField: FieldUpdate = None()
//
//        with (intent) {
//            if (action == Intent.ACTION_SEND && type == "text/plain") {
//                getStringExtra(Intent.EXTRA_TEXT)?.let { incomingText ->
//                    if (submitCounter == 0 || submitCounter % 2 == 0) {
//                        returnField = Key(incomingText)
//                    } else {
//                        returnField = Data(incomingText)
//                    }
//
//                    submitCounter++
//                }
//            }
//        }

        return FieldUpdate()
    }

}