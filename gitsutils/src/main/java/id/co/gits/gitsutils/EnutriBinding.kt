package id.co.gits.gitsutils

/**
 * Created by mochadwi on 8/2/18.
 */
/*
object EnutriBinding {

    @BindingAdapter("app:imageSource")
    @JvmStatic
    fun setImageSource(imageView: ImageView, urlSource: String?) {
        urlSource?.takeIf { it.isNotEmpty() }?.let {
            Picasso.with(imageView.context)
                    .load(urlSource)
                    .error(R.mipmap.no_image_found)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView)
        }
    }

    @BindingAdapter("bind:date")
    @JvmStatic
    fun setDate(textView: TextView, dateInString: String) {
        val sdf = SimpleDateFormat(DateHelper.DATE_FORMAT_DELTA)
        val date: Date = sdf.parse(dateInString)

        val formatInDate = SimpleDateFormat("dd MMM yy")
        val formatInTime = SimpleDateFormat("HH:mm")
        val resultInDate = formatInDate.format(date)
        val resultInTime = formatInTime.format(date)

        textView.text = "$resultInDate At $resultInTime"
    }
}*/
