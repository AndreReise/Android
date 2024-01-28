package nure.ua.babanin

import android.app.Activity
import android.view.View
import android.widget.AdapterView
import java.util.Locale

class LanguageSpinnerSelectionListener(private val activity: MainActivity): Activity(), AdapterView.OnItemSelectedListener {

    var isFirstCall = true

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if (isFirstCall)
        {
            isFirstCall = false;

            return;
        }

        if (position == 0)
        {
            setLocale("en")
        }
        else
        {
            setLocale("uk")
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // No-op
    }

    fun setLocale(languageCode: String)
    {
        val configuration = this.activity.resources.configuration

        if (configuration.locale.toLanguageTag() == languageCode)
        {
            return;
        }

        val locale = Locale(languageCode)

        configuration.setLocale(locale)

        this.activity.createConfigurationContext(configuration)
        this.activity.recreate()
    }
}