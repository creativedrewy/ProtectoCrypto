package com.creativedrewy.protectocrypto.usecase

import android.content.Intent
import javax.inject.Inject

sealed class FieldUpdate(val value: String)

class None: FieldUpdate("")
class Key(keyValue: String) : FieldUpdate(keyValue)
class Data(dataValue: String) : FieldUpdate(dataValue)

class IncomingDataUseCase @Inject constructor() {

    private var submitCounter: Int = 0

    /**
     * Process the intent for the text payload and return it with
     * the relevant data field it will be put in
     */
    fun processIntentForUpdate(intent: Intent): FieldUpdate {
        var returnField: FieldUpdate = None()

        with (intent) {
            if (action == Intent.ACTION_SEND && type == "text/plain") {
                getStringExtra(Intent.EXTRA_TEXT)?.let { incomingText ->
                    if (submitCounter == 0 || submitCounter % 2 == 0) {
                        returnField = Key(incomingText)
                    } else {
                        returnField = Data(incomingText)
                    }

                    submitCounter++
                }
            }
        }

        return returnField
    }

}