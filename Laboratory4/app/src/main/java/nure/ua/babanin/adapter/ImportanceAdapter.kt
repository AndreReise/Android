package nure.ua.babanin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import nure.ua.babanin.Importance
import nure.ua.babanin.R

class ImportanceAdapter(context: Context, resource: Int, private val priorities: Array<Importance>) :
    ArrayAdapter<Importance>(context, resource, priorities) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent, android.R.layout.simple_spinner_item)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent, android.R.layout.simple_spinner_dropdown_item)
    }

    private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup, resource: Int): View {
        val view: TextView = convertView as? TextView ?: LayoutInflater.from(context)
            .inflate(resource, parent, false) as TextView

        view.text = getLocalizedPriorityString(priorities[position])

        return view
    }

    private fun getLocalizedPriorityString(priority: Importance): String {
        return when (priority) {
            Importance.Low -> context.getString(R.string.low_priority)
            Importance.Medium -> context.getString(R.string.medium_priority)
            Importance.High-> context.getString(R.string.high_priority)
        }
    }
}