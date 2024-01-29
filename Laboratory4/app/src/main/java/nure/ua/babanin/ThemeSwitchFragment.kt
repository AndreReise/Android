package nure.ua.babanin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment


class ThemeSwitchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView: View = inflater.inflate(R.layout.fragment_theme_switch, container, false)
        val themeSwitch = rootView.findViewById<Button>(R.id.checkBoxDarkMode)


        themeSwitch.setOnClickListener { view ->

            if (view.resources.configuration.isNightModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }

            requireActivity().recreate()
        }
        return rootView
    }
}